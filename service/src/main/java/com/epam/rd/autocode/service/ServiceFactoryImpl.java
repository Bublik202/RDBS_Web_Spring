package com.epam.rd.autocode.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

public class ServiceFactoryImpl implements EmployeeService {
	private final ConnectionSource connectionSource;
	
	public ServiceFactoryImpl() {
		this.connectionSource = ConnectionSource.instance();
	}

	//Сортировка Employee по дате
	@Override
	public List<Employee> getAllSortByHireDate(Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE ORDER BY HIREDATE LIMIT ? OFFSET ?";
		try {
			sortEmployee(employees, paging, sql);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employees;
	}	

	//Сортировка Employee по фамилии
	@Override
	public List<Employee> getAllSortByLastname(Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE ORDER BY LASTNAME LIMIT ? OFFSET ?";
		try {
			sortEmployee(employees, paging, sql);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employees;
	}

	//Сортировка Employee по зарплате
	@Override
	public List<Employee> getAllSortBySalary(Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE ORDER BY SALARY LIMIT ? OFFSET ?";
		try {
			sortEmployee(employees, paging, sql);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employees;
	}

	//Сортировка Department по Department NAME и Фамилии
	@Override
	public List<Employee> getAllSortByDepartmentNameAndLastname(Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE "
				+ "LEFT JOIN DEPARTMENT ON EMPLOYEE.DEPARTMENT = DEPARTMENT.ID "
				+ "ORDER BY DEPARTMENT.NAME, EMPLOYEE.LASTNAME LIMIT ? OFFSET ?";
		try {
			sortEmployee(employees, paging, sql);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employees;
	}

	//Сортировка Department по дате
	@Override
	public List<Employee> getByDepartmentSortByHireDate(Department department, Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = ? ORDER BY HIREDATE LIMIT ? OFFSET ?";
		try {
			departmentSort(employees, department, paging, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//Сортировка Department по зарплате
	@Override
	public List<Employee> getByDepartmentSortBySalary(Department department, Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = ? ORDER BY SALARY LIMIT ? OFFSET ?";
		try {
			departmentSort(employees, department, paging, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//Сортировка Department по Фамилии
	@Override
	public List<Employee> getByDepartmentSortByLastname(Department department, Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = ? ORDER BY LASTNAME LIMIT ? OFFSET ?";
		try {
			departmentSort(employees, department, paging, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//Сортировка Manager по Фамилии
	@Override
	public List<Employee> getByManagerSortByLastname(Employee manager, Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE WHERE MANAGER = ? ORDER BY LASTNAME LIMIT ? OFFSET ?";
		try {
			managerSort(manager, employees, paging, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//Сортировка Manager по дате
	@Override
	public List<Employee> getByManagerSortByHireDate(Employee manager, Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE WHERE MANAGER = ? ORDER BY HIREDATE LIMIT ? OFFSET ?";
		try {
			managerSort(manager, employees, paging, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//Сортировка Manager по зарплате
	@Override
	public List<Employee> getByManagerSortBySalary(Employee manager, Paging paging) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM EMPLOYEE WHERE MANAGER = ? ORDER BY SALARY LIMIT ? OFFSET ?";
		try {
			managerSort(manager, employees, paging, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	//Возвращает сотрудника с полной информацией о его отделе и цепочке руководителей
	@Override
	public Employee getWithDepartmentAndFullManagerChain(Employee employee) {
		String sql = "SELECT * FROM EMPLOYEE";
		try (Connection connection = connectionSource.createConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(employee.getId()));
			ResultSet resultSet = statement.executeQuery();
			Employee employeeResult = null;
			while (resultSet.next()) {
				BigDecimal managerId = resultSet.getBigDecimal("MANAGER");
				BigDecimal departmentId = resultSet.getBigDecimal("DEPARTMENT");		
				Employee manager = (managerId != null) ? getEmployeeByIdAllManager(managerId): null;
				Department department = (departmentId != null) ? getDepartmentById(departmentId) : null;
						
				employeeResult = new Employee(
						resultSet.getBigDecimal("ID").toBigInteger(),
						new FullName(resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("MIDDLENAME")),
						Position.valueOf(resultSet.getString("POSITION")),
						resultSet.getDate("HIREDATE").toLocalDate(),
						resultSet.getBigDecimal("SALARY"),
						manager,	
						department
						);	
			}			
			return employeeResult;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	//Возвращает топ-N сотрудников с самыми высокими зарплатами в указанном отделе
	@Override
	public Employee getTopNthBySalaryByDepartment(int salaryRank, Department department) {
		String sql = "SELECT * FROM EMPLOYEE"
				+ "WHERE DEPARTMENT = ? ORDER BY SALARY DESC LIMIT ? OFFSET ?";
		try (Connection connection = connectionSource.createConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(department.getId()));
			statement.setInt(2, (salaryRank-1));
			ResultSet resultSet = statement.executeQuery();
			Employee employee = null;
			if(resultSet.next()) {
				employee = getEmployeeMethod(resultSet);
			}
			return employee;			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	//-------------------------------------------------------------------------------------------------------------
	//Сортировка Employee
	private void sortEmployee(List<Employee> employees, Paging paging, String sql) throws SQLException {
		Connection connection = connectionSource.createConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, paging.itemPerPage);
		statement.setInt(2, (paging.page-1) * paging.itemPerPage);
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			Employee employee = getEmployeeMethod(resultSet);
			employees.add(employee);
		}
	}
		
	//Сортировка Department
	private void departmentSort(List<Employee> employees, Department department, Paging paging, String sql) throws SQLException{
		Connection connection = connectionSource.createConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setBigDecimal(1, new BigDecimal(department.getId()));
		statement.setInt(2, paging.itemPerPage);
		statement.setInt(3, (paging.page-1) * paging.itemPerPage);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {				
			Employee employee = getEmployeeMethod(resultSet);
			employees.add(employee);
		}		
	}
		
	//Сортировка Manager
	private void managerSort(Employee manager, List<Employee> employees, Paging paging, String sql) throws SQLException{
		Connection connection = connectionSource.createConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setBigDecimal(1, new BigDecimal(manager.getId()));
		statement.setInt(2, paging.itemPerPage);
		statement.setInt(3, (paging.page-1) * paging.itemPerPage);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {				
			Employee employee = getEmployeeMethod(resultSet);
			employees.add(employee);
		}			 
	}
	
	//Получение всех Employee
	private Employee getEmployeeMethod(ResultSet resultSet) throws SQLException {
		BigDecimal managerId = resultSet.getBigDecimal("MANAGER");
		BigDecimal departmentId = resultSet.getBigDecimal("DEPARTMENT");
		Employee manager = (managerId != null) ? getEmployeeById(managerId) : null;
		Department department = (departmentId != null) ? getDepartmentById(departmentId) : null;
		
		Employee employee = new Employee(
				new BigInteger(resultSet.getString("ID")),
				new FullName(resultSet.getString("FIRSTNAME"),
						resultSet.getString("LASTNAME"),
						resultSet.getString("MIDDLENAME")),
				Position.valueOf(resultSet.getString("POSITION")),
				resultSet.getDate("HIREDATE").toLocalDate(),
				resultSet.getBigDecimal("SALARY"),
				manager,
				department);
		return employee;
	}
	
	//Получение ID у Employee
	private Employee getEmployeeById(BigDecimal idManager) {	
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		try (Connection connection = connectionSource.createConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, idManager);
			ResultSet resultSet = statement.executeQuery();
			Employee employee = null;
			while(resultSet.next()) {	
				BigDecimal departmentId = resultSet.getBigDecimal("DEPARTMENT");		
				employee = new Employee(
						resultSet.getBigDecimal("ID").toBigInteger(),
						new FullName(resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("MIDDLENAME")),
						Position.valueOf(resultSet.getString("POSITION")),
						resultSet.getDate("HIREDATE").toLocalDate(),
						resultSet.getBigDecimal("SALARY"),
						null,	
						(departmentId != null) ? getDepartmentById(departmentId) : null
						);						
			}
			return employee;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
		
	//Получение ID у Department
	private Department getDepartmentById(BigDecimal departmentId) {				
		try (Connection connection = connectionSource.createConnection()) {
			String sql = "SELECT * FROM DEPARTMENT WHERE ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, departmentId);
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				return new Department(
						resultSet.getBigDecimal("ID").toBigInteger(), 
						resultSet.getString("NAME"), 
						resultSet.getString("LOCATION"));
			} else {
				throw new RuntimeException();
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		}		
	}
	
	//Получение Employee по Id всех Manager
	private Employee getEmployeeByIdAllManager(BigDecimal idManager) {	
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		try (Connection connection = connectionSource.createConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, idManager);
			ResultSet resultSet = statement.executeQuery();
			Employee employee = null;
			if(resultSet.next()) {	
				BigDecimal managerId = resultSet.getBigDecimal("MANAGER");
				BigDecimal departmentId = resultSet.getBigDecimal("DEPARTMENT");		
				employee = new Employee(
						resultSet.getBigDecimal("ID").toBigInteger(),
						new FullName(resultSet.getString("FIRSTNAME"), resultSet.getString("LASTNAME"), resultSet.getString("MIDDLENAME")),
						Position.valueOf(resultSet.getString("POSITION")),
						resultSet.getDate("HIREDATE").toLocalDate(),
						resultSet.getBigDecimal("SALARY"),
						(managerId != null) ? getEmployeeByIdAllManager(managerId): null,	
						(departmentId != null) ? getDepartmentById(departmentId) : null
						);						
			}
			return employee;
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

}
