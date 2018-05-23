package com.sunilsamuel.brms.model;

import java.io.Serializable;

/**
 * Class that will hold the state information pertaining to the loans.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 *
 */
public class StateInformation implements Serializable {
	private static final long serialVersionUID = 6138579310097564433L;

	private String state;
	private int totalLoans;

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the totalLoans
	 */
	public int getTotalLoans() {
		return totalLoans;
	}

	/**
	 * @param totalLoans
	 *            the totalLoans to set
	 */
	public void setTotalLoans(int totalLoans) {
		this.totalLoans = totalLoans;
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
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + totalLoans;
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
		StateInformation other = (StateInformation) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (totalLoans != other.totalLoans)
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
		builder.append("StateInformation [state=").append(state).append(", totalLoans=").append(totalLoans).append("]");
		return builder.toString();
	}
}
