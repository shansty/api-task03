package org.example;

import com.microsoft.playwright.options.Cookie;

import java.util.List;

public class Cookies {
    public String getCookieByName(String cookieName, List<Cookie> list) {
        for (Cookie cookie : list) {
            if (cookie.name.equals(cookieName) && cookie.value != null) {
                cookieName = cookie.value;
            } else if (cookie.value == null){
                return null;
            }
        }
        return cookieName;
    }
}
