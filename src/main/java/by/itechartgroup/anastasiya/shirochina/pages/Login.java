package by.itechartgroup.anastasiya.shirochina.pages;

import by.itechartgroup.anastasiya.shirochina.utils.Reader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class Login extends BasePage{
    private Locator userNameInput;
    private Locator passwordInput;
    private Locator submitButton;

    public Login(Page page) {
        super(page);
        this.userNameInput = page.getByPlaceholder("UserName");
        this.passwordInput = page.getByPlaceholder("Password");
        this.submitButton = page.locator("//button[@id='login']");
    }
    public Login navigate() {
        page.navigate("https://demoqa.com/login");
        return this;
    }
    public Login inputUserName() throws IOException {
        userNameInput.fill(Reader.readPropertyUserName());
        return this;
    }
    public Login inputUserPassword() throws IOException {
        passwordInput.fill(Reader.readPropertyPassword());
        return this;
    }
    public Login submitButton() {
        submitButton.click();
        return this;
    }
}