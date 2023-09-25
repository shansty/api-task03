package org.example;

public class Randomizer {
    public static String randomNumberString(int min, int max) {
        int randomPageCount = (int) (Math.random() * (max-min+1) + min);
        String randomString = String.valueOf(randomPageCount);
        return randomString;
    }
    public static int randomNumberInteger(int min, int max) {
        int randomPageCount = (int) (Math.random() * (max-min+1) + min);
        return randomPageCount;
    }
}
