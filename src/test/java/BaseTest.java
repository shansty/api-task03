import by.itechartgroup.anastasiya.shirochina.api.ApiLogin;
import by.itechartgroup.anastasiya.shirochina.pages.BookPage;
import by.itechartgroup.anastasiya.shirochina.pages.BookStore;
import by.itechartgroup.anastasiya.shirochina.pages.LoginPage;
import by.itechartgroup.anastasiya.shirochina.pages.ProfilePage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {
    static Playwright playwright;
    static Browser browser;
    Page page;
    BrowserContext context;
    LoginPage login;
    ProfilePage profile;
    BookStore bookStore;
    BookPage book;
    ApiLogin apiLogin;

    @BeforeAll
    public static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    public static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }
}
