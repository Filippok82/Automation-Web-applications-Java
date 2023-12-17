package homework03;


import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import homework03.pages.LoginPage;
import homework03.pages.MainPage;
import homework03.pages.ProfilePage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestStandGB {
    private static final String userName = "Student-5";
    private static final String password = "97d2434151";
    private static final String FULLNAME = "5 Student";




    @BeforeEach
    void open() {

        Selenide.open("https://test-stand.gb.ru/login");

    }

    @Test
    void testAuthorizationWithEmptyFields() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.clickLoginButton();
        Assertions.assertEquals("Invalid credentials.", loginPage.getTextError());
    }

    @Test
    void testValidAuthorisation() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.authorization(userName, password);
        loginPage.checkButtonInvisible();
        MainPage mainPage = Selenide.page(MainPage.class);
        Assertions.assertEquals(("Hello, " + userName), mainPage.getHelloUser());

    }



    @Test
    void testTitleGroup() {
        String nameGroupText = "New name group" + System.currentTimeMillis();
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.authorization(userName, password);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.successCreatingNewGroup(nameGroupText);
        Assertions.assertEquals("active", mainPage.getStatusRow(nameGroupText));
        mainPage.successDeleteGroup(nameGroupText);
        Assertions.assertEquals("inactive", mainPage.getStatusRow(nameGroupText));
        mainPage.successRestoreGroup(nameGroupText);
        Assertions.assertEquals("active", mainPage.getStatusRow(nameGroupText));


        Selenide.screenshot("src/main/resources/screen02.png");

    }


    @Test
    void testCheckNumberStudentsInGroup() {
        int count = 3;
        String nameGroupText = "New name group" + System.currentTimeMillis();
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.authorization(userName, password);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.successCreatingNewGroup(nameGroupText);
        mainPage.addingNumbersStudent(String.valueOf(count));
        mainPage.getCountStudents(nameGroupText,count);
        mainPage.clickIconMagnifier();
        String name = mainPage.getStudentNameByIndex(0);
        assertEquals("active", mainPage.getStatusStudent(name));
        mainPage.successDeleteStudent(name);
        assertEquals("block", mainPage.getStatusStudent(name));
        mainPage.successRestoreStudent(name);
        assertEquals("active", mainPage.getStatusStudent(name));

    }

    @Test
    public void testNameInProfile() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.authorization(userName, password);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        assertEquals(FULLNAME, profilePage.getAdditionalInfoText());
        assertEquals(FULLNAME, profilePage.getAvatarFullName());
    }

    @AfterAll
    static void close() {
        WebDriverRunner.closeWebDriver();

    }
}
