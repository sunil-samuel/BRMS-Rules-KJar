package com.sunilsamuel.brms.rules.cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * The test class that will instantiate all of the cucumber feature files.
 * 
 * @author Sunil Samuel (web_github@sunilsamuel.com)
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true, features = "src/test/resources/com/sunilsamuel/brms/rules/cucumber", glue = "com/sunilsamuel/brms/rules/cucumber/steps", plugin = {
		"pretty" }, monochrome = true, tags = { "not @Ignore" })
public class BrmsRulesTest {

}
