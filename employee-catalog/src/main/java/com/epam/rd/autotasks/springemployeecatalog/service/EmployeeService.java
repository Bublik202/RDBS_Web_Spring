package com.epam.rd.autotasks.springemployeecatalog.service;

import java.util.List;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees(Integer page, Integer size, String sort);
	Employee getEmployeeById(Long employeeId, boolean fullChain);
	List<Employee> getEmployeesByManager(Long managerId, int page, int size, String sort);
    List<Employee> getEmployeesByDepartment(String departmentIdOrName, int page, int size, String sort);
}
