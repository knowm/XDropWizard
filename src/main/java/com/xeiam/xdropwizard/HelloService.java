/**
 * Copyright (C) 2012 Xeiam  http://xeiam.com
 *
 * ***IMPORTANT*** THIS CODE IS PROPRIETARY!!! 
 */
package com.xeiam.xdropwizard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xdropwizard.health.TemplateHealthCheck;
import com.xeiam.xdropwizard.manager.SundialManager;
import com.xeiam.xdropwizard.manager.YankManager;
import com.xeiam.xdropwizard.resources.HelloWorldResource;
import com.xeiam.xdropwizard.task.LockSundialSchedulerTask;
import com.xeiam.xdropwizard.task.MyJobTask;
import com.xeiam.xdropwizard.task.SampleJob3Task;
import com.xeiam.xdropwizard.task.UnlockSundialSchedulerTask;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * @author timmolter
 */
public class HelloService extends Service<HelloServiceConfiguration> {

  private final Logger logger = LoggerFactory.getLogger(HelloService.class);

  public static void main(String[] args) throws Exception {

    new HelloService().run(args);
  }

  @Override
  public void initialize(Bootstrap<HelloServiceConfiguration> bootstrap) {

    bootstrap.setName("xdropwizard-service");
  }

  @Override
  public void run(HelloServiceConfiguration configuration, Environment environment) throws Exception {

    logger.error("booya!");

    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();
    environment.addResource(new HelloWorldResource(template, defaultName));
    environment.addHealthCheck(new TemplateHealthCheck(template));

    // MANAGERS /////////////////////////

    // Sundial
    SundialManager sm = new SundialManager(configuration.getSundialProperties()); // A DropWizard Managed Object
    environment.manage(sm); // Assign the management of the object to the Service

    // Yank
    YankManager ym = new YankManager(configuration.getYankConfiguration()); // A DropWizard Managed Object
    environment.manage(ym); // Assign the management of the object to the Service

    // TASKS ////////////////////////////
    environment.addTask(new MyJobTask());
    environment.addTask(new SampleJob3Task());
    environment.addTask(new LockSundialSchedulerTask());
    environment.addTask(new UnlockSundialSchedulerTask());

  }
}
