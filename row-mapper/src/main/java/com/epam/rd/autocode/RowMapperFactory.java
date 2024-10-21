package com.epam.rd.autocode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

public class RowMapperFactory {

    public RowMapper<Employee> employeeRowMapper() {
        return new RowMapper<Employee>() {
			
			@Override
			public Employee mapRow(ResultSet resultSet) {
				try {
					String firstName = resultSet.getString("FIRSTNAME");
					String middleName = resultSet.getString("MIDDLENAME");
					String lastName = resultSet.getString("LASTNAME");				
					FullName fullName = new FullName(firstName, lastName, middleName); 
					
					BigInteger id = BigInteger.valueOf(resultSet.getInt("ID"));
					Position position = Position.valueOf(resultSet.getString("POSITION"));
					LocalDate hired = resultSet.getDate("HIREDATE").toLocalDate();
					BigDecimal salary = resultSet.getBigDecimal("SALARY");
			
				    return new Employee(id, fullName, position, hired, salary);											
				} catch (SQLException e) {
					throw new RuntimeException();
				}			    
			}
		};
    }
}
