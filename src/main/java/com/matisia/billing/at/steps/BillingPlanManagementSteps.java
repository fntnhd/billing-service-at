package com.matisia.billing.at.steps;

import com.matisia.billing.at.transform.BillingRateTransformer;
import com.matisia.billing.entity.Account;
import com.matisia.billing.entity.BillingPlan;
import com.matisia.billing.entity.BillingRate;
import com.matisia.billing.exception.ValidationException;
import com.matisia.billing.service.AccountService;
import com.matisia.billing.service.BillingPlanService;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Makes the billing-plan-management scenarios executable
 */
@ContextConfiguration(locations = {"classpath*:/cucumber-context.xml"})
public class BillingPlanManagementSteps {

    @Autowired
    private BillingPlanService billingPlanService;

    @Autowired
    private AccountService accountService;

    private BillingPlan billingPlan;

    @Given("^no billing plan with name '(.*)' exists$")
    public void no_billing_plan_with_name_exists(String billingPlanName) {
        BillingPlan billingPlan = billingPlanService.findBillingPlanByName(billingPlanName);
        assertNull(billingPlan);
    }

    @When("^I create a billing plan with name '(.*)' and billing rate (.*) per minute$")
    public void i_create_a_billing_plan_with_name_and_billing_rate_per_minute(String billingPlanName,
                                                                              @Transform(BillingRateTransformer.class) BillingRate billingRate) throws ValidationException {
        BillingPlan billingPlan = new BillingPlan();
        billingPlan.setName(billingPlanName);
        billingPlan.setBillingRate(billingRate);

        billingPlanService.createBillingPlan(billingPlan);
    }

    @Then("^the billing plan can be found by name '(.*)'$")
    public void the_billing_plan_can_be_found_by_name(String billingPlanName) {
        billingPlan = billingPlanService.findBillingPlanByName(billingPlanName);

        assertNotNull(billingPlan);
    }

    @Then("^the billing plan rate is (.*) per minute$")
    public void the_billing_plan_rate_is_per_minute(@Transform(BillingRateTransformer.class) BillingRate expectedBillingRate) {
        assertEquals(expectedBillingRate, billingPlan.getBillingRate());
    }

    @Given("^a billing plan with name \"([^\"]*)\" exists$")
    public void a_billing_plan_with_name_exists(String billingPlanName) throws ValidationException {
        BillingPlan billingPlan = new BillingPlan();
        billingPlan.setName(billingPlanName);
        billingPlan.setBillingRate(new BillingRate("0.08"));
        billingPlanService.createBillingPlan(billingPlan);
    }

    @Given("^no accounts use the billing plan with name \"([^\"]*)\"$")
    public void no_accounts_use_the_billing_plan_with_name(String billingPlanName) {
        Collection<Account> accounts = accountService.findAll();
        assertEquals(0,accounts.size());
    }

    @When("^I delete the billing plan with name \"([^\"]*)\"$")
    public void i_delete_the_billing_plan_with_name(String billingPlanName) throws ValidationException {
        BillingPlan billingPlan = billingPlanService.findBillingPlanByName(billingPlanName);
        billingPlan = billingPlanService.deleteBillingPlan(billingPlan);
    }

    @When("^I find the billing plan with name \"([^\"]*)\"$")
    public void i_find_the_billing_plan_with_name(String billingPlanName) {
        billingPlan = billingPlanService.findBillingPlanByName(billingPlanName);
    }

    @Then("^the billing plan with name \"([^\"]*)\" does not exist$")
    public void the_billing_plan_with_name_does_not_exist(String billingPlanName) {
        assertNull(billingPlan);
    }


    @After("@billing-plan-management")
    public void deleteAllBillingPlans() {
        billingPlanService.deleteAll();
    }
}
