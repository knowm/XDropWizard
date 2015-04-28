package com.xeiam.xdropwizard.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.Job;
import com.xeiam.sundial.SundialJobScheduler;
import com.xeiam.sundial.annotations.CronTrigger;
import com.xeiam.sundial.exceptions.JobInterruptException;

@CronTrigger(cron = "0/25 * * * * ?")
public class MyJob extends Job {

  private final Logger logger = LoggerFactory.getLogger(MyJob.class);

  @Override
  public void doRun() throws JobInterruptException {

    // pull object from ServletContext, which was added in the pllication's run method
    String myObject = (String) SundialJobScheduler.getServletContext().getAttribute("MyKey");

    logger.info("MyJob says: " + myObject);
  }
}
