package stepdefinations;

import io.cucumber.java.en.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class LoginSteps {

    LoginPage loginPage;
    DashboardPage dashboardPage;
    Dotenv dotenv = Dotenv.load();

    @Given("Launch the Browser")
    public void launch_the_browser() {

        System.out.println("Browser launched by Hooks");
    }

    @When("Navigate to the OrangeHRM login page")
    public void navigate_to_the_orange_hrm_login_page() {

        String url = ConfigReader.getProperty("dev.url");

        DriverFactory.getDriver().get(url);

        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @And("Login with valid credentials")
    public void login_with_valid_credentials() {

        String username = dotenv.get("DEV_USERNAME");
        String password = dotenv.get("DEV_PASSWORD");

        loginPage.setUserName(username);
        loginPage.setPassword(password);
        loginPage.clickLogin();

        dashboardPage =
                new DashboardPage(DriverFactory.getDriver());

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard is not displayed after login"
        );
    }

    @Then("Dashboard should be displayed")
    public void dashboard_should_be_displayed() {

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard is not displayed"
        );
    }

    @And("Logout")
    public void logout() {

        dashboardPage.clickLogout();
    }

    @Then("Login page should be displayed")
    public void login_page_should_be_displayed() {

        Assert.assertTrue(
                DriverFactory.getDriver()
                        .getCurrentUrl()
                        .contains("/auth/login"),
                "Login page is not displayed"
        );
    }
}