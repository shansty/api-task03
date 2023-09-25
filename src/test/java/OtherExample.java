import com.microsoft.playwright.*;

public class OtherExample {
    static Playwright playwright;
    static Browser browser;
    static Page page;
    static BrowserContext context;

    public static void main(String[] args) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        page.setDefaultTimeout(1200000);
        page.route("**/v1.0/state*", route -> {
            System.out.println("log");
            APIResponse response = route.fetch();
            System.out.println(response.url());
            route.resume();
        });
        page.navigate("https://www.theverge.com/");


    }
}
