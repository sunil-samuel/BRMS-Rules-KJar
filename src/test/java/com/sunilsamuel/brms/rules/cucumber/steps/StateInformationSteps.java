package com.sunilsamuel.brms.rules.cucumber.steps;

import java.util.List;

import com.sunilsamuel.brms.model.CollegeStatus;
import com.sunilsamuel.brms.model.LoanAmount;
import com.sunilsamuel.brms.model.StateInformation;
import com.sunilsamuel.brms.model.UserInformation;
import com.sunilsamuel.brms.rules.command.RulesEngine;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class StateInformationSteps {
	private RulesEngine rulesEngineSupport;
	private StateInformation stateInformation;
	private List<LoanAmount> loanAmounts;

	public StateInformationSteps() {
		rulesEngineSupport = new RulesEngine();
	}

	@Given("^a total of (\\d+) loans for the state of (\\S+)$")
	public void createStateInformation(int totalLoans, String state) {
		stateInformation = new StateInformation();
		stateInformation.setTotalLoans(totalLoans);
		stateInformation.setState(state);
		rulesEngineSupport.addFact(stateInformation);
	}

	@Given("^user (\\d+) (\\S+) (\\S+) identified by (\\d+) from (\\S+) age (\\d+) who is a (\\S+) student with an income of \\$(\\d+)$")
	public void createUserInformation(int order, String first, String last, long id, String state, int age,
			CollegeStatus collegeStatus, double income) {
		UserInformation ui = new UserInformation();
		ui.setAge(age);
		ui.setCollegeStatus(collegeStatus);
		ui.setFamilyIncome(income);
		ui.setFirstName(first);
		ui.setIdentifier(id);
		ui.setLastName(last);
		ui.setOrder(order);
		ui.setState(state);
		rulesEngineSupport.addFact(ui);
	}

	@When("^I process loan amounts for states$")
	public void processStateLoans() {
		rulesEngineSupport.setDebug(false);
		rulesEngineSupport.fireRules();
		rulesEngineSupport.queryCommand("loanAmount", "Query LoanAmount");
		rulesEngineSupport.executeRules();
		loanAmounts = rulesEngineSupport.getLoanAmounts();
	}

	@Then("^user (\\d+) is eligible for \\$(\\d+)$")
	public void checkStateEligibility(int id, double loanAmount) {
		LoanAmount loan = getLoanAmountForId(id);
		Assert.assertNotNull(loan);
		Assert.assertEquals(loanAmount, loan.getLoanAmount().doubleValue(), 0.001);
	}

	@Then("^user (\\d+) is not eligible$")
	public void checkStateNotEligibility(long id) {
		LoanAmount loan = getLoanAmountForId(id);
		Assert.assertNotNull(loan);
		Assert.assertFalse(loan.getEligible());
	}

	private LoanAmount getLoanAmountForId(long id) {
		for (LoanAmount la : loanAmounts) {
			if (la.getUserIdentifier().longValue() == id) {
				return la;
			}
		}
		return null;
	}

}
