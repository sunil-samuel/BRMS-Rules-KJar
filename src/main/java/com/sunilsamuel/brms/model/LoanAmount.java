package com.sunilsamuel.brms.model;

import java.io.Serializable;

/**
 * The object that will hold the loan amount and eligibility for a given user.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 *
 */
public class LoanAmount implements Serializable {
	private static final long serialVersionUID = -595679900855536665L;
	private Long userIdentifier;
	private Double loanAmount;
	private Boolean eligible;

	public LoanAmount() {

	}

	public LoanAmount(Long userIdentifier, Double loanAmount, Boolean eligible) {
		super();
		this.userIdentifier = userIdentifier;
		this.loanAmount = loanAmount;
		this.eligible = eligible;
	}

	/**
	 * @return the userIdentifier
	 */
	public Long getUserIdentifier() {
		return userIdentifier;
	}

	/**
	 * @param userIdentifier
	 *            the userIdentifier to set
	 */
	public void setUserIdentifier(Long userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	/**
	 * @return the loanAmount
	 */
	public Double getLoanAmount() {
		return loanAmount;
	}

	/**
	 * @param loanAmount
	 *            the loanAmount to set
	 */
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	/**
	 * @return the eligible
	 */
	public Boolean getEligible() {
		return eligible;
	}

	/**
	 * @param eligible
	 *            the eligible to set
	 */
	public void setEligible(Boolean eligible) {
		this.eligible = eligible;
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
		result = prime * result + ((eligible == null) ? 0 : eligible.hashCode());
		result = prime * result + ((loanAmount == null) ? 0 : loanAmount.hashCode());
		result = prime * result + ((userIdentifier == null) ? 0 : userIdentifier.hashCode());
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
		LoanAmount other = (LoanAmount) obj;
		if (eligible == null) {
			if (other.eligible != null)
				return false;
		} else if (!eligible.equals(other.eligible))
			return false;
		if (loanAmount == null) {
			if (other.loanAmount != null)
				return false;
		} else if (!loanAmount.equals(other.loanAmount))
			return false;
		if (userIdentifier == null) {
			if (other.userIdentifier != null)
				return false;
		} else if (!userIdentifier.equals(other.userIdentifier))
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
		builder.append("LoanAmount [userIdentifier=").append(userIdentifier).append(", loanAmount=").append(loanAmount)
				.append(", eligible=").append(eligible).append("]");
		return builder.toString();
	}
}
