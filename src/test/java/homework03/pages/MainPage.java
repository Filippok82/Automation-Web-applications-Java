package homework03.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import homework03.tableElements.GroupTableRow;
import homework03.tableElements.StudentTableRow;


import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement helloUserField = $("li.mdc-menu-surface--anchor");
    private final SelenideElement buttonPlus = $("#create-btn");
    private final SelenideElement nameGroupField = $("form#update-item input[type='text']");
    private final SelenideElement buttonSaveGroup = $("#update-item > div.submit > button");
    private final SelenideElement buttonCloseModalWindow = $(".form-modal-header button");
    private final SelenideElement tableTitleCss = $("div.mdc-data-table");
    private final ElementsCollection listRow = $$x("//table[@aria-label='Tutors list']/tbody/tr");

    private final SelenideElement buttonAddNumbers = $x("//button[@class='mdc-button smui-button--color-secondary mdc-ripple-upgraded']");
    private final SelenideElement numbersStudentField = $("form#generate-logins input[type='number']");
    private final SelenideElement buttonSaveNumbers = $("form#generate-logins button");
    private final SelenideElement buttonCloseModalAddStudent = $x("//h2[@id='generateStudentsForm-title']/../button");
    private final SelenideElement iconOpenTableStudent = $x("//table[@aria-label='Tutors list']/tbody/tr[1]/td[4]/button[3]");
    private final ElementsCollection rowsInStudentTable = $$x("//table[@aria-label='User list']/tbody/tr");

    private final SelenideElement profileButton = $x("//nav//li[contains(@class,'mdc-menu-surface--anchor')]//span[text()='Profile']");


    public String getHelloUser() {
        return helloUserField.should(Condition.visible).getText();
    }

    public void creatingNewGroup(String nameGroupText) {
        buttonPlus.should(Condition.visible).click();
        nameGroupField.should(Condition.visible).setValue(nameGroupText);
        nameGroupField.should(Condition.value(nameGroupText));
        buttonSaveGroup.should(Condition.visible).click();
        buttonCloseModalWindow.should(Condition.visible).click();

    }

    public void successCreatingNewGroup(String nameGroupText) {
        creatingNewGroup(nameGroupText);
        tableTitleCss.should(Condition.visible).should(Condition.text(nameGroupText));

    }

    private GroupTableRow getRow(String nameGroupText) {
        return listRow
                .asDynamicIterable()
                .stream()
                .map(GroupTableRow::new)
                .filter(x -> x.getTitle().equals(nameGroupText))
                .findFirst().orElseThrow();
    }

    public String getStatusRow(String nameGroupText) {
        return getRow(nameGroupText).getStatusGroup();

    }

    public void successDeleteGroup(String nameGroupText) {
        getRow(nameGroupText).clickDelete("delete");
    }

    public void successRestoreGroup(String nameGroupText) {
        getRow(nameGroupText).waitRestore("restore_from_trash");
    }

    public void addingNumbersStudent(String count) {
        buttonAddNumbers.should(Condition.visible).click();
        numbersStudentField.should(Condition.visible).setValue(count);
        buttonSaveNumbers.should(Condition.visible).click();
        buttonCloseModalAddStudent.should(Condition.visible).click();

    }


    public void getCountStudents(String nameGroup, int count) {
        getRow(nameGroup).studentsCount(count);

    }

    public void clickIconMagnifier() {
        iconOpenTableStudent.should(Condition.visible).click();

    }

    private StudentTableRow getRowStudent(String name) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable()
                .stream()
                .map(StudentTableRow::new)
                .filter(x -> x.getName().equals(name))
                .findFirst().orElseThrow();

    }


    public String getStatusStudent(String name) {
        return getRowStudent(name).getStatus();

    }

    public String getStudentNameByIndex(int index) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(StudentTableRow::new)
                .toList().get(index).getName();
    }

    public void successDeleteStudent(String name) {
        getRowStudent(name).clickDeleteStudent("delete");

    }

    public void successRestoreStudent(String name) {
        getRowStudent(name).waitRestoreStudent("restore_from_trash");

    }

    public void clickProfileButton() {
        helloUserField.should(Condition.visible).click();
        profileButton.should(Condition.visible).click();

    }

}