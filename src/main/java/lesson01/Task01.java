package lesson01;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Task01 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\IrinaFil\\IdeaProjects\\Automation-Web-applications-Java\\src\\main\\resources\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        driver.get("http://google.com");
        System.out.println(driver.getTitle());
        driver.quit();



//        System.setProperty("webdriver.firefox.driver","C:\\Users\\IrinaFil\\IdeaProjects\\Automation-Web-applications-Java\\src\\main\\resources\\geckodriver.exe");
//        WebDriver driver = new FirefoxDriver();
//        driver.manage().window().minimize();
//        driver.get("http://google.com");
//        System.out.println(driver.getTitle());
//        driver.quit();
    }
}
