package pages.components.sidebar;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;

public class SidebarMenuComponent {
    private final SelenideElement root;

    public SidebarMenuComponent(SelenideElement root) {
        this.root = root;
    }

    public String getText() {
        return root.shouldBe(visible.because("Пункт меню не отображается")).getText();
    }

    public void clickMenuComponent() {
        root.shouldBe(visible.because("Пункт меню не отображается"))
                .scrollTo()
                .click();
    }

    public boolean isVisible() {
        return root.shouldBe(visible.because("Пункт меню не отображается")).isDisplayed();
    }
}
