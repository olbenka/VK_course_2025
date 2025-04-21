package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private final SelenideElement emailField = $x("//*[@id='field_email']");
    private final SelenideElement passwordField = $x("//*[@id='field_password']");
    private final SelenideElement loginButton = $x(".//input[@type='submit']");

    private final SelenideElement loginError = $x("//*[contains(@class, 'login_error')]");

    public LoginPage typeEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    public LoginPage typePassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public void clickLogin() {
        loginButton.shouldBe(visible.because("Кнопка входа не отображается"))
                .scrollTo()
                .click();
    }

    public String getLoginErrorText() {
        loginError.shouldBe(visible.because("Ошибка не отображается"));
        return loginError.getText();
    }

    public boolean isEmailFieldVisible() {
        emailField.shouldBe(visible.because("Поле логина не отображается"));
        return true;
    }

    public boolean isPasswordFieldVisible() {
        passwordField.shouldBe(visible.because("Поле пароля не отображается"));
        return true;
    }

}