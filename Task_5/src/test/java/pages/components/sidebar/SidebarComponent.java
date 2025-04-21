package pages.components.sidebar;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class SidebarComponent {
    private final SelenideElement root = $x("//div[@id='hook_Block_SideNavigation']");

    private final ElementsCollection menuLinks = root.$$x(".//a[contains(@class,'nav-side_i')]");

    public List<SidebarMenuComponent> getAllMenuComponents() {
        return menuLinks.stream()
                .map(SidebarMenuComponent::new)
                .collect(Collectors.toList());
    }

    public SidebarMenuComponent getMenuByText(String text) {
        return getAllMenuComponents()
                .stream()
                .filter(item -> item.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Не найден пункт с текстом: " + text));
    }

    public boolean isVisible() {
        return root.shouldBe(visible.because("Боковое меню не отображается")).isDisplayed();
    }
}
