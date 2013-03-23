/**
 * Copyright (C) 2012 Xeiam  http://xeiam.com
 *
 * ***IMPORTANT*** THIS CODE IS PROPRIETARY!!! 
 */
package com.xeiam.xdropwizard.api;

/**
 * @author timmolter
 */
public class Saying {

  private final long id;
  private final String content;

  public Saying(long id, String content) {

    this.id = id;
    this.content = content;
  }

  public long getId() {

    return id;
  }

  public String getContent() {

    return content;
  }

}
