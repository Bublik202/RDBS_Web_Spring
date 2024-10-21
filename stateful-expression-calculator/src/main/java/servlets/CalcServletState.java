package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc/*")
public class CalcServletState extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Храним переменные по сессии
    private Map<String, Map<String, Integer>> variables = new HashMap<>();
    // Храним выражения по сессии
    private Map<String, String> expressions = new HashMap<>();

    // Обработка метода PUT
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo(); // Получаем путь        
        String[] pathDivide = path.split("/");
        

        // Если это выражение
        if (pathDivide.length == 2 && pathDivide[1].equals("expression")) {
            expressionPut(req, resp);
        // Если это переменная
        } else if (pathDivide.length == 2 && pathDivide[1].matches("[a-z]")) {
            variablePut(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().write("Invalid PUT request");
        }
    }

    // Метод для обработки выражения
    private void expressionPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String expression = req.getReader().lines().reduce("", (acc, actual) -> acc + actual);

            if (expression != null && !expression.trim().isEmpty()) {
                String sessionId = req.getSession().getId();
                if (isValidExpression(expression)) {
                    // Обновляем выражение, если оно уже существует
                    if (expressions.containsKey(sessionId)) {
                        expressions.put(sessionId, expression);
                        resp.setStatus(HttpServletResponse.SC_OK); // 200
                    } else {
                        // Добавляем новое выражение
                        expressions.put(sessionId, expression);
                        resp.setStatus(HttpServletResponse.SC_CREATED); // 201
                        resp.setHeader("Location", req.getRequestURI());
                    }
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                    resp.getWriter().write("Bad expression format");
                }
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
        }
    }

    // Проверка правильности выражения
    private boolean isValidExpression(String expression) {
        return !expression.matches(".*[A-z]{2,}.*");
    }

    // Метод для обработки переменной
    private void variablePut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String variableName = req.getPathInfo().substring(1);
            String variable = req.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            String sessionId = req.getSession().getId();

            // Если переменная — другая переменная
            variable = isVariable(variable) ? String.valueOf(variables.get(sessionId).get(variable)) : variable;

            if (variable != null && variable.matches("-?\\d+")) {
                int intValue = Integer.parseInt(variable);

                variables.computeIfAbsent(sessionId, k -> new HashMap<>());

                // Ограничения на значение переменной
                if (intValue < -10000 || intValue > 10000) {
                    resp.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
                } else {
                    if (variables.get(sessionId).containsKey(variableName)) {
                        // Обновляем переменную, если она уже существует
                        variables.get(sessionId).put(variableName, intValue);
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        // Добавляем новую переменную
                        variables.get(sessionId).put(variableName, intValue);
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        resp.setHeader("Location", req.getRequestURI());
                    }
                }
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
        }
    }

    // Обработка метода DELETE
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathSegments = pathInfo.split("/");

        if (pathSegments.length == 2 && pathSegments[1].matches("[a-z]")) {
            variableDelete(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        }
    }

    // Удаление переменной
    private void variableDelete(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = req.getSession().getId();
        String pathInfo = req.getPathInfo();
        String[] pathSegments = pathInfo.split("/");

        if (variables.containsKey(sessionId)) {
            variables.get(sessionId).remove(pathSegments[1]);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
        }
    }

    // Обработка метода GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String pathInfo = req.getPathInfo();        
        if ("/result".equals(pathInfo)) {
            resultGet(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        }
    }

    // Получение результата выражения
    private void resultGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String sessionId = req.getSession().getId();
        String expression = expressions.get(sessionId);

        variables.computeIfAbsent(sessionId, k -> new HashMap<>());
        Map<String, Integer> variableMap = variables.get(sessionId);

        if (expression != null && !expression.trim().isEmpty() && !variableMap.isEmpty()) {
            if (areVariablesPresent(expression, variableMap)) {
                try {
                	expression = replaceVariables(expression, variableMap);
                	System.out.println(expression);
                    int result = evaluateExpression(expression, variableMap);
                    System.out.println(result);
                    resp.setStatus(HttpServletResponse.SC_OK); // 200
                    resp.getWriter().write(Integer.toString(result));
                } catch (IllegalArgumentException e) {
                    resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                    System.out.println("Err " + e);
                    resp.getWriter().write("Error: IllegalArgumentException " + e);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                resp.getWriter().write("Error: Missing variable(s) for evaluation");
            }
        }
    }

    // Проверка, все ли переменные присутствуют
    private boolean areVariablesPresent(String expression, Map<String, Integer> variableMap) {
        for (char ch : expression.toCharArray()) {
            if (Character.isLetter(ch)) {
                if (!variableMap.containsKey(String.valueOf(ch))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Оценка выражения
    private int evaluateExpression(String expression, Map<String, Integer> variableMap) {
        Stack<Integer> stackNumber = new Stack<>();
        Stack<String> stackOperator = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(expression, "()+-*/", true);
        while (tokenizer.hasMoreTokens()) {
            String symbol = tokenizer.nextToken().trim();

            if (isNumber(symbol)) {
                stackNumber.push(Integer.valueOf(symbol));
            } else if (symbol.equals("(")) {
                stackOperator.push(symbol);
            } else if (symbol.equals(")")) {
                while (!stackOperator.peek().equals("(")) {
                    pushNumber(stackNumber, stackOperator);
                }
                stackOperator.pop();
            } else if (isOperator(symbol)) {
                while (!stackOperator.isEmpty() && isLeg(symbol, stackOperator.peek())) {
                    pushNumber(stackNumber, stackOperator);
                }
                stackOperator.push(symbol);
            }
        }

        while (!stackOperator.isEmpty()) {
            pushNumber(stackNumber, stackOperator);
        }

        if (stackNumber.isEmpty()) {
            throw new IllegalArgumentException("No result to return: empty stack");
        }

        return stackNumber.pop();
    }

    // Замена переменных в выражении на значения
    private String replaceVariables(String expression, Map<String, Integer> variableMap) {
        StringBuilder builder = new StringBuilder();
        for (char ch : expression.toCharArray()) {
            String symbol = String.valueOf(ch);
            
            builder.append(Character.isLetter(ch) 
                    ? (variableMap.get(symbol) < 0 
                        ? "(0 - " + Math.abs(variableMap.get(symbol)) + ")" 
                        : variableMap.get(symbol)) 
                    : symbol);
                      
        }
        return builder.toString();
    }

    // Выполняем операцию между двумя числами
    private void pushNumber(Stack<Integer> stackNumber, Stack<String> stackOperator) {
        if (stackNumber.size() < 2 || stackOperator.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression format: missing operators or numbers");
        }
        stackNumber.push(calculate(stackNumber.pop(), stackOperator.pop(), stackNumber.pop()));
    }

    // Проверка приоритета оператора
    private boolean isLeg(String currentOperator, String operatorOnStack) {
        if (operatorOnStack.equals("(")) {
            return false;
        }
        if ((currentOperator.equals("*") || currentOperator.equals("/")) && (operatorOnStack.equals("+") || operatorOnStack.equals("-"))) {
            return false;
        }
        return true;
    }

    // Проверка на число
    private boolean isNumber(String str) {
        return str.matches("\\d+");
    }

    // Проверка на переменную
    private boolean isVariable(String str) {
        return str.matches("[a-z]");
    }

    // Проверка на оператор
    private boolean isOperator(String oper) {
        return oper.matches("[+\\-*/]");
    }

    // Выполняем операцию
    private int calculate(int a, String oper, int b) {
        switch (oper) {
            case "+": return b + a;
            case "-": return b - a;
            case "*": return b * a;
            case "/": return a != 0 ? b / a : 0;
            default: throw new IllegalArgumentException();
        }
    }
}
