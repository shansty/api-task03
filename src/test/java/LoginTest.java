import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.RequestOptions;
import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginTest {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;
    String url = "https://demoqa.com/login";
    String token;
    String userID;
    String userName;
    String expires;

    @BeforeEach
    public void warmUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(1200000);
        page.navigate(url);
    }

    @AfterEach
    public void tearsDown() {
        playwright.close();
    }

    @Test
    public void loginFormTest() throws IOException, InterruptedException {
        page.getByPlaceholder("UserName").fill(Reader.readPropertyUserName());
        page.getByPlaceholder("Password").fill(Reader.readPropertyPassword());
        page.locator("//button[@id='login']").click();
        Thread.sleep(2000);
        List<Cookie> cookies = context.cookies();
        for (Cookie cookie : cookies) {
            if (cookie.name.equals("userID") && cookie.value != null) {
                userID = cookie.value;
            }
            if (cookie.name.equals("token") && cookie.value != null) {
                token = cookie.value;
                System.out.println(token);
            }
            if (cookie.name.equals("userName") && cookie.value != null) {
                userName = cookie.value;
            }
            if (cookie.name.equals("expires") && cookie.value != null) {
                expires = cookie.value;
            }
        }
        page.route("**/*.{png,jpg,jpeg}", route -> route.abort());
        Response response = page.waitForResponse("https://demoqa.com/BookStore/v1/Books", () -> {
            page.locator("//span[text() ='Book Store']").click();
        });

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/screenshot.png")));
        Assertions.assertEquals(200, response.status());

        String jsonResponse = response.text();
        Gson gson = new Gson();
        Root booksResponse = gson.fromJson(jsonResponse, Root.class);
        List<Book> books = booksResponse.getBooks();
        int quantityOfBooksApi = books.size();
        List<Locator> booksUI = page.locator("//div[@role='rowgroup' and @class='rt-tr-group']//img[contains(@src, '.jpg')]").all();
        int quantityOfBooksUi = booksUI.size();
        Assertions.assertEquals(quantityOfBooksUi, quantityOfBooksApi);

        String quantityOfPageApi = Randomizer.randomNumber();
        page.route("https://demoqa.com/BookStore/v1/Book?ISBN=*", route -> {
            APIResponse newResponse = route.fetch();
            Gson newGson = new Gson();
            JsonObject json = newGson.fromJson(newResponse.text(), JsonObject.class);
            json.remove("pages");
            json.addProperty("pages", quantityOfPageApi);
            route.fulfill(new Route.FulfillOptions().setBody(json.toString()));
        });
        page.locator("//a[text() = 'Git Pocket Guide']").click();
        String quantityOfPageUi = page.locator("//div[@id = 'pages-wrapper']//label[@id = 'userName-value']").textContent();
        Assertions.assertEquals(quantityOfPageApi, quantityOfPageUi);

        APIResponse responseNew = playwright.request().newContext().get("https://demoqa.com/Account/v1/User/" + userID, RequestOptions.create().setHeader("Authorization", "Bearer " + token));
        String responseNewText = responseNew.text();
        Assertions.assertEquals("shansty", gson.fromJson(responseNewText, UserId.class).getUsername());
        Assertions.assertEquals(new ArrayList<>(), gson.fromJson(responseNewText, UserId.class).getBooks());
    }
}
