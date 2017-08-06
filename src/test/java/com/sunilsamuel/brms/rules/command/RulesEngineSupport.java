package com.sunilsamuel.brms.rules.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.drools.core.event.DefaultAgendaEventListener;
import org.drools.core.runtime.rule.impl.FlatQueryResults;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.internal.command.CommandFactory;

/**
 * Execute a BRMS rule given a set of facts. This class uses the command factory
 * to process the rules.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 *
 */
public class RulesEngineSupport {

	private boolean debug = false;

	private KieContainer kieContainer;
	List<Command<?>> commands;
	ExecutionResults results;

	/**
	 * Use the kie service factory to get the kie container.
	 */
	public RulesEngineSupport() {
		kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		commands = new ArrayList<Command<?>>();
	}

	/**
	 * Set the debug option that will enable debug during the rules processing.
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * Insert the objects as BRMS facts that will be used to run the rules.
	 * 
	 * @param auditFileName
	 *            Audit file name
	 * @param facts
	 *            The objects to insert into the BRMS engine
	 */
	public void initializeProcess(String auditFileName, Object... facts) {
		for (Object fact : facts) {
			commands.add(CommandFactory.newInsert(fact));
		}
		commands.add(CommandFactory.newEnableAuditLog(auditFileName));
		commands.add(CommandFactory.newFireAllRules());
	}

	/**
	 * Create the query command so that the objects can be retrieved from the BRMS
	 * engine.
	 * 
	 * @param queryIdentifier
	 *            The variable identifier used within the query.
	 * @param queryName
	 *            The name of the query
	 */
	public void queryCommand(String queryIdentifier, String queryName) {
		commands.add(CommandFactory.newQuery(queryIdentifier, queryName));
	}

	/**
	 * Execute the rules and store the results to be used to retrieve the response.
	 */
	public void executeRules() {
		StatelessKieSession ksession = this.kieContainer.newStatelessKieSession();
		if (debug) {
			ksession.addEventListener(new DefaultAgendaEventListener() {
				public void afterMatchFired(AfterMatchFiredEvent event) {
					super.afterMatchFired(event);
					System.out.println(event);
				}
			});
		}
		BatchExecutionCommand batch = CommandFactory.newBatchExecution(commands);
		results = ksession.execute(batch);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<?> get(String queryIdentifier, T clazz) {
		FlatQueryResults queryResults = (FlatQueryResults) results.getValue(queryIdentifier);
		if (queryResults == null) {
			return null;
		}
		Iterator<QueryResultsRow> iterator = queryResults.iterator();
		return (List<T>) (iterator.hasNext() ? iterator.next().get(queryIdentifier) : null);
	}

}
