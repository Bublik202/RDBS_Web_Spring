package com.epam.rd.autocode;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SetMapperFactory {

    public SetMapper<Set<Employee>> employeesSetMapper() {
    	return new SetMapper<Set<Employee>>() {
			
			@Override
			public Set<Employee> mapSet(ResultSet resultSet) {
				Set<Employee> employees = new HashSet<>();
	        	try {
					while(resultSet.next()) {
						employees.add(add(resultSet));
					}
					return employees;
				} catch (SQLException e) {
					throw new RuntimeException();
				}
			}
		};
    }
    
    private static Employee add(ResultSet resultSet) {    	
		try {
			String firstName = resultSet.getString("FIRSTNAME");
			String middleName = resultSet.getString("MIDDLENAME");
			String lastName = resultSet.getString("LASTNAME");				
			FullName fullName = new FullName(firstName, lastName, middleName); 
			
			BigInteger id = BigInteger.valueOf(resultSet.getInt("ID"));
			Position position = Position.valueOf(resultSet.getString("POSITION"));
			LocalDate hired = resultSet.getDate("HIREDATE").toLocalDate();
			BigDecimal salary = resultSet.getBigDecimal("SALARY");
			
	        Employee manager = getEmployee(resultSet, resultSet.getInt("MANAGER"));			
			
			return new Employee(id, fullName, position, hired, salary, manager); 
		} catch (SQLException e) {
			throw new RuntimeException();
		}
		   	
    }
    private static Employee getEmployee(ResultSet resultSet, int idManager) {
	    try {
	        Employee employee = null;	        
	        int rowReturn = resultSet.getRow();	        
	        resultSet.beforeFirst();
	        while (resultSet.next() && employee == null) {
	            if (resultSet.getInt("ID") == idManager) {
	                employee = add(resultSet);
	            }
	        }
	        resultSet.absolute(rowReturn);
	        return employee;
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
}
