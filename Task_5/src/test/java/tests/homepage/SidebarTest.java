package tests.homepage;

import com.codeborne.selenide.Configuration;
import data.objects.User;
import data.objects.UserData;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;
import pages.components.sidebar.SidebarMenuComponent;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static data.SetUpData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SidebarTest {
    private HomePage homePage;
    private final User user = UserData.VALID_USER;

    @BeforeAll
    static void setUp() {
        Configuration.browser = BROWSER;
    }

    @BeforeEach
    void loginAndOpenHomePage() {
        open(LOGIN_URL);
        new LoginPage()
                .typeEmail(user.email())
                .typePassword(user.password())
                .clickLogin();
        homePage = new HomePage();
        homePage.assertIsLoaded();
    }

    @Test
    @Tag("navigation")
    @DisplayName("Пункты боковой панели отображаются")
    @Timeout(10)
    void allSidebarItemsAreVisibleTest() {
        List<SidebarMenuComponent> items = homePage.getSidebar().getAllMenuComponents();
        for (SidebarMenuComponent item : items) {
            assertTrue(item.isVisible(), "Не отображается пункт: " + item.getText());
        }
    }

    @Test
    @Disabled("Отключен из-за неработающего клика")
    @Tag("navigation")
    @DisplayName("Переход на страницу 'Друзья'")
    void canClickFriendsMenuTest() {
        SidebarMenuComponent friends = homePage.getSidebar().getMenuByText("Друзья");
        friends.clickMenuComponent();

        // Пока что: Простейшая проверка — по URL
        assertTrue(
                webdriver().driver().url().contains("/friends"),
                "После клика на 'Друзья' не произошёл переход"
        );
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}
