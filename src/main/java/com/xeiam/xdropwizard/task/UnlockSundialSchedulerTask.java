/**
 * Copyright (C) 2012 Xeiam  http://xeiam.com
 *
 * ***IMPORTANT*** THIS CODE IS PROPRIETARY!!! 
 */
package com.xeiam.xdropwizard.task;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;
import com.yammer.dropwizard.tasks.Task;

/**
 * @author timmolter
 */
public class UnlockSundialSchedulerTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(UnlockSundialSchedulerTask.class);

  /**
   * Constructor
   */
  public UnlockSundialSchedulerTask() {

    super("unlocksundialscheduler");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> arg0, PrintWriter arg1) throws Exception {

    logger.info("Locking Sundial Scheduler...");

    SundialJobScheduler.unlockScheduler();

  }

}
