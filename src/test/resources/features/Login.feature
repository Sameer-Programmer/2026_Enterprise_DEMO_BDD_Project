
Feature: OrangeHRM Login


  @sanity
  Scenario: Successful login with valid credentials
    Given Launch the Browser
    When Navigate to the OrangeHRM login page
    And Login with valid credentials
    Then Dashboard should be displayed
    And Logout

  @sanity
  Scenario: Verify OrangeHRM login page
    Given Launch the Browser
    When Navigate to the OrangeHRM login page
    Then Login page should be displayed
