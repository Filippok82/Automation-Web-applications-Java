package homework03.tableElements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class StudentTableRow {
    private final WebElement root;

    public StudentTableRow(WebElement root) {
        this.root = root;
    }


    public String getStatus() {
        return root.findElement(By.xpath("./td[4]")).getText();
    }

    public void clickDeleteStudent(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        root.findElement(By.xpath(buttonText)).click();
    }

    public void waitRestoreStudent(String text) {
        String buttonText = String.format("./td/button[text()='%s']", text);
        FluentWait<WebElement> fluentWait = new FluentWait<>(root);
        fluentWait
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class)
                .until(x -> x.findElement(By.xpath(buttonText)));

    }





    public String getName() {
        return root.findElement(By.xpath("./td[2]")).getText();
    }

}
