package com.epam.rd.autotasks.springemployeecatalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.epam.rd.autotasks.springemployeecatalog.entity.EmployeeEntity;

public interface EmployeeEntityRepository extends PagingAndSortingRepository<EmployeeEntity, Long>{
	Page<EmployeeEntity> findByManagerId(Pageable pageable, Long managerId);
	Page<EmployeeEntity> findByDepartmentId(Pageable pageable, Long departmentId);
	Page<EmployeeEntity> findByDepartmentName(Pageable pageable, String departmentName);
}
