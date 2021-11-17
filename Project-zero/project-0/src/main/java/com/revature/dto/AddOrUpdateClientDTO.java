package com.revature.dto;

import java.util.Objects;

/*
 * DTO (Data Transfer Object): An object that contains properties, getters/setters and possibly an overridden equals + hashCode and toString method
 * That is used to encapsulate data and pass that data around as this single object
 * 	This data will be stored as instance variables/properties of that object.
 * 
 * Model - Is a type of DTO, but more specifically, it has all of the properties associated with the database representation
 * 	of a row in a table
 * 
 * For example, the Client class is a model of rows in the clients table
 * 
 */

// This is a DTO used to, in our case, add or update a client in the clients table
public class AddOrUpdateClientDTO {
	
	// This DTO does not have an ID, because to Add a new client or to update a previous already existing client,
	// we just need the core properties
	
	private String firstName;
	private String lastName;
	private String memberType;
	private int ssNumber;
	
	public AddOrUpdateClientDTO() {	
	}
	
	public AddOrUpdateClientDTO(String firstName, String lastName, String memberType, int ssNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberType = memberType;
		this.ssNumber = ssNumber;
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

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public int getSSNumber() {
		return ssNumber;
	}

	public void setSSNumber(int ssNumber) {
		this.ssNumber = ssNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ssNumber, memberType, firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrUpdateClientDTO other = (AddOrUpdateClientDTO) obj;
		return ssNumber == other.ssNumber && Objects.equals(memberType, other.memberType)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "AddOrUpdateClientDTO [firstName=" + firstName + ", lastName=" + lastName + ", memberType="
				+ memberType + ", ssNumber=" + ssNumber + "]";
	}
	
}