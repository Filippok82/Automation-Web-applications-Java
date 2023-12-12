package lesson02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "li.mdc-menu-surface--anchor")
    WebElement helloUserField;
    @FindBy(css = "#create-btn")
    WebElement buttonPlus;
    @FindBy(css = ".mdc-text-field .mdc-text-field__input")
    WebElement nameGroupField;
    @FindBy(css = "#update-item > div.submit > button")
    WebElement buttonSaveGroup;
    @FindBy(css = ".form-modal-header button")
    WebElement buttonCloseModalWindow;
    @FindBy(css = "div.mdc-data-table")
    WebElement tableTitleCss;
    @FindBy(xpath = "//table[@aria-label='Tutors list']/tbody/tr")
    List<WebElement> listRow;


    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public String getHelloUser() {
        return wait.until(ExpectedConditions.visibilityOf(helloUserField)).getText();

    }

    private void creatingNewGroup(String nameGroupText) {
        wait.until(ExpectedConditions.visibilityOf(buttonPlus));
        buttonPlus.click();

        wait.until(ExpectedConditions.visibilityOf(nameGroupField));
        nameGroupField.sendKeys(nameGroupText);

        wait.until(ExpectedConditions.textToBePresentInElementValue(nameGroupField, nameGroupText));

        wait.until(ExpectedConditions.visibilityOf(buttonSaveGroup));
        buttonSaveGroup.click();

        wait.until(ExpectedConditions.visibilityOf(buttonCloseModalWindow));
        buttonCloseModalWindow.click();


    }

    public void successCreatingNewGroup(String nameGroupText) {
        creatingNewGroup(nameGroupText);
        wait.until(ExpectedConditions.textToBePresentInElement(tableTitleCss, nameGroupText));
    }

    private TableRow getRow(String nameGroupText) {
        return listRow.stream()
                .map(TableRow::new)
                .filter(x -> x.getTitle().equals(nameGroupText))
                .findFirst().orElseThrow();
    }
    public String getStatusRow(String nameGroupText){
        return getRow(nameGroupText).getStatus();

    }

    public void successDelete(String nameGroupText){
        getRow(nameGroupText).clickDelete("delete");
        getRow(nameGroupText).waitRestore("restore_from_trash");
    }

    public void successRestore(String nameGroupText) {
        getRow(nameGroupText).clickDelete("restore_from_trash");
        getRow(nameGroupText).waitRestore("delete");
    }

}