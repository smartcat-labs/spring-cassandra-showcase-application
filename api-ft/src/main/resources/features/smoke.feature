@smoke-test
Feature: Smoke tests
  Non-destructive tests able to run in all environments including production

  Scenario: API responds to ping
    When a request to the health endpoint is sent
    Then it should report that status is UP
