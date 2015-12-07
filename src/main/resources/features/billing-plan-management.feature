Feature: The billing plan management feature allows a user to create, read, update, and delete billing plans.

  @billing-plan-management
  Scenario: Create a new billing plan.
    Given no billing plan with name 'Anytime Minutes' exists
    When I create a billing plan with name 'Anytime Minutes' and billing rate 0.10 per minute
    Then the billing plan can be found by name 'Anytime Minutes'
    And the billing plan rate is 0.10 per minute


