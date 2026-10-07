package stepdefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.EmployeePage;
import utils.DriverFactory;
import utils.TestDataManager;

import java.io.IOException;
import java.util.Map;

public class EmployeeSteps {

    DashboardPage dashboardPage;
    EmployeePage employeePage;
    AddEmployeePage addEmployeePage;

    Map<String, String> employeeData;


    @And("User navigates to PIM page")
    public void user_navigates_to_pim_page() {

        dashboardPage =
                new DashboardPage(DriverFactory.getDriver());

        dashboardPage.clickPIM();

        employeePage =
                new EmployeePage(DriverFactory.getDriver());
    }


    @And("User clicks on Add Employee")
    public void user_clicks_on_add_employee() {

        employeePage.clickAddEmployee();

        addEmployeePage = new AddEmployeePage(DriverFactory.getDriver());
    }


    @And("User enters employee details")
    public void user_enters_employee_details()
            throws IOException {

        employeeData = TestDataManager.getEmployeeData();

        addEmployeePage.enterFirstName(
                employeeData.get("EmployeeFirstname")
        );

        addEmployeePage.enterMiddleName(
                employeeData.get("EmployeeMiddleName")
        );

        addEmployeePage.enterLastName(
                employeeData.get("EmployeeLastName")
        );
    }


    @And("User saves the employee")
    public void user_saves_the_employee() {

        addEmployeePage.clickSave();
    }


    @Then("Employee personal details page should be displayed")
    public void employee_personal_details_page_should_be_displayed() {

        Assert.assertTrue(
                addEmployeePage.isPersonalDetailsDisplayed(),
                "Employee personal details page is not displayed"
        );
    }
}