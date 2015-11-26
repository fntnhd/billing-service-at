package com.example.billing.at;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Runs the Cucumber tests
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json", "html:target/site/cucumber-pretty"}, features= {"classpath:features"}, glue = {"com.example.billing.at.steps"})
public class AccountManagementTest {

}
