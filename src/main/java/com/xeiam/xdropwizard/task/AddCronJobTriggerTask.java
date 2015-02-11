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
public class AddCronJobTriggerTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(AddCronJobTriggerTask.class);

  /**
   * Constructor
   */
  public AddCronJobTriggerTask() {

    super("addcronjobtrigger");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    String triggerName = (String) parameters.get("TRIGGER_NAME").toArray()[0];
    String jobName = (String) parameters.get("JOB_NAME").toArray()[0];
    String cronExpression = (String) parameters.get("CRON_EXPRESSION").toArray()[0];

    logger.debug(triggerName + " " + jobName + " " + cronExpression);

    SundialJobScheduler.addCronTrigger(triggerName, jobName, cronExpression);

  }
}
