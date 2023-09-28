package by.itechartgroup.anastasiya.shirochina.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BookPage extends BasePage {
    private Locator uiPageNumberLocator;
    public BookPage(Page page) {
        super(page);
        this.uiPageNumberLocator = page.locator("//div[@id = 'pages-wrapper']//label[@id = 'userName-value']");
    }
    public Locator getQuantityOfPageUi() {
        return uiPageNumberLocator;
    }

}
