import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import org.example.Book;
import org.example.Randomizer;
import org.example.Reader;
import org.example.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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

        String modifiedPage = Randomizer.randomNumber();
        books.get(0).setPages(modifiedPage);


        page.route("https://demoqa.com/BookStore/v1/Book?ISBN=*", route -> {
            System.out.println("log");
          //  APIResponse newResponse = route.fetch();
          //  System.out.println(newResponse.url());
//                    System.out.println(newResponse.text());
//                    System.out.println("log2");
//                    Gson newGson = new Gson();
//                    JsonObject json = newGson.fromJson(newResponse.text(), JsonObject.class).getAsJsonObject();
//                    json.remove("pages");
//                    json.addProperty("pages", Randomizer.randomNumber());
            route.resume();
        });

        Thread.sleep(3000);
        page.locator("//a[text() = 'Git Pocket Guide']").click();
        Thread.sleep(10000);


//        Response newResp = page.waitForResponse("https://demoqa.com/books?book=9781449325862", () -> {
//            page.locator("//a[text() = 'Git Pocket Guide']").click();
//        });
//        Thread.sleep(20000);
//        System.out.println(newResp.text());


        //        Response responsenaa = page.waitForResponse("https://demoqa.com/books?book=9781449325862", () -> {
//            page.locator("//a[text() = 'Git Pocket Guide']").click();
//        page.route("**/BookStore/v1/Book?ISBN=9781449325862", route -> {
//           route.fulfill(new Route.FulfillOptions().setBody(modifiedBook));
//        });
//        });
//        System.out.println(responsenaa.text());
//        Thread.sleep(1000000);


//        page.locator("//a[text() = 'Git Pocket Guide']").click();
//        String quantityOfPageUi = page.locator("//label[@id='userName-value' and text()='234']").textContent();
//        String quantityOfPageApi = books.get(0).getPages();
//        Assertions.assertEquals(quantityOfPageApi, quantityOfPageUi);


//        APIRequest request = playwright.request();
//        APIRequestContext requestContext = request.newContext();
//        APIResponse responseNew = requestContext.get("")

    }
}
