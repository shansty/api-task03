package org.example;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Cookie;

import java.nio.file.Paths;
import java.util.List;

public class Screenshot {
    public static void getScreenshot(Page page, String path) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)));
    }
}
