package com.xeiam.xdropwizard.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.Job;
import com.xeiam.sundial.JobContext;
import com.xeiam.sundial.exceptions.JobInterruptException;

/**
 * @author timmolter
 */
public class SampleJob3 extends Job {

  private final Logger logger = LoggerFactory.getLogger(SampleJob3.class);

  @Override
  public void doRun() throws JobInterruptException {

    JobContext context = getJobContext();

    String valueAsString = context.get("MyParam");
    logger.info("valueAsString = " + valueAsString);

    Integer valueAsInt = Integer.valueOf(valueAsString);
    logger.info("valueAsInt = " + valueAsInt);

    context.put("MyValue", new Integer(123));

    new SampleJobAction().run();

  }
}
