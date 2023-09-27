import by.itechartgroup.anastasiya.shirochina.pages.*;
import by.itechartgroup.anastasiya.shirochina.utils.Reader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoginTest extends BaseTest{
    Login login;
    Profile profile;
    BookStore bookStore;
    Book book;
    ApiLogin apiLogin;

    @Test
    public void loginFormTest() throws IOException {
        login = new Login(page);
        login.navigate().inputUserName().inputUserPassword().submitButton();
        profile = new Profile(page, context);
        profile.waitForProfileUrlAndCookie();
        Assertions.assertNotNull(profile.getCookieUserID());
        Assertions.assertNotNull(profile.getCookieToken());
        Assertions.assertNotNull(profile.getCookieUserName());
        Assertions.assertNotNull(profile.getCookieExpires());
        bookStore = new BookStore(page);
        bookStore.abortAllPicture().takeScreenshot();
        Assertions.assertEquals(200, bookStore.getResponseStatusCode());
        bookStore.serializationJsonToRoot();
        Assertions.assertEquals(bookStore.getQuantityOfBooksApi(), bookStore.getQuantityOfBooksUi());
        book = new Book(page);
        Assertions.assertEquals(book.getQuantityOfPageApi(), book.modifiedResponseWithDifferentBody().clickRandomBook().getQuantityOfPageUi());
        apiLogin = new ApiLogin(playwright, profile);
        apiLogin.sendRequest();
        Assertions.assertEquals(apiLogin.getTextFromResponse().getBooksFromResponse(), apiLogin.arrayList);
        Assertions.assertEquals(apiLogin.getTextFromResponse().getUserNameFromResponse(), Reader.readPropertyUserName());
    }
}
