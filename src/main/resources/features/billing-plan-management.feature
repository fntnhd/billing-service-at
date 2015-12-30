Feature: The billing plan management feature allows a user to create, read, update, and delete billing plans.

  @billing-plan-management
  Scenario: Create a new billing plan.
    Given no billing plan with name 'Anytime Minutes' exists
    When I create a billing plan with name 'Anytime Minutes' and billing rate 0.10 per minute
    Then the billing plan can be found by name 'Anytime Minutes'
    And the billing plan rate is 0.10 per minute


  @billing-plan-management
  Scenario: Delete an unreferenced billing plan
    Given a billing plan with name "Friends and Family" exists
    And no accounts use the billing plan with name "Friends and Family"
    When I delete the billing plan with name "Friends and Family"
    And I find the billing plan with name "Friends and Family"
    Then the billing plan with name "Friends and Family" does not exist

