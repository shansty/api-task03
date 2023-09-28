package by.itechartgroup.anastasiya.shirochina.pages;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BookStore extends BasePage {
    private Locator booksArrayLocator;
    private Locator bookStoreButton;
    public String booksUrl = "https://demoqa.com/BookStore/v1/Books";
    public BookStore(Page page) {
        super(page);
        this.bookStoreButton = page.locator("//span[text() ='Book Store']");
        this.booksArrayLocator = page.locator("//div[@role='rowgroup' and @class='rt-tr-group']//div[@class='action-buttons']");
    }

    public BookStore clickBookByNumber(int number) {
        booksArrayLocator.all().get(number).click();
        return this;
    }

    public Locator getBooksArrayLocator() {
        return booksArrayLocator;
    }

    public Locator getBookStoreButton() {
        return bookStoreButton;
    }
}
