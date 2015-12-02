package com.matisia.billing.at.steps;

import com.matisia.billing.entity.Account;
import com.matisia.billing.entity.BillingPlan;
import com.matisia.billing.entity.BillingRate;
import com.matisia.billing.exception.ValidationException;
import com.matisia.billing.service.AccountService;
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
 * Binds the account management steps to the application code.
 */
@ContextConfiguration(locations = {"classpath*:/cucumber-context.xml"})
public class AccountManagementSteps {

    @Autowired
    private AccountService accountService;

    private BillingPlan billingPlan = null;
    private Account account = null;

    @Given("^no account with phone number (.*) exists$")
    public void no_account_with_phone_number_exists(String phoneNumber) {
        Account account = accountService.findAccountByPhoneNumber(phoneNumber);
        assertNull(account);
    }

    @Given("a billing plan with name '(.*)' and billing rate '(.*)' per minute exists")
    public void a_billing_plan_with_name_and_billing_rate_per_minute_exists(String name, String rate) {
        billingPlan = new BillingPlan();
        BillingRate billingRate = new BillingRate(rate);
        billingPlan.setName(name);
        billingPlan.setBillingRate(billingRate);
    }

    @When("^I create a new account with phone number (.*) and billing plan '(.*)'$")
    public void i_create_a_new_account_with_phone_number_and_billing_plan(String phoneNumber, String billingPlanName) throws ValidationException {
        Account newAccount = new Account();
        newAccount.setPhoneNumber(phoneNumber);
        newAccount.setBillingPlan(billingPlan);

        account = accountService.createAccount(newAccount);

        assertNotNull(account);
    }

    @Then("^the account can be found by the phone number (.*)$")
    public void the_account_can_be_found_by_the_phone_number(String phoneNumber) {
        account = accountService.findAccountByPhoneNumber(phoneNumber);

        assertNotNull(account);

    }

    @Then("^the name of the account billing plan is '(.*)'")
    public void the_name_of_the_account_billing_plan_is(String billingPlanName) {
        assertEquals(billingPlanName, account.getBillingPlan().getName());
    }

    @After("@account-management")
    public void delete_all_accounts() {
        accountService.deleteAll();
    }
}
