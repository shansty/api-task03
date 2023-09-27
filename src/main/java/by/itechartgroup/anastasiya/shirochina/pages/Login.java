package by.itechartgroup.anastasiya.shirochina.pages;

import by.itechartgroup.anastasiya.shirochina.utils.Reader;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class Login {
    private String inputUserNameLocator = "UserName";
    private String inputPasswordLocator = "Password";
    private String submitButtonLocator = "//button[@id='login']";
    private String url = "https://demoqa.com/login";
    private Page page;

    public Login(Page page) {
        this.page = page;
    }
    public Login navigate() {
        page.navigate(url);
        return this;
    }
    public Login inputUserName() throws IOException {
        page.getByPlaceholder(inputUserNameLocator).fill(Reader.readPropertyUserName());
        return this;
    }
    public Login inputUserPassword() throws IOException {
        page.getByPlaceholder(inputPasswordLocator).fill(Reader.readPropertyPassword());
        return this;
    }
    public Login submitButton() {
        page.locator(submitButtonLocator).click();
        return this;
    }
}