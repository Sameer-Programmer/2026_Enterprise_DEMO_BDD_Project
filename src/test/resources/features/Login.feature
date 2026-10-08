
Feature: OrangeHRM Login


  @sanity @work
  Scenario: Successful login with valid credentials
    Given Launch the Browser
    When Navigate to the OrangeHRM login page
    And Login with valid credentials
    Then Dashboard should be displayed
    And Logout

  @sanity @work
  Scenario: Verify OrangeHRM login page
    Given Launch the Browser
    When Navigate to the OrangeHRM login page
    Then Login page should be displayed
