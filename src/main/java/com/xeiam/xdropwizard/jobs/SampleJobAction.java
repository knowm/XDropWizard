package com.xeiam.xdropwizard.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.sundial.JobAction;

/**
 * A sample Job Action that simply logs a message every time it's called.
 * 
 * @author timmolter
 */
public class SampleJobAction extends JobAction {

  private final Logger logger = LoggerFactory.getLogger(SampleJobAction.class);

  @Override
  public void doRun() {

    Integer myValue = getJobContext().get("MyValue");

    logger.info("SampleJobAction says " + myValue);
  }

}
