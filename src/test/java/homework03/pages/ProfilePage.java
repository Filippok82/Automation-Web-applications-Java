package homework03.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.time.Duration;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage {
    private final SelenideElement additionalInfo = $x("//h3/following-sibling::div//div[contains(text(), 'Full name')]/following-sibling::div");
    private final SelenideElement avatarFullName = $("div.mdc-card h2");
    private final SelenideElement editButton = $("button[title ='More options']");
    private final SelenideElement form = $("form#update-item");
    private final SelenideElement inputAvatar = form.$("input[type='file']");
    private final SelenideElement inputDate = form.$("input[type='date']");
    private final SelenideElement saveProfileButton = form.$("button[type ='submit']");
    private final SelenideElement closeEditingProfileButton = $x("//button[text()='close']");
    private final SelenideElement getDateOfBirthInfo = $x("//div[@class='row svelte-vyyzan' and div[@class='label svelte-vyyzan' " +
            "and text()='Date of birth']]/div[@class='content svelte-vyyzan']");


    public String getAdditionalInfoText(){
        return additionalInfo.should(Condition.visible).getText();
    }

    public String getAvatarFullName(){
        return avatarFullName.should(Condition.visible).getText();
    }

    public void editButtonClick(){
        editButton.shouldBe(Condition.visible).click();
    }


    public void uploadNewAvatar(File file){
        inputAvatar.shouldBe(Condition.visible).uploadFile(file);
    }
    public String getAvatarValue(){
        String inputValue = inputAvatar.shouldBe(Condition.visible).getValue();
        return Objects.requireNonNull(inputValue)
                .substring(inputValue.lastIndexOf("\\") + 1);
    }

    public void inputDateOfBirth(String dateInput){
        inputDate.should(Condition.visible).setValue(dateInput);
        inputDate.should(Condition.visible, Duration.ofSeconds(30));
    }

    public void saveProfileButtonClick(){
        saveProfileButton.shouldBe(Condition.visible, Duration.ofSeconds(30)).click();
    }

    public void closeEditingProfileButtonClick(){
        closeEditingProfileButton.shouldBe(Condition.visible).click();
        closeEditingProfileButton.shouldBe(Condition.disappear, Duration.ofSeconds(30));
    }

    public String checkBirthdayText(){
        return getDateOfBirthInfo.should(Condition.visible).getText();
    }
}
