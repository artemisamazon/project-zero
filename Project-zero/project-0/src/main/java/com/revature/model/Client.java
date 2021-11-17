package com.revature.model;

import java.util.Objects;

// We will follow the conventions of a Java bean
// 	1. No-args constructor
//  2. All-args constructor
//  3. all private variables
//  4. getters-setters  
public class Client {

	private int id;
	private String firstName;
	private String lastName;
	private String memberType;
	private int ssNumber;
	
	public Client() {
		// super() is inserted implicitly. What this means is parent constructors are always called
		// Because Client is not explicitly inheriting any class, it is extending directly the Object class
		// Hence, the implicit super() is invoking the Object class constructor before running the rest of the code inside of this constructor
	}
	
	public Client(int id, String firstName, String lastName, String memberType, int ssNumber) {
		// super(); (implicit)
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.memberType = memberType;
		this.ssNumber = ssNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	
	/*
	 * Object class methods: override toString(), equals(), and hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(ssNumber, memberType, firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return ssNumber == other.ssNumber && Objects.equals(memberType, other.memberType)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", memberType="
				+ memberType + ", ssNumber=" + ssNumber + "]";
	}
	
}