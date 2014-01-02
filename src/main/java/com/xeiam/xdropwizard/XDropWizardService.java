/**
 * Copyright 2012 - 2013 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xdropwizard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xdropwizard.health.TemplateHealthCheck;
import com.xeiam.xdropwizard.manager.SundialManager;
import com.xeiam.xdropwizard.manager.YankManager;
import com.xeiam.xdropwizard.resources.HelloWorldResource;
import com.xeiam.xdropwizard.resources.XChartResource;
import com.xeiam.xdropwizard.resources.YankBookResource;
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
public class XDropWizardService extends Service<XDropWizardServiceConfiguration> {

  private final Logger logger = LoggerFactory.getLogger(XDropWizardService.class);

  public static void main(String[] args) throws Exception {

    new XDropWizardService().run(args);
  }

  @Override
  public void initialize(Bootstrap<XDropWizardServiceConfiguration> bootstrap) {

    bootstrap.setName("xdropwizard-service");
  }

  @Override
  public void run(XDropWizardServiceConfiguration configuration, Environment environment) throws Exception {

    logger.info("running DropWizard!");

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
    environment.addResource(new YankBookResource());

    // TASKS ////////////////////////////

    // task are things that should run triggered by a POST, but don't need to respond
    environment.addTask(new MyJobTask());
    environment.addTask(new SampleJob3Task());
    environment.addTask(new LockSundialSchedulerTask());
    environment.addTask(new UnlockSundialSchedulerTask());

    // RESOURCES ////////////////////////////

    environment.addResource(new XChartResource());

  }
}
