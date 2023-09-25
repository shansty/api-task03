package org.example;

public class Randomizer {
    public static String randomNumber() {
        int minPageCount = 1;
        int maxPageCount = 1000;
        int randomPageCount = (int) (Math.random()*maxPageCount + minPageCount);
        String randomString = String.valueOf(randomPageCount);
        return randomString;
    }
}
