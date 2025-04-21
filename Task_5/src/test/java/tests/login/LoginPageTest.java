package tests.login;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static data.SetUpData.*;
import static data.UserData.*;
import static data.ErrorMessagesData.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты страницы входа")
public class LoginPageTest {
    private LoginPage loginPage;

    @BeforeAll
    static void setUp() {
        Configuration.browser = BROWSER;
    }

    @BeforeEach
    void openPage() {
        open(LOGIN_URL);
        loginPage = new LoginPage();
    }

    @Test
    @DisplayName("Поля логина и пароля отображаются")
    void fieldsVisibleTest() {
        assertAll("Проверка отображения полей логина и пароля",
                () -> Assertions.assertTrue(loginPage.isEmailFieldVisible(), "Поле логина не отображается"),
                () -> Assertions.assertTrue(loginPage.isPasswordFieldVisible(), "Поле пароля не отображается")
        );
    }

    @Nested
    @Tag("negative")
    @DisplayName("Негативные сценарии авторизации")
    class NegativeLoginTests {

        @Test
        @DisplayName("Неверный логин и пароль")
        void invalidLoginTest() {
            loginPage.typeEmail(INVALID_EMAIL)
                    .typePassword(INVALID_PASSWORD)
                    .clickLogin();

            String actualError = loginPage.getLoginErrorText();
            assertEquals(EXPECTED_ERROR_INVALID_LOGIN, actualError,
                    "Сообщение об ошибке некорректного логина не совпадает с ожидаемым");
        }

        @Test
        @DisplayName("Поля логина и пароля пустые")
        void emptyFieldsTest() {
            loginPage.clickLogin();

            String actualError = loginPage.getLoginErrorText();
            assertEquals(EXPECTED_ERROR_EMPTY_LOGIN, actualError,
                    "Сообщение об ошибке при пустом логине не совпадает с ожидаемым");
        }

        @Test
        @DisplayName("Пустой пароль")
        void emptyFieldPasswordTest() {
            loginPage.typeEmail(INVALID_EMAIL)
                    .clickLogin();

            String actualError = loginPage.getLoginErrorText();
            assertEquals(EXPECTED_ERROR_EMPTY_PASSWORD, actualError,
                    "Сообщение об ошибке при пустом пароле не совпадает с ожидаемым");
        }
    }

    @Nested
    @Tag("positive")
    @DisplayName("Позитивные сценарии авторизации")
    class PositiveLoginTests {
        @Test
        //@Disabled("Отключено из-за капчи")
        @DisplayName("Успешная авторизация через кнопку 'Войти в Одноклассники'")
        void successfulLoginWithButtonTest() {
            loginPage.typeEmail(VALID_EMAIL)
                    .typePassword(VALID_PASSWORD)
                    .clickLogin();

            HomePage homePage = new HomePage();
            homePage.assertIsLoaded();
            homePage.shouldSidebarAvatarBeVisible();
        }
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}