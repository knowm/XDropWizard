package org.knowm.xdropwizard.api;

/** @author timmolter */
public class Saying {

  private final long id;
  private final String content;

  /**
   * Constructor
   *
   * @param id
   * @param content
   */
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
