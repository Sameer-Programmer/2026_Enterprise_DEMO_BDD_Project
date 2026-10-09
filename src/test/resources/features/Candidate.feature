Feature: Candidate Management

  @work
  Scenario: Create and search for a candidate

    Given Launch the Browser
    When Navigate to the OrangeHRM login page
    And Login with valid credentials
    When User navigates to Recruitment
    And User clicks on Add Candidate
    And User enters candidate details
    And User selects the vacancy
    And User saves the candidate
    Then Candidate should be created successfully
    And User clicks on Recruitment tab
    When User searches for the created candidate
    Then Candidate should be displayed