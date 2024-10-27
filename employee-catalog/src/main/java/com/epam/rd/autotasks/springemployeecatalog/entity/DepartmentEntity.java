package com.epam.rd.autotasks.springemployeecatalog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT")
public class DepartmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column
	private String name;
	@Column(name = "location")
	private String location;
	
	public DepartmentEntity(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	
	public DepartmentEntity() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "DepartmentEntity [id=" + id + ", name=" + name + ", location=" + location + "]";
	}
	
}
