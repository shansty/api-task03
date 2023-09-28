package by.itechartgroup.anastasiya.shirochina.pages;

import by.itechartgroup.anastasiya.shirochina.utils.Cookies;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Cookie;

import java.util.List;

public class ProfilePage extends BasePage {
    private List<Cookie> cookies;
    private String cookieNameUserId = "userID";
    private String cookieNameToken = "token";
    private String cookieNameUserName = "userName";
    private String cookieNameExpires = "expires";

    public ProfilePage(Page page) {
        super(page);
    }

    public ProfilePage waitForProfileUrlAndCookie() {
        page.waitForURL("**/profile");
        cookies = page.context().cookies();
        return this;
    }

    public String getCookieUserID() {
        return Cookies.getCookieByName(cookieNameUserId, cookies);
    }

    public String getCookieToken() {
        return Cookies.getCookieByName(cookieNameToken, cookies);
    }

    public String getCookieUserName() {
        return Cookies.getCookieByName(cookieNameUserName, cookies);
    }

    public String getCookieExpires() {
        return Cookies.getCookieByName(cookieNameExpires, cookies);
    }

}
