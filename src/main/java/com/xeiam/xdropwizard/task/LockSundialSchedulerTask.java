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
public class LockSundialSchedulerTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(LockSundialSchedulerTask.class);

  /**
   * Constructor
   */
  public LockSundialSchedulerTask() {

    super("locksundialscheduler");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> arg0, PrintWriter arg1) throws Exception {

    logger.info("Locking Sundial Scheduler...");

    SundialJobScheduler.lockScheduler();

  }

}
