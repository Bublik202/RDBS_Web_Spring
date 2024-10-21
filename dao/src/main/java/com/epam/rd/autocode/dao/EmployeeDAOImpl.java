package com.epam.rd.autocode.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;
import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

public class EmployeeDAOImpl implements EmployeeDao {
	private final ConnectionSource connectionSource;
	
	public EmployeeDAOImpl() {
		this.connectionSource = ConnectionSource.instance();
	}

	@Override
	public Optional<Employee> getById(BigInteger Id) {
		try {
			String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(Id));
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Employee employee = getEmployeeMethod(resultSet);
				return Optional.of(employee);
			}		
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return Optional.empty();
	}	

	@Override
	public List<Employee> getAll() {
		List<Employee> employee = new ArrayList<>();
		try {		
			String sql = "SELECT * FROM EMPLOYEE";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				 Employee employeeObj = getEmployeeMethod(resultSet);
				 employee.add(employeeObj);				 
			}		
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employee;
	}

	@Override
	public Employee save(Employee t) {
		return getById(t.getId()).isEmpty() ? insertEmployee(t) : updateEmployee(t);
	}

	@Override
	public void delete(Employee t) {
		try {
			String sql = "DELETE FROM EMPLOYEE WHERE ID = ?";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(t.getId()));
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public List<Employee> getByDepartment(Department department) {
		List<Employee> employees = new ArrayList<>();
		try {
			String sql = "SELECT * FROM EMPLOYEE WHERE DEPARTMENT = ?";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(department.getId()));
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				employees.add(getEmployeeMethod(resultSet));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employees;
	}

	@Override
	public List<Employee> getByManager(Employee employee) {
		List<Employee> employees = new ArrayList<>();
		try {
			String sql = "SELECT * FROM EMPLOYEE WHERE MANAGER = ?";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(employee.getId()));
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				employees.add(getEmployeeMethod(resultSet));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return employees;
	}
	
	private Employee insertEmployee(Employee employee) {
		String sql = "INSERT INTO EMPLOYEE "
				+ "(ID, FIRSTNAME, LASTNAME, MIDDLENAME, POSITION, MANAGER, HIREDATE, SALARY, DEPARTMENT) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(Connection connection = connectionSource.createConnection()){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(employee.getId()));
			statement.setString(2, employee.getFullName().getFirstName());			
			statement.setString(3, employee.getFullName().getLastName());
			statement.setString(4, employee.getFullName().getMiddleName());
			statement.setString(5, employee.getPosition().name());
			statement.setBigDecimal(6, employee.getManagerId() != null ? new BigDecimal(employee.getManagerId()) : BigDecimal.ZERO);
			statement.setDate(7, Date.valueOf(employee.getHired()));				
			statement.setBigDecimal(8, employee.getSalary());
			statement.setBigDecimal(9, new BigDecimal(employee.getDepartmentId()));		
			
			statement.execute();
			
			return employee;
			
		} catch (SQLException e) {
			throw new RuntimeException("Error connection ", e);
	    }
		
	}
	
	private Employee updateEmployee(Employee employee) {
		String sql = "UPDATE EMPLOYEE "
				+ "SET FIRSTNAME = ?, LASTNAME = ?, MIDDLENAME = ?, POSITION = ?, MANAGER = ?, HIREDATE = ?, SALARY = ?, DEPARTMENT = ? "
				+ "WHERE ID = ?";
		try(Connection connection = connectionSource.createConnection()){
			PreparedStatement statement = connection.prepareStatement(sql);				
			statement.setString(1, employee.getFullName().getFirstName());
			statement.setString(2, employee.getFullName().getLastName());
			statement.setString(3, employee.getFullName().getMiddleName());
			statement.setString(4, employee.getPosition().name());
			statement.setBigDecimal(5, employee.getManagerId() != null ? new BigDecimal(employee.getManagerId()) : BigDecimal.ZERO);
			statement.setDate(6, Date.valueOf(employee.getHired()));				
			statement.setBigDecimal(7, employee.getSalary());
			statement.setBigDecimal(8, new BigDecimal(employee.getDepartmentId()));
			
			statement.setBigDecimal(9, new BigDecimal(employee.getId()));
			statement.executeUpdate();
			
			return employee;
		} catch (SQLException e) {
			throw new RuntimeException("Error connection ", e);
	    }
	}
	
	private Employee getEmployeeMethod(ResultSet resultSet) throws SQLException {
		Employee employee = new Employee(new BigInteger(resultSet.getString("ID")),
				new FullName(resultSet.getString("FIRSTNAME"),
						resultSet.getString("LASTNAME"),
						resultSet.getString("MIDDLENAME")),
				Position.valueOf(resultSet.getString("POSITION")),
				resultSet.getDate("HIREDATE").toLocalDate(),
				resultSet.getBigDecimal("SALARY"),
				resultSet.getBigDecimal("MANAGER") != null ?
						new BigInteger(resultSet.getString("MANAGER")) 
						: BigInteger.ZERO,
				resultSet.getBigDecimal("DEPARTMENT") !=null ?
						new BigInteger(resultSet.getString("DEPARTMENT")) 
						: BigInteger.ZERO
				);
		return employee;
	}

}
