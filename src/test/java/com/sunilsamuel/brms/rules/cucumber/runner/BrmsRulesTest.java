package com.sunilsamuel.brms.rules.cucumber.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * The test class that will instantiate all of the cucumber feature files.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/sunilsamuel/brms/rules/cucumber", glue = "com/sunilsamuel/brms/rules/cucumber/steps", plugin = {
		"pretty" }, monochrome = true, tags = { "~@Ignore" })
public class BrmsRulesTest {

}
