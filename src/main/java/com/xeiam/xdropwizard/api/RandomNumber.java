package com.xeiam.xdropwizard.api;

/**
 * @author timmolter
 */
public class RandomNumber {

  private int number;

  public int getNumber() {

    return (int) (Math.random() * 1000);
  }

}
