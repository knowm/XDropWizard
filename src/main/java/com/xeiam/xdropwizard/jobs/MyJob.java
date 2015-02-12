package com.xeiam.xdropwizard.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.Job;
import com.xeiam.sundial.Triggered;
import com.xeiam.sundial.exceptions.JobInterruptException;

@Triggered(cron = "0/25 * * * * ?")
public class MyJob extends Job {

  private final Logger logger = LoggerFactory.getLogger(MyJob.class);

  @Override
  public void doRun() throws JobInterruptException {

    logger.info("MyJob says hello!");
  }
}
