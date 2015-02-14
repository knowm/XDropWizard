package com.xeiam.xdropwizard.task;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;

/**
 * @author timmolter
 */
public class StartJobTask extends Task {

  private final Logger logger = LoggerFactory.getLogger(StartJobTask.class);

  /**
   * Constructor
   */
  public StartJobTask() {

    super("startjob");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    logger.info(parameters.toString());

    Map<String, Object> params = new HashMap<String, Object>();

    for (Entry<String, String> entry : parameters.entries()) {
      params.put(entry.getKey(), entry.getValue());
    }

    String jobName = (String) parameters.get("JOB_NAME").toArray()[0];

    SundialJobScheduler.startJob(jobName, params);
  }
}
