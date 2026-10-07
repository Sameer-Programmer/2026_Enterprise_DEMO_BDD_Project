package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h6[text()='Dashboard']")
    WebElement dashboardText;

    @FindBy(css = ".oxd-topbar-header-userarea")
    WebElement userProfile;

    @FindBy(xpath = "//a[contains(normalize-space(),'Logout')]")
    WebElement lnkLogout;

    @FindBy(xpath = "(//span[contains(normalize-space(),'Recruitment')])[1]")
    WebElement recruitmentMenu;

    @FindBy(xpath = "//a[contains(@href,'/pim/viewPimModule')]")
    WebElement pimMenu;


    public boolean isDashboardDisplayed() {

        return waitHelper
                .waitForVisibility(dashboardText)
                .isDisplayed();
    }


    public void clickLogout() {

        waitHelper
                .waitForElementClickable(userProfile)
                .click();

        waitHelper
                .waitForVisibility(lnkLogout);

        lnkLogout.click();
    }


    public void clickRecruitment() {

        waitHelper
                .waitForVisibility(recruitmentMenu);

        recruitmentMenu.click();
    }

    public void clickPIM() {
        waitHelper.waitForElementClickable(pimMenu).click();
    }
}