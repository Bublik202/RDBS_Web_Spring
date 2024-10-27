package com.epam.rd.autotasks.springemployeecatalog.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FullNameEntity {
	@Column(name = "FIRSTNAME")	
	private String firstName; 
	@Column(name = "LASTNAME")
	private String lastName;
	@Column(name = "MIDDLENAME")
	private String middleName;
	
	public FullNameEntity(String firstName, String lastName, String middleName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
	}
	
	public FullNameEntity() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String toString() {
		return "FullNameEntity [firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + "]";
	}
	
}
