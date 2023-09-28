import by.itechartgroup.anastasiya.shirochina.api.ApiBookStore;
import by.itechartgroup.anastasiya.shirochina.pages.*;
import by.itechartgroup.anastasiya.shirochina.api.ApiLogin;
import by.itechartgroup.anastasiya.shirochina.utils.Randomizer;
import by.itechartgroup.anastasiya.shirochina.utils.Reader;
import by.itechartgroup.anastasiya.shirochina.utils.Route;
import by.itechartgroup.anastasiya.shirochina.utils.Screenshot;
import com.microsoft.playwright.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import by.itechartgroup.anastasiya.shirochina.pojos.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest{
    Login login;
    Profile profile;
    BookStore bookStore;
    BookPage book;
    ApiLogin apiLogin;

    @Test
    public void loginFormTest() throws IOException {
        login = new Login(page);
        login.navigate().inputUserName().inputUserPassword().submitButton();

        profile = new Profile(page);
        profile.waitForProfileUrlAndCookie();
        Assertions.assertNotNull(profile.getCookieUserID());
        Assertions.assertNotNull(profile.getCookieToken());
        Assertions.assertNotNull(profile.getCookieUserName());
        Assertions.assertNotNull(profile.getCookieExpires());
        Route.abortAllPicture(page,  "**/*.{png,jpg,jpeg}");

        bookStore = new BookStore(page);
        Response getBooksResponse = page.waitForResponse(bookStore.booksUrl, () -> bookStore.getBookStoreButton().click());
        List<Book> books = ApiBookStore.getBooksArray(getBooksResponse, "books");
        Screenshot.getScreenshot(page, "screenshots/screenshot.png");
        Assertions.assertEquals(200, getBooksResponse.status());
        //не PW, так как используется метод page.waitForResponse(), возвращающий Response, а не APIResponse
        assertThat(bookStore.getBooksArrayLocator()).hasCount(books.size());
        book = new BookPage(page);
        String quantityOfPageApi = String.valueOf(Randomizer.randomNumber(100, 1000));
        Route.modifiedResponseWithDifferentBody(page, "pages", quantityOfPageApi, "https://demoqa.com/BookStore/v1/Book?ISBN=*");
        bookStore.clickBookByNumber(Randomizer.randomNumber(0, bookStore.getBooksArrayLocator().all().size()-1));
        assertThat(book.getQuantityOfPageUi()).containsText((quantityOfPageApi));

        apiLogin = new ApiLogin(playwright, profile);
        assertThat(apiLogin.sendRequest()).isOK();
        Assertions.assertEquals(new ArrayList<>(), apiLogin.getTextFromResponse().getBooksFromResponse());
        Assertions.assertEquals(Reader.readPropertyUserName(), apiLogin.getTextFromResponse().getUserNameFromResponse());
        //тут у нас в классе не используются locator и page, а проверки для APIResponse недостаточно для сравнения
    }
}
