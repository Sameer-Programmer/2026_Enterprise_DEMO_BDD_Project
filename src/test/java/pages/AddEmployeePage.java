package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddEmployeePage extends BasePage {
    public AddEmployeePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "firstName")
    WebElement txtFirstName;

    @FindBy(name = "middleName")
    WebElement txtMiddleName;

    @FindBy(name = "lastName")
    WebElement txtLastName;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    WebElement btnSave;

    @FindBy(xpath = "//h6[normalize-space()='Personal Details']")
    WebElement personalDetailsHeader;

    public void enterFirstName(String firstName) {
        txtFirstName.clear();
        txtFirstName.sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        txtMiddleName.clear();
        txtMiddleName.sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
    }

    public void clickSave() {
        waitHelper.waitForElementClickable(btnSave).click();
    }



    public boolean isPersonalDetailsDisplayed() {
        return waitHelper
                .waitForVisibility(personalDetailsHeader)
                .isDisplayed();
    }



}
