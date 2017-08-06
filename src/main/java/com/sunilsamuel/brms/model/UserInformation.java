package com.sunilsamuel.brms.model;

import java.io.Serializable;

/**
 * Class that will hold the user information to determine loan eligibility.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 *
 */
public class UserInformation implements Serializable {
	private static final long serialVersionUID = -2522864986817685221L;
	private String firstName;
	private String lastName;
	private Long identifier;
	private Integer age;
	private CollegeStatus collegeStatus;
	private Double familyIncome;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the identifier
	 */
	public Long getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the collegeStatus
	 */
	public CollegeStatus getCollegeStatus() {
		return collegeStatus;
	}

	/**
	 * @param collegeStatus
	 *            the collegeStatus to set
	 */
	public void setCollegeStatus(CollegeStatus collegeStatus) {
		this.collegeStatus = collegeStatus;
	}

	/**
	 * @return the familyIncome
	 */
	public Double getFamilyIncome() {
		return familyIncome;
	}

	/**
	 * @param familyIncome
	 *            the familyIncome to set
	 */
	public void setFamilyIncome(Double familyIncome) {
		this.familyIncome = familyIncome;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((collegeStatus == null) ? 0 : collegeStatus.hashCode());
		result = prime * result + ((familyIncome == null) ? 0 : familyIncome.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInformation other = (UserInformation) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (collegeStatus != other.collegeStatus)
			return false;
		if (familyIncome == null) {
			if (other.familyIncome != null)
				return false;
		} else if (!familyIncome.equals(other.familyIncome))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInformation [firstName=").append(firstName).append(", lastName=").append(lastName)
				.append(", identifier=").append(identifier).append(", age=").append(age).append(", collegeStatus=")
				.append(collegeStatus).append(", familyIncome=").append(familyIncome).append("]");
		return builder.toString();
	}
}
