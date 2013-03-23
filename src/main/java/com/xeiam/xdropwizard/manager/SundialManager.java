/**
 * Copyright (C) 2012 Xeiam  http://xeiam.com
 *
 * ***IMPORTANT*** THIS CODE IS PROPRIETARY!!! 
 */
package com.xeiam.xdropwizard.manager;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.SundialJobScheduler;
import com.yammer.dropwizard.lifecycle.Managed;

/**
 * Adapted from here: http://blog.tech.renttherunway.com/?p=60
 * <p>
 * This is where Sundial is bound to the main DropWizard thread.
 * </p>
 * 
 * @author timmolter
 */
public class SundialManager implements Managed {

  private final Logger logger = LoggerFactory.getLogger(SundialManager.class);

  private Properties quartzProperties;

  private boolean performShutdown;
  private boolean waitOnShutdown;

  /**
   * Constructor
   */
  public SundialManager(Properties quartzProperties) {

    this.quartzProperties = quartzProperties;
  }

  @Override
  public void start() throws Exception {

    logger.info("Sundial Initializer Manager loaded, initializing Scheduler...");

    // PERFORM SHUTDOWN
    performShutdown = Boolean.valueOf(quartzProperties.getProperty("performShutdown", "true")).booleanValue();

    // WAIT ON SHUTDOWN
    waitOnShutdown = Boolean.valueOf(quartzProperties.getProperty("waitOnShutdown", "false")).booleanValue();

    // THREAD POOL SIZE
    int threadPoolSize = 10; // ten is default
    threadPoolSize = Integer.parseInt(quartzProperties.getProperty("threadPoolSize", "10"));

    // CREATE SCHEDULER
    SundialJobScheduler.createScheduler(threadPoolSize);

    // START SCHEDULER
    boolean startOnLoad = Boolean.valueOf(quartzProperties.getProperty("startOnLoad", "true")).booleanValue();
    if (startOnLoad) {
      int startDelay = Integer.parseInt(quartzProperties.getProperty("startDelay", "0"));
      if (startDelay <= 0) {
        // Start now
        SundialJobScheduler.getScheduler().start();
        logger.info("Sundial scheduler has been started.");
      } else {
        // Start delayed
        SundialJobScheduler.getScheduler().startDelayed(startDelay);
        logger.info("Sundial scheduler will start in " + startDelay + " seconds.");
      }
    } else {
      logger.info("Sundial scheduler has not been started. Use scheduler.start()");
    }

    // GLOBAL LOCK
    boolean globalLockOnLoad = Boolean.valueOf(quartzProperties.getProperty("globalLockOnLoad", "false")).booleanValue();
    if (globalLockOnLoad) {
      SundialJobScheduler.lockScheduler();
      logger.info("Sundial scheduler has been locked.");
    }

    logger.info("Scheduler has been started.");
  }

  @Override
  public void stop() throws Exception {

    if (!performShutdown) {
      return;
    }

    try {
      if (SundialJobScheduler.getScheduler() != null) {
        SundialJobScheduler.getScheduler().shutdown(waitOnShutdown);
      }
    } catch (Exception e) {
      logger.error("Sundial Scheduler failed to shutdown cleanly: ", e);
    }

    logger.info("Sundial Scheduler shutdown successfully.");
  }

}
