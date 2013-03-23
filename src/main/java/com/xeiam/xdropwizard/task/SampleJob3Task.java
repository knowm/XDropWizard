/**
 * Copyright (C) 2012 Xeiam  http://xeiam.com
 *
 * ***IMPORTANT*** THIS CODE IS PROPRIETARY!!! 
 */
package com.xeiam.xdropwizard.task;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;
import com.yammer.dropwizard.tasks.Task;

/**
 * @author timmolter
 */
public class SampleJob3Task extends Task {

  /**
   * Constructor
   */
  public SampleJob3Task() {

    super("samplejob3");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> arg0, PrintWriter arg1) throws Exception {

    SundialJobScheduler.startJob("SampleJob3");

  }

}
