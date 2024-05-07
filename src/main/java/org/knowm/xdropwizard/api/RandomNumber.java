package org.knowm.xdropwizard.api;


public class RandomNumber {

    private int number;

    public int getNumber() {

        return (int) (Math.random() * 1000);
    }
}
