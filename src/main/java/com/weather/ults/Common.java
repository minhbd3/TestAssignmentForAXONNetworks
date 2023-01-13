package com.weather.ults;

public class Common {
    public void sleepInSeconds(int timeout) {
        try {
            Thread.sleep(1000 * timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
