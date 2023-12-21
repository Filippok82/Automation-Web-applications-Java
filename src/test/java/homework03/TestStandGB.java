package homework03;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import homework03.pages.LoginPage;
import homework03.pages.MainPage;
import homework03.pages.ProfilePage;
import org.junit.jupiter.api.*;


import java.io.File;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestStandGB {
    private static final String userName = "Student-5";
    private static final String password = "97d2434151";
    private static final String FULLNAME = "5 Student";
    private static final String DATE = "01.01.1980";


    @BeforeAll
    public static void selenoid() {
        Configuration.browser = "chrome";
        Configuration.remote = "http://localhost:4444/wd/hub";
        Map<String, Object> map = new HashMap<>();
        map.put("enableVNC", true);
        map.put("enableLog", true);
        Configuration.browserCapabilities.setCapability("selenoid:options", map);
    }

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
        mainPage.getCountStudents(nameGroupText, count);
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

    @Test
    public void testAddingAvatar() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.authorization(userName, password);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        profilePage.editButtonClick();
        assertEquals("", profilePage.getAvatarValue());
        profilePage.uploadNewAvatar(new File("src/test/resources/Avatar.PNG"));
        assertEquals("Avatar.PNG", profilePage.getAvatarValue());
    }

    @Test
    public void testDateOfBirthUpdate() {
        LoginPage loginPage = Selenide.page(LoginPage.class);
        loginPage.authorization(userName, password);
        MainPage mainPage = Selenide.page(MainPage.class);
        mainPage.clickProfileButton();
        ProfilePage profilePage = Selenide.page(ProfilePage.class);
        profilePage.editButtonClick();
        profilePage.inputDateOfBirth(DATE);
        profilePage.saveProfileButtonClick();
        profilePage.closeEditingProfileButtonClick();
        assertEquals(DATE,profilePage.checkBirthdayText());

    }

    @AfterAll
    static void close() {
        WebDriverRunner.closeWebDriver();

    }
}
