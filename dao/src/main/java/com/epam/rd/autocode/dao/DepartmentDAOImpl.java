package com.epam.rd.autocode.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.rd.autocode.ConnectionSource;
import com.epam.rd.autocode.domain.Department;

public class DepartmentDAOImpl implements DepartmentDao {
	private final ConnectionSource connectionSource;
	
	public DepartmentDAOImpl() {
		this.connectionSource = ConnectionSource.instance();
	}
	
	@Override
	public Optional<Department> getById(BigInteger Id) {
		try {
			String sql = "SELECT * FROM DEPARTMENT WHERE ID = ?";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(Id));
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Department department = getDepartmentMethod(resultSet);
				return Optional.of(department);
			}		
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return Optional.empty();
	}

	@Override
	public List<Department> getAll() {
		List<Department> department = new ArrayList<>();
		try {		
			String sql = "SELECT * FROM DEPARTMENT";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				Department departmentObj = getDepartmentMethod(resultSet);
				department.add(departmentObj);				 
			}		
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		return department;
	}

	@Override
	public Department save(Department t) {
		return getById(t.getId()).isPresent() ? updateDeprtment(t) : insertDeprtment(t);
	}

	@Override
	public void delete(Department t) {
		try {
			String sql = "DELETE FROM DEPARTMENT WHERE ID = ?";
			Connection connection = connectionSource.createConnection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(t.getId()));
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	private Department getDepartmentMethod(ResultSet resultSet) throws SQLException {
		Department department = new Department(
				new BigInteger(resultSet.getString("ID")),
				resultSet.getString("NAME"),
				resultSet.getString("LOCATION"));
		return department;
	}
	
	private Department insertDeprtment(Department department) {		
		String sql = "INSERT INTO DEPARTMENT (ID, NAME, LOCATION) VALUES (?, ?, ?)";
		try (Connection connection = connectionSource.createConnection()){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBigDecimal(1, new BigDecimal(department.getId()));
			statement.setString(2, department.getName());
			statement.setString(3, department.getLocation());
			statement.execute();
			
			return department;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private Department updateDeprtment(Department department) {
		String sql = "UPDATE DEPARTMENT SET NAME = ?, LOCATION = ? WHERE ID = ?";
		try (Connection connection = connectionSource.createConnection()){
			PreparedStatement statement = connection.prepareStatement(sql);			
			statement.setString(1, department.getName());
			statement.setString(2, department.getLocation());
			statement.setBigDecimal(3, new BigDecimal(department.getId()));
			statement.execute();
			
			return department;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
