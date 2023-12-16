package homework03.tableElements;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

public class StudentTableRow {
    private final SelenideElement root;

    public StudentTableRow(SelenideElement root) {
        this.root = root;
    }


    public String getStatus() {
        return root.$x("./td[4]").getText();
    }

    public void clickDeleteStudent(String text) {
        root.$x("./td/button[text()='delete']").click();
        root.$x("./td/button[text()='restore_from_trash']")
                .shouldBe(visible, Duration.ofSeconds(30));
    }

    public void waitRestoreStudent(String text) {
        root.$x("./td/button[text()='restore_from_trash']").click();
        root.$x("./td/button[text()='delete']")
                .shouldBe(visible, Duration.ofSeconds(30));

    }

    public String getName() {
        return root.$x("./td[2]").getText();
    }

}
