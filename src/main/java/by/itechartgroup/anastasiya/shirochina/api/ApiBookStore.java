package by.itechartgroup.anastasiya.shirochina.api;

import by.itechartgroup.anastasiya.shirochina.pojos.Book;
import by.itechartgroup.anastasiya.shirochina.pojos.Root;
import com.google.gson.Gson;
import com.microsoft.playwright.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ApiBookStore {

    public static List<Book> getBooksArray(Response response, String key) {
        String jsonResponse = response.text();
        Gson gson = new Gson();
        Root booksResponse = gson.fromJson(jsonResponse, Root.class);
       return booksResponse.getBooks();
    }
}
