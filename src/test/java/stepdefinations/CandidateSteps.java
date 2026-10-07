package stepdefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AddCandidatePage;
import pages.CandidatePage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.TestDataManager;

import java.io.IOException;
import java.util.Map;

public class CandidateSteps {

    CandidatePage candidatePage;
    AddCandidatePage addCandidatePage;
    Map<String, String> candidateData;



    @When("User navigates to Recruitment")
    public void user_navigates_to_recruitment() {
        candidatePage = new CandidatePage(DriverFactory.getDriver());
        candidatePage.clickRecruitment();
    }


    @And("User clicks on Add Candidate")
    public void user_clicks_on_add_candidate() {

        candidatePage.clickAddButton();

        addCandidatePage =
                new AddCandidatePage(DriverFactory.getDriver());
    }


    @And("User enters candidate details")
    public void user_enters_candidate_details()
            throws IOException {

        candidateData = TestDataManager.getCandidateData();
        addCandidatePage.enterFirstName(candidateData.get("firstName"));
        addCandidatePage.enterMiddleName(candidateData.get("middleName"));
        addCandidatePage.enterLastName(candidateData.get("lastName"));
        addCandidatePage.enterEmail(candidateData.get("email"));
        addCandidatePage.enterContactNumber(candidateData.get("contactNumber"));
        addCandidatePage.enterKeywords(candidateData.get("KeyWors"));
        addCandidatePage.enterNotes(candidateData.get("Notes"));
    }


    @And("User selects the vacancy")
    public void user_selects_the_vacancy() {

        addCandidatePage.clickVacancyDropdown();
        addCandidatePage.selectSeniorQALead();
    }


    @And("User saves the candidate")
    public void user_saves_the_candidate() {

        addCandidatePage.clickSaveButton();
    }


    @Then("Candidate should be created successfully")
    public void candidate_should_be_created_successfully() {

        Assert.assertTrue(
                addCandidatePage.isSuccessMessageDisplayed(),
                "Candidate was not created successfully"
        );
    }

    @And("User clicks on Recruitment tab")
    public void user_clicks_on_recruitment_tab() {
        candidatePage.clickRecruitment();
    }


    @When("User searches for the created candidate")
    public void user_searches_for_the_created_candidate() {

        candidatePage =new CandidatePage(DriverFactory.getDriver());
        candidatePage.clickSearchButton();
    }


    @Then("Candidate should be displayed")
    public void candidate_should_be_displayed() {

        Assert.assertTrue(candidatePage.isCandidatesHeaderDisplayed(),
                "Candidate is not displayed"
        );
    }
}