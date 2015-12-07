package com.matisia.billing.at.steps;

import com.matisia.billing.at.transform.BillingRateTransformer;
import com.matisia.billing.entity.BillingPlan;
import com.matisia.billing.entity.BillingRate;
import com.matisia.billing.exception.ValidationException;
import com.matisia.billing.service.BillingPlanService;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

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

    @After("@billing-plan-management")
    public void deleteAllBillingPlans() {
        billingPlanService.deleteAll();
    }
}
