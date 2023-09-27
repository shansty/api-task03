package by.itechartgroup.anastasiya.shirochina.pages;

import by.itechartgroup.anastasiya.shirochina.utils.Randomizer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;
import java.util.List;

public class Book {
    private Page page;
    private APIResponse newResponse;
    private  String quantityOfPageApi;
    private Gson newGson;
    private JsonObject json;
    private String bookUrlRegex = "https://demoqa.com/BookStore/v1/Book?ISBN=*";
    private String listOfBooksLocator = "//div[@role='rowgroup' and @class='rt-tr-group']//div[@class='action-buttons']";
    private String pageQuantityLocator = "//div[@id = 'pages-wrapper']//label[@id = 'userName-value']";
    private String keyName = "pages";
    private  List<Locator> listOfBooks;
    private int minPage = 100;
    private int maxPage = 1000;
    public Book(Page page) {
        this.page = page;
    }
    public String getQuantityOfPageApi() {
        return quantityOfPageApi = String.valueOf(Randomizer.randomNumber(minPage,maxPage));
    }
    public Book modifiedResponseWithDifferentBody() {
        page.route(bookUrlRegex, route -> {
            newResponse = route.fetch();
            newGson = new Gson();
            json = newGson.fromJson(newResponse.text(), JsonObject.class);
            json.remove(keyName);
            json.addProperty(keyName, quantityOfPageApi);
            route.fulfill(new Route.FulfillOptions().setBody(json.toString()));
        });
        return this;
    }
    public Book clickRandomBook() {
        listOfBooks =  page.locator(listOfBooksLocator).all();
        listOfBooks.get(Randomizer.randomNumber(0, listOfBooks.size()-1)).click();
        return this;
    }
    public String getQuantityOfPageUi() {
        return page.locator(pageQuantityLocator).textContent();
    }

}
