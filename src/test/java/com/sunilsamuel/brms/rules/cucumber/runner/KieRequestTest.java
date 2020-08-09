package com.sunilsamuel.brms.rules.cucumber.runner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;

import com.sunilsamuel.brms.model.CollegeStatus;
import com.sunilsamuel.brms.model.LoanAmount;
import com.sunilsamuel.brms.model.UserInformation;

/**
 * A sample code that uses the kie-server-client APIs to make JSON ReSTful calls
 * to the kie-server. Send several facts and execute the rules.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 * 
 *
 */
@Ignore
public class KieRequestTest {

	private static final String KIE_SERVER_URL = "http://localhost:8080/kie-server/services/rest/server";

	private static final String USERNAME = "sunil";
	private static final String PASSWORD = "password";
	private static final String STATELESS_KIE_SESSION_ID = "defaultStatelessKieSession";
	private static final String CONTAINER_ID = "brms-rules";

	@SuppressWarnings("unchecked")
	@Test
	public void getRequest() {

		KieServices kieServices = KieServices.Factory.get();

		/**
		 * Create a configuration objecgt with the url, username, and password.
		 */
		KieServicesConfiguration kieServicesConfig = KieServicesFactory.newRestConfiguration(KIE_SERVER_URL, USERNAME,
				PASSWORD);

		/**
		 * Set the content type to JSON.
		 */
		kieServicesConfig.setMarshallingFormat(MarshallingFormat.JSON);

		KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);

		/**
		 * Get the RulesServicesClient
		 */
		RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

		/**
		 * Create a list that will hold all of the commands sent to the kie-server.
		 */
		List<Command<?>> commands = new ArrayList<>();

		KieCommands commandFactory = kieServices.getCommands();
		// The identifiers that we provide in the insert commands can later be used to
		// retrieve the object from the response.
		/**
		 * Add the commands facts into the commands.
		 */
		commands.add(commandFactory.newInsert(get("Sunil", "Samuel", 123, "MD", 14, CollegeStatus.FullTime, 42000)));
		commands.add(commandFactory.newInsert(get("Joel", "Samuel", 421231, "MD", 21, CollegeStatus.PartTime, 54043)));
		commands.add(commandFactory.newInsert(get("Jake", "Samuel", 123455, "MD", 21, CollegeStatus.FullTime, 76000)));
		/**
		 * Insert the command to fire all rules.
		 */
		commands.add(commandFactory.newFireAllRules());
		/**
		 * Insert the command to get 'query' after the rule is run using an identifier.
		 */
		commands.add(commandFactory.newQuery("loanAmounts", "Query LoanAmount"));
		/**
		 * Create the batch commands using the kie session information.
		 */
		BatchExecutionCommand batchExecutionCommand = commandFactory.newBatchExecution(commands,
				STATELESS_KIE_SESSION_ID);
		/**
		 * Execute the commands against the container id.
		 */
		ServiceResponse<ExecutionResults> response = rulesClient.executeCommandsWithResults(CONTAINER_ID,
				batchExecutionCommand);

		ExecutionResults results = response.getResult();

		// We can retrieve the objects from the response using the identifiers we
		// specified in the Insert commands.
		// List<LoanAmount> loanAmounts = (List<LoanAmount>)
		// results.getValue("loanAmounts");
		QueryResults loanAmounts = (QueryResults) results.getValue("loanAmounts");
		Iterator<QueryResultsRow> it = loanAmounts.iterator();
		while (it.hasNext()) {
			QueryResultsRow value = it.next();
			List<LoanAmount> values = (List<LoanAmount>) value.get("loanAmount");
			System.out.println("VALUE [" + values + "]");
		}
	}

	public UserInformation get(String first, String last, long id, String state, int age, CollegeStatus status,
			double income) {
		UserInformation ui = new UserInformation();
		ui.setFirstName(first);
		ui.setLastName(last);
		ui.setIdentifier(id);
		ui.setState(state);
		ui.setAge(age);
		ui.setCollegeStatus(status);
		ui.setFamilyIncome(income);
		return ui;
	}

}
