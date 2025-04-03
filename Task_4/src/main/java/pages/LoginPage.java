package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    private final SelenideElement emailField = $x("//*[@id=\"field_email\"]");
    private final SelenideElement passwordField = $x("//*[@id=\"field_password\"]");
    private final SelenideElement loginButton =  $x("//*[contains(@id, 'tabpanel-login')]/form//input[@type='submit']");

    private final SelenideElement loginError = $x("//div[contains(@class, 'login_error')]");

    public void openPage() {
        open("https://ok.ru/");
    }

    public LoginPage typeEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    public LoginPage typePassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public void clickLogin() {
        loginButton.click();
    }

    public SelenideElement getLoginError() { return loginError;}

    public SelenideElement getEmailField() {
        return emailField;
    }

    public SelenideElement getPasswordField() {
        return passwordField;
    }
}