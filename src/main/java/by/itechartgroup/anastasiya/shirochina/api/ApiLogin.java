package by.itechartgroup.anastasiya.shirochina.api;

import by.itechartgroup.anastasiya.shirochina.pages.Profile;
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

    public ApiLogin(Playwright playwright, Profile profile) {
        this.playwright = playwright;
        this.profile = profile;
    }
    public APIResponse sendRequest() {
        responseNew = playwright.request().newContext().get("https://demoqa.com/Account/v1/User/" + profile.getCookieUserID(),
                RequestOptions.create().setHeader("Authorization", "Bearer " + profile.getCookieToken()));
        return responseNew;
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
