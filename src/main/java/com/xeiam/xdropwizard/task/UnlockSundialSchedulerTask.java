package com.xeiam.xdropwizard.task;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;

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
