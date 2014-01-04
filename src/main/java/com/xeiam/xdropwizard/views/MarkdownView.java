package com.xeiam.xdropwizard.views;

import java.io.IOException;
import java.net.URL;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.yammer.dropwizard.views.View;

/**
 * @author timmolter
 */
public class MarkdownView extends View {

  private final String name;
  private final int age;

  public MarkdownView(String name, int age) {

    super("ftl/markdown.ftl");

    this.name = name;
    this.age = age;
  }

  public String getHardcodedhtml() {

    StringBuilder sb = new StringBuilder();

    sb.append("#### This is from hardcoded markdown");
    sb.append("\n");
    sb.append("\n");
    sb.append("The name path parameter in the URL was: " + this.name);
    sb.append("\n");
    sb.append("\n");
    sb.append("The age query parameter in the URL was: " + this.age);

    PegDownProcessor processor = new PegDownProcessor();

    String html = processor.markdownToHtml(sb.toString());

    return html;
  }

  public String getLoadedhtml() throws IOException {

    // String markdown = readFileFromClasspathToString("markdown/markdownsample.md");

    URL url = Resources.getResource("markdown/markdownsample.md");
    String markdown = Resources.toString(url, Charsets.UTF_8);

    // New processor each time due to pegdown not being thread-safe internally
    PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

    // Return the rendered HTML
    return processor.markdownToHtml(markdown);
  }

}
