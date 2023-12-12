package homework02;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainPage {

    WebDriver driver;
    private final WebDriverWait wait;


    @FindBy(css = ".error-block.svelte-uwkxn9 p.svelte-uwkxn9")
    WebElement textError;
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
    @FindBy(xpath = "//*[@id='app']/main/div/div/div[1]/div[1]/table/tbody/tr[1]/td[4]/button[1]/i")
    WebElement buttonAddNumbers;
    @FindBy(css = "#generate-logins > div.field > label > input")
    WebElement numbersStudentField;
    @FindBy(xpath = "//*[@id='generate-logins']/div[2]/button/span")
    WebElement buttonSaveNumbers;
    @FindBy(xpath = "//*[@id='app']/main/div/div/div[3]/div[2]/div/div[1]/button")
    WebElement buttonCloseModalAddStudent;
    @FindBy(xpath = "//*[@id='app']/main/div/div/div[1]/div[1]/table/tbody/tr[1]/td[4]/button[1]/span/span")
    WebElement iconStudentCount;
    @FindBy(xpath = "//*[@id='app']/main/div/div/div[1]/div[1]/table/tbody/tr[1]/td[4]/button[3]")
    WebElement iconStudent;
    @FindBy(xpath = "//table[@aria-label='User list']/tbody/tr")
    List<WebElement> rowsInStudentTable;
    @FindBy(xpath = "//*[@id='large-scroll-content']/div/div[1]/table/tbody/tr[1]/td[2]")
    WebElement nameStudentFirst;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }


    public String getTextError() {
        return wait.until(ExpectedConditions.visibilityOf(textError)).getText();

    }

    public void creatingNewGroup(String nameGroupText) {
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

    public String getStatusRow(String nameGroupText) {
        return getRow(nameGroupText).getStatusGroup();

    }

    public void successDelete(String nameGroupText) {
        getRow(nameGroupText).clickDeleteStudent("delete");
        getRow(nameGroupText).waitRestoreStudent("restore_from_trash");
    }

    public void successRestore(String nameGroupText) {
        getRow(nameGroupText).clickDeleteStudent("restore_from_trash");
        getRow(nameGroupText).waitRestoreStudent("delete");
    }

    public void addingNumbersStudent(String count) {
        wait.until(ExpectedConditions.visibilityOf(buttonAddNumbers));
        buttonAddNumbers.click();

        wait.until(ExpectedConditions.visibilityOf(numbersStudentField));
        numbersStudentField.sendKeys(count);

        wait.until(ExpectedConditions.visibilityOf(buttonSaveNumbers));
        buttonSaveNumbers.click();

        wait.until(ExpectedConditions.visibilityOf(buttonCloseModalAddStudent));
        buttonCloseModalAddStudent.click();


    }

    public Boolean checkIconCount(String count)  {
        return wait.until(ExpectedConditions.textToBePresentInElement(iconStudentCount, count));

    }

    public void clickIconMagnifier() {
        wait.until(ExpectedConditions.visibilityOf(iconStudent)).click();
    }

    public String nameStudent() {
        return wait.until(ExpectedConditions.visibilityOf(nameStudentFirst)).getText();
    }

    private TableRow getRowStudent(String name) {
        return rowsInStudentTable.stream()
                .map(TableRow::new)
                .filter(x -> x.getName().equals(name))
                .findFirst().orElseThrow();
    }

    public String getStatusStudent(String name) {
        return getRowStudent(name).getStatus();

    }

    public void successDeleteStudent(String name) {
        getRowStudent(name).clickDelete("delete");
        getRowStudent(name).waitRestore("restore_from_trash");
    }

    public void successRestoreStudent(String nameGroupText) {
        getRowStudent(nameGroupText).clickDelete("restore_from_trash");
        getRowStudent(nameGroupText).waitRestore("delete");
    }


}