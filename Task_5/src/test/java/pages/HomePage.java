package pages;

import base.LoadableComponent;
import com.codeborne.selenide.SelenideElement;
import pages.components.sidebar.SidebarComponent;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends LoadableComponent {
    private final SelenideElement sidebarAvatar = $x("//div[@id='hook_Block_Avatar']");
    private final SidebarComponent sidebar = new SidebarComponent();

    public SidebarComponent getSidebar() {
        return sidebar;
    }

    public void shouldSidebarAvatarBeVisible() {
        sidebarAvatar.shouldBe(visible.because("Не появился аватар на левой панели после авторизации"));
    }

    @Override
    protected SelenideElement getLoadedElement() {
        return sidebarAvatar;
    }

    @Override
    protected String getComponentName() {
        return "Домашняя страница (по аватару)";
    }
}
