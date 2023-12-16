package homework03.tableElements;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

public class GroupTableRow {

    private final SelenideElement root;

    public GroupTableRow(SelenideElement root) {
        this.root = root;
    }

    public String getTitle() {
        return root.$x("./td[2]").shouldBe(visible).getText();
    }

    public String getStatusGroup() {
        return root.$x("./td[3]").shouldBe(visible).getText();
    }

    public void clickDelete(String text) {
        root.$x("./td/button[text()='delete']").shouldBe(visible).click();
        root.$x("./td/button[text()='restore_from_trash']").shouldBe(visible, Duration.ofSeconds(30));
    }

    public void waitRestore(String text) {
        root.$x("./td/button[text()='restore_from_trash']").shouldBe(visible).click();
        root.$x("./td/button[text()='delete']").shouldBe(visible, Duration.ofSeconds(30));
    }

    public void studentsCount(int expectedCount) {
        root.$x("./td[4]//span[text()='%s']".formatted(expectedCount)).shouldBe(visible);

    }


}
