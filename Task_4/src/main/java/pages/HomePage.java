package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
public class HomePage {
    private final SelenideElement leftSidebarAvatar = $x("//div[@id = 'hook_Block_Avatar']");

    public SelenideElement getLeftSidebarAvatar(){
        return leftSidebarAvatar;
    }

}
