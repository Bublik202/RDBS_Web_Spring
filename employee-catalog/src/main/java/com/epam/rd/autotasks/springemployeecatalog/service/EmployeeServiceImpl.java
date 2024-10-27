package com.epam.rd.autotasks.springemployeecatalog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.epam.rd.autotasks.springemployeecatalog.domain.Department;
import com.epam.rd.autotasks.springemployeecatalog.domain.Employee;
import com.epam.rd.autotasks.springemployeecatalog.domain.FullName;
import com.epam.rd.autotasks.springemployeecatalog.entity.DepartmentEntity;
import com.epam.rd.autotasks.springemployeecatalog.entity.EmployeeEntity;
import com.epam.rd.autotasks.springemployeecatalog.repository.EmployeeEntityRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeEntityRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees(Integer page, Integer size, String sort) {		
	    if (sort != null && sort.equals("lastName")) {	       
	        sort = "fullName.lastName";
	    }
	    List<EmployeeEntity> employeeEntities;	   
	    if(page == null || size == null) {
	    	Iterable<EmployeeEntity> employeeIterable = employeeRepository.findAll();
	    	Stream<EmployeeEntity> employeeStream = StreamSupport.stream(employeeIterable.spliterator(), false);	    	
	    	employeeEntities = employeeStream.collect(Collectors.toList());
	    }else {
	    	Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
	        Page<EmployeeEntity> employeePage = employeeRepository.findAll(pageable);
	        employeeEntities = employeePage.getContent();
	    }
	    	        
		return employeeEntities.stream()
				.map(this::convertToEmployee)
				.collect(Collectors.toList());
	}	
	
	@Override
	public Employee getEmployeeById(Long employeeId, boolean fullChain) {
		Optional<EmployeeEntity> empployeeOptional = employeeRepository.findById(employeeId);
		EmployeeEntity employeeEntity = empployeeOptional.orElse(null);	
		return convertToEmployee(employeeEntity, fullChain);
	}

	@Override
	public List<Employee> getEmployeesByManager(Long managerId, int page, int size, String sort) {
		if (sort.equals("lastName")) {	       
	        sort = "fullName.lastName";
	    }	
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		Page<EmployeeEntity> employeePage = employeeRepository.findByManagerId(pageable, managerId);
		List<EmployeeEntity> employeeEntities = employeePage.getContent();
		
		return employeeEntities.stream()
				.map(this::convertToEmployee)
				.collect(Collectors.toList());
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department, int page, int size, String sort) {
		if (sort.equals("lastName")) {	       
	        sort = "fullName.lastName";
	    }
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		List<EmployeeEntity> employeeEntities = department.matches("\\d+") 
		? employeeRepository.findByDepartmentId(pageable, Long.valueOf(department)).getContent()
				: employeeRepository.findByDepartmentName(pageable, department).getContent();
		
		return employeeEntities.stream()
				.map(this::convertToEmployee)
				.collect(Collectors.toList());
	}
		
	private Employee convertToEmployee(EmployeeEntity employeeEntity) {
	    return convertToEmployee(employeeEntity, false);
	}

	private Employee convertToEmployee(EmployeeEntity employeeEntity, boolean fullChain) {	
		if (employeeEntity == null) {
			return null;
		}		
		
		Employee manager = Optional.ofNullable(employeeEntity.getManager())
			.map(managerEntity -> employeeRepository.findById(managerEntity.getId()))
			.orElse(Optional.empty())
			.map(managerEmployee -> {
				if(fullChain) {
					return convertToEmployee(employeeRepository.findById(managerEmployee.getId()).orElse(null), fullChain);
					
				}
				
				return new Employee(managerEmployee.getId(),
	                    new FullName(
	                    		managerEmployee.getFullName().getFirstName(),
	                    		managerEmployee.getFullName().getLastName(), 
	                    		managerEmployee.getFullName().getMiddleName()),
	                    managerEmployee.getPosition(),
	                    managerEmployee.getHired(),
	                    managerEmployee.getSalary(),
	                    null, 
	                    mapToDepartment(managerEmployee.getDepartment()));				
			})
			.orElse(null);
		
		return new Employee(
				employeeEntity.getId(),
				new FullName(
						employeeEntity.getFullName().getFirstName(),
						employeeEntity.getFullName().getLastName(), 
						employeeEntity.getFullName().getMiddleName()), 
				employeeEntity.getPosition(), 
				employeeEntity.getHired(), 
				employeeEntity.getSalary(), 
				manager,
				employeeEntity.getDepartment() != null ? mapToDepartment(employeeEntity.getDepartment()) : null);				
	}

	private Department mapToDepartment(DepartmentEntity departmentEntity) {		
		if(departmentEntity == null) {
			return null;
		}
		return new Department(
				departmentEntity.getId(), 
				departmentEntity.getName(), 
				departmentEntity.getLocation());
	}

}
