package base;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.TimeoutException;

import static com.codeborne.selenide.Condition.visible;

public abstract class LoadableComponent {
    protected abstract SelenideElement getLoadedElement();
    protected abstract String getComponentName();

    public void assertIsLoaded() {
        try {
            getLoadedElement().shouldBe(visible.because(getComponentName() + " должен быть загружен"));
        } catch (TimeoutException e) {
            throw new AssertionError("Компонент не загрузился: " + getComponentName(), e);
        }
    }
}
