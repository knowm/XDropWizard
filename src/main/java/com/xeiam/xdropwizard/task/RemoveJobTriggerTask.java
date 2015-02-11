package com.xeiam.xdropwizard.task;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.xeiam.sundial.SundialJobScheduler;

/**
 * @author timmolter
 */
public class RemoveJobTriggerTask extends Task {

  /**
   * Constructor
   */
  public RemoveJobTriggerTask() {

    super("removejobtrigger");
  }

  @Override
  public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {

    ImmutableCollection<String> triggerNames = parameters.get("TRIGGER_NAME");

    for (String triggerName : triggerNames) {

      SundialJobScheduler.removeTrigger(triggerName);
    }

  }
}
