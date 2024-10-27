package com.epam.rd.autotasks.springemployeecatalog.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.epam.rd.autotasks.springemployeecatalog.domain.Position;

@Entity
@Table(name = "EMPLOYEE")
public class EmployeeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;
	
	@Embedded
	private FullNameEntity fullName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "POSITION")
	private Position position;
	
	@Column(name = "HIREDATE")
	private LocalDate hired;
	
	@Column(name = "SALARY")
	private BigDecimal salary;
	
	@OneToOne
	@JoinColumn(name = "MANAGER")
	private EmployeeEntity manager;
	
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT")
	private DepartmentEntity department;

	public EmployeeEntity(FullNameEntity fullName, Position position, LocalDate hired, BigDecimal salary,
			EmployeeEntity manager, DepartmentEntity department) {
		super();
		this.fullName = fullName;
		this.position = position;
		this.hired = hired;
		this.salary = salary;
		this.manager = manager;
		this.department = department;
	}
	
	public EmployeeEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FullNameEntity getFullName() {
		return fullName;
	}

	public void setFullName(FullNameEntity fullName) {
		this.fullName = fullName;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public LocalDate getHired() {
		return hired;
	}

	public void setHired(LocalDate hired) {
		this.hired = hired;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public EmployeeEntity getManager() {
		return manager;
	}

	public void setManager(EmployeeEntity manager) {
		this.manager = manager;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", fullName=" + fullName + ", position=" + position + ", hired=" + hired
				+ ", salary=" + salary + ", manager=" + manager + ", department=" + department + "]";
	}
	
}
