package com.xeiam.xdropwizard.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.Job;
import com.xeiam.sundial.exceptions.JobInterruptException;

/**
 * @author timmolter
 */
public class MyJob extends Job {

  private final Logger logger = LoggerFactory.getLogger(MyJob.class);

  @Override
  public void doRun() throws JobInterruptException {

    logger.info("MyJob says hello!");
  }
}
