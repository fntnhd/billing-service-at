Feature: The account management feature allows a user to create, read, update, and delete accounts.

  Scenario: Create a new account.
    Given no account with phone number 703-555-1234 exists
    And a billing plan with name 'Anytime Minutes' and billing rate '0.10' per minute exists
    When I create a new account with phone number 703-555-1234 and billing plan 'Anytime Minutes'
    Then the account can be found by the phone number 703-555-1234
    And the name of the account billing plan is 'Anytime Minutes'

