package by.itechartgroup.anastasiya.shirochina.pages;

import by.itechartgroup.anastasiya.shirochina.pojos.Book;
import by.itechartgroup.anastasiya.shirochina.pojos.Root;
import by.itechartgroup.anastasiya.shirochina.utils.Screenshot;
import com.google.gson.Gson;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

import java.util.List;

public class BookStore {
    private Page page;
    private String jsonResponse;
    private Response response;
    private Gson gson;
    private Root booksResponse;
    private List<Book> books;
    private String listOfBooksLocator = "//div[@role='rowgroup' and @class='rt-tr-group']//img[contains(@src, '.jpg')]";
    private String buttonBookStoreLocator = "//span[text() ='Book Store']";
    private String pictureRegex = "**/*.{png,jpg,jpeg}";
    private String screenshotPath = "screenshots/screenshot.png";
    private String url = "https://demoqa.com/BookStore/v1/Books";
    public BookStore(Page page) {
        this.page = page;
    }

    public BookStore abortAllPicture() {
        page.route(pictureRegex, route -> route.abort());
        response = page.waitForResponse(url, () -> {
            page.locator(buttonBookStoreLocator).click();
        });
        return this;
    }
    public int getResponseStatusCode() {
        return response.status();
    }
    public BookStore takeScreenshot() {
        Screenshot.getScreenshot(page,screenshotPath);
        return this;
    }
    public BookStore serializationJsonToRoot() {
        jsonResponse = response.text();
        gson = new Gson();
        booksResponse = gson.fromJson(jsonResponse, Root.class);
        return this;
    }
    public int getQuantityOfBooksApi() {
        books = booksResponse.getBooks();
        return books.size();
    }
    public int getQuantityOfBooksUi() {
        return page.locator(listOfBooksLocator).all().size();
    }
}
