package homework03.tableElements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;


import java.time.Duration;

public class GroupTableRow {

    WebElement root;

    public GroupTableRow(WebElement webElement) {
        this.root = webElement;
    }

    public String getTitle() {
        return root.findElement(By.xpath("./td[2]")).getText();
    }

    public String getStatusGroup() {
        return root.findElement(By.xpath("./td[3]")).getText();
    }

    public void clickDelete(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        root.findElement(By.xpath(buttonText)).click();
    }

    public void waitRestore(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        FluentWait<WebElement> fluentWait = new FluentWait<>(root);
        fluentWait
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(x -> x.findElement(By.xpath(buttonText)));

    }

    public void studentsCount(int expectedCount) {
        root.findElement(By.xpath("./td[4]//span[text()='%s']".formatted(expectedCount))).getText();

    }


}
