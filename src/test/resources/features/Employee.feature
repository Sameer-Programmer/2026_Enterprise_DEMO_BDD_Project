Feature: Employee Management

  @sanity
  Scenario: Add a new employee
    Given Launch the Browser
    When Navigate to the OrangeHRM login page
    And Login with valid credentials
    And User navigates to PIM page
    And User clicks on Add Employee
    And User enters employee details
    And User saves the employee
    Then Employee personal details page should be displayed
