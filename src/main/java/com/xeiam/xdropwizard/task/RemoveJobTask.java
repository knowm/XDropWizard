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
public class RemoveJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(RemoveJobTask.class);

  /**
   * Constructor
   */
  public RemoveJobTask() {

    super("removejob");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    String jobName = (String) parameters.get("JOB_NAME").toArray()[0];

    SundialJobScheduler.removeJob(jobName);

  }
}
