package com.xeiam.xdropwizard.task;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;

/**
 * @author timmolter
 */
public class MyJobTask extends Task {

  /**
   * Constructor
   */
  public MyJobTask() {

    super("myjob");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> arg0, PrintWriter arg1) throws Exception {

    SundialJobScheduler.startJob("MyJob");

  }

}
