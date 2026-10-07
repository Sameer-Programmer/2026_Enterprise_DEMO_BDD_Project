package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AddCandidatePage extends BasePage {

    public AddCandidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@placeholder='First Name']")
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@placeholder='Middle Name']")
    WebElement txtMiddleName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement txtLastName;

    @FindBy(xpath = "//label[normalize-space()='Vacancy']/following::div[contains(normalize-space(),'-- Select --')][1]")
    WebElement drpVacancy;

    @FindBy(xpath = "(//span[normalize-space()='Senior QA Lead'])[1]")
    WebElement optionSeniorQALead;

    @FindBy(xpath = "//label[normalize-space()='Email']/following::input[@placeholder='Type here'][1]")
    WebElement txtEmail;

    @FindBy(xpath = "//label[normalize-space()='Contact Number']/following::input[@placeholder='Type here']")
    WebElement txtContactNumber;

    @FindBy(xpath = "//input[@placeholder='Enter comma seperated words...']")
    WebElement txtKeywords;

    @FindBy(xpath = "//textarea[@placeholder='Type here']")
    WebElement txtNotes;

    @FindBy(xpath = "(//button[normalize-space()='Save'])[1]")
    WebElement btnSave;

    @FindBy(xpath = "//p[contains(normalize-space(),'Successfully Saved')]")
    WebElement txtSuccessMessage;


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


    public void clickVacancyDropdown() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                drpVacancy
        );

        waitHelper.waitForElementClickable(drpVacancy);

        Actions actions = new Actions(driver);
        actions.moveToElement(drpVacancy).click().perform();
    }


    public void selectSeniorQALead() {
        waitHelper.waitForElementClickable(optionSeniorQALead).click();
    }

    public void enterEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void enterContactNumber(String contactNumber) {
        txtContactNumber.clear();
        txtContactNumber.sendKeys(contactNumber);
    }

    public void enterKeywords(String keywords) {
        txtKeywords.clear();
        txtKeywords.sendKeys(keywords);
    }

    public void enterNotes(String notes) {
        txtNotes.clear();
        txtNotes.sendKeys(notes);
    }

    public void clickSaveButton() {
        waitHelper.waitForElementClickable(btnSave).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return waitHelper
                .waitForVisibility(txtSuccessMessage)
                .isDisplayed();
    }

    public String getSuccessMessage() {
        waitHelper.waitForVisibility(txtSuccessMessage);
        return txtSuccessMessage.getText();
    }
}