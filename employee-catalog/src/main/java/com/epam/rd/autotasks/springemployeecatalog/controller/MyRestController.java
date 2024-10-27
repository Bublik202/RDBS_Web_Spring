package com.epam.rd.autotasks.springemployeecatalog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/employees")
public class MyRestController {
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping
	public List<Employee> getAllEmployees(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) String sort) {	
		return employeeService.getAllEmployees(page, size, sort);
	}
	
	@GetMapping("/{employee_id}")
	public Employee getEmployeeById(
			@PathVariable Long employee_id,
			@RequestParam(value="full_chain", defaultValue = "false") boolean fullChain) {		
		return employeeService.getEmployeeById(employee_id, fullChain);
	}
	
	@GetMapping("/by_manager/{managerId}")
	public List<Employee> getEmployeesByManager(
			@PathVariable Long managerId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "fullName.lastName") String sort) {		
		return employeeService.getEmployeesByManager(managerId, page, size, sort);
		
	}
	
	@GetMapping("/by_department/{department}")
	public List<Employee> getEmploeesByDepartment(
			@PathVariable String department,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "fullName.lastName") String sort) {		
		return employeeService.getEmployeesByDepartment(department, page, size, sort);
		
	}
	

}