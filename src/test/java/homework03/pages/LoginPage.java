package homework03.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;


public class LoginPage {

    private final SelenideElement userNameField = $("form#login input[type='text']");
    private final SelenideElement passwordField = $("form#login input[type='password']");
    private final SelenideElement loginButton = $("form#login button");
    private final SelenideElement textError = $x("//p[text() = 'Invalid credentials.']");


    public void authorization(String userName, String password) {
        typeUsernameField(userName);
        typePassword(password);
        clickLoginButton();

    }

    public void typeUsernameField(String username) {
        userNameField.should(Condition.visible).setValue(username);
    }

    public void typePassword(String password) {
        passwordField.should(Condition.visible).setValue(password);
    }

    public void clickLoginButton() {
        loginButton.should(Condition.visible).click();
    }

    public void checkButtonInvisible() {
        loginButton.shouldBe(Condition.visible);
    }

    public String getTextError() {
        return textError.should(Condition.visible).getText();
    }


}
