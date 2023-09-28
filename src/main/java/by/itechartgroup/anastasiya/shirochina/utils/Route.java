package by.itechartgroup.anastasiya.shirochina.utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;

public class Route {
    public static void abortAllPicture(Page page, String regex) {
        page.route(regex, route -> route.abort());
    }
    public static void modifiedResponseWithDifferentBody(Page page, String key, String value, String url) {
        page.route(url, route -> {
            APIResponse response = route.fetch();
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(response.text(), JsonObject.class);
            json.remove(key);
            json.addProperty(key, value);
            route.fulfill(new com.microsoft.playwright.Route.FulfillOptions().setBody(json.toString()));
        });
    }
}
