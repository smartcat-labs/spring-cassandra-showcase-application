Feature: Account
  Account registration, sign in

  Scenario: Sign up via email address - happy path
    When a valid sign up via email address request is sent
    Then the response status code should be 201
    And account with that email address is created