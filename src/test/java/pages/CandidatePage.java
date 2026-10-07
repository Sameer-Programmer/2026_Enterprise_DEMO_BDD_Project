package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CandidatePage extends BasePage {

    public CandidatePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//span[contains(normalize-space(),'Recruitment')])[1]")
    WebElement recruitmentMenu;

    @FindBy(xpath = "//button[normalize-space()='Add']")
    WebElement btnAdd;

    @FindBy(xpath = "//h5[normalize-space()='Candidates']")
    WebElement txtCandidatesHeader;

    @FindBy(xpath = "//button[normalize-space()='Search']")
    WebElement btnSearch;

    public void clickRecruitment() {
        waitHelper.waitForElementClickable(recruitmentMenu).click();
    }

    public void clickAddButton() {
        waitHelper.waitForElementClickable(btnAdd).click();
    }

    public boolean isCandidatesHeaderDisplayed() {
        return waitHelper
                .waitForVisibility(txtCandidatesHeader)
                .isDisplayed();
    }

    public void clickSearchButton() {
        waitHelper.waitForElementClickable(btnSearch).click();
    }
}