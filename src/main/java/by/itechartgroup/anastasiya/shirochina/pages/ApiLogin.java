package by.itechartgroup.anastasiya.shirochina.pages;

import by.itechartgroup.anastasiya.shirochina.pojos.UserId;
import com.google.gson.Gson;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import java.util.ArrayList;

public class ApiLogin {
    private APIResponse responseNew;
    private String responseNewText;
    private Playwright playwright;
    private Profile profile;
    private String url = "https://demoqa.com/Account/v1/User/";
    private String headerKey = "Authorization";
    private String headerValue = "Bearer ";
    public ArrayList arrayList = new ArrayList<>();

    public ApiLogin(Playwright playwright, Profile profile) {
        this.playwright = playwright;
        this.profile = profile;
    }

    public ApiLogin sendRequest() {
        responseNew = playwright.request().newContext().get(url + profile.getCookieUserID(),
                RequestOptions.create().setHeader(headerKey, headerValue + profile.getCookieToken()));
        return this;
    }
    public ApiLogin getTextFromResponse(){
        responseNewText = responseNew.text();
        return this;
    }
    public String getUserNameFromResponse() {
        return new Gson().fromJson(responseNewText, UserId.class).getUsername();
    }
    public ArrayList<Object> getBooksFromResponse() {
        return new Gson().fromJson(responseNewText, UserId.class).getBooks();
    }
}
