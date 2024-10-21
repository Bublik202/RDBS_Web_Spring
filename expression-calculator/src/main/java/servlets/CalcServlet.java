package servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("expression");
		Map<String, String[]> map = req.getParameterMap();
		map.forEach((t, u) -> System.out.println("Key: " + t + " val: " + Arrays.toString(u)));
		
		Stack<Integer> stackNumber = new Stack<>();
		Stack<String> stackOperator = new Stack<>();
		
		String exp = replaceVariables(param, map);
		
		StringTokenizer tokenizer = new StringTokenizer(exp, "()+-*/", true);
		while(tokenizer.hasMoreTokens()) {	
			String symbol = tokenizer.nextToken().trim();
			
			if(isNumber(symbol)) {				
				stackNumber.push(Integer.valueOf(symbol));	// Число
			}else if(symbol.equals("(")){
				stackOperator.push(symbol); // Открывающая скобка	
			}else if(symbol.equals(")")) {
				// Обрабатываем до открывающей скобки
				while(!stackOperator.peek().equals("(")) {					
					pushNamber(stackNumber, stackOperator);
				}				
				stackOperator.pop(); // Убираем '('
			}else if(isOperator(symbol)){
				// Пока оператор не пустой и имеет более высокий приоритет, выполняем его
				while(!stackOperator.isEmpty() && isLeg(symbol, stackOperator.peek())) {					
					pushNamber(stackNumber, stackOperator);
				}
				stackOperator.push(symbol);	// Добавляем оператор			
			}
		}				
		// Обрабатываем оставшиеся операторы
		while(!stackOperator.isEmpty()) {
			pushNamber(stackNumber, stackOperator);
		}	
		// Отправляем результат
		resp.getWriter().write(String.valueOf(stackNumber.pop()));		
	}

	//Перевожу буквы в цифры в моем выражении
	private String replaceVariables(String param, Map<String, String[]> map) {
		StringBuilder builder = new StringBuilder();
		for (char ch : param.toCharArray()) {
            String symbol = String.valueOf(ch);
            while (map.containsKey(symbol) && isVariable(symbol)) {
                symbol = map.get(symbol)[0];
            }
            builder.append(symbol);
        }
        return builder.toString();
	}

	//Закидываю посчитанное число в stack
	private void pushNamber(Stack<Integer> stackNumber, Stack<String> stackOperator) {
		stackNumber.push(calculate(stackNumber.pop(), stackOperator.pop(), stackNumber.pop()));
	}
	
	//Выбор операции
	private boolean isLeg(String symbol, String oper) {
		if((symbol.equals("*") || symbol.equals("/")) && (oper.equals("*") || oper.equals("/"))) {
			return true;
		}
		return false;
	}
	
	//Проверка число ли это
	private boolean isNumber(String str) {
		return str.matches("\\d+");
	}
	
	//Проверка буква ли это
	private boolean isVariable(String str) {
		return str.matches("[a-z]");
	}
	
	//Проверка оператор ли это
	private boolean isOperator(String oper) {
		return oper.matches("[+\\-*/]");
	}
	
	//Вычисление двух переменных
	private int calculate(int a, String oper, int b) {		
		switch (oper) {
		case "+": return b + a;			
		case "-": return b - a;		
		case "*": return b * a;
		case "/":
			if(a != 0) {
				return b / a;
			}								
		default:
			throw new IllegalArgumentException();
		}		
	}
}
