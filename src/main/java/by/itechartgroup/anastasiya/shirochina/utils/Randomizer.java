package by.itechartgroup.anastasiya.shirochina.utils;

public class Randomizer {
    public static int randomNumber(int min, int max) {
        return (int) (Math.random() * (max-min+1) + min);
    }
}
