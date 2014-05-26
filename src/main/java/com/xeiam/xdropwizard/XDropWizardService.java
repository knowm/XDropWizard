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

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xdropwizard.health.TemplateHealthCheck;
import com.xeiam.xdropwizard.manager.SundialManager;
import com.xeiam.xdropwizard.manager.YankManager;
import com.xeiam.xdropwizard.resources.HelloWorldResource;
import com.xeiam.xdropwizard.resources.RandomNumberResource;
import com.xeiam.xdropwizard.resources.ViewBookResource;
import com.xeiam.xdropwizard.resources.ViewMarkdownResource;
import com.xeiam.xdropwizard.resources.XChartResource;
import com.xeiam.xdropwizard.resources.YankBookResource;
import com.xeiam.xdropwizard.task.LockSundialSchedulerTask;
import com.xeiam.xdropwizard.task.MyJobTask;
import com.xeiam.xdropwizard.task.SampleJob3Task;
import com.xeiam.xdropwizard.task.UnlockSundialSchedulerTask;

/**
 * @author timmolter
 */
public class XDropWizardService extends Application<XDropWizardServiceConfiguration> {

  private final Logger logger = LoggerFactory.getLogger(XDropWizardService.class);

  public static void main(String[] args) throws Exception {

    new XDropWizardService().run(args);
  }

  @Override
  public void initialize(Bootstrap<XDropWizardServiceConfiguration> bootstrap) {

    bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    bootstrap.addBundle(new ViewBundle());
  }

  @Override
  public void run(XDropWizardServiceConfiguration configuration, Environment environment) throws Exception {

    logger.info("running DropWizard!");

    environment.jersey().setUrlPattern("/service/*");

    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();
    environment.jersey().register(new HelloWorldResource(template, defaultName));
    environment.healthChecks().register("TemplateHealth", new TemplateHealthCheck(template));

    // MANAGERS /////////////////////////

    // Sundial
    SundialManager sm = new SundialManager(configuration.getSundialProperties()); // A DropWizard Managed Object
    environment.lifecycle().manage(sm); // Assign the management of the object to the Service

    // Yank
    YankManager ym = new YankManager(configuration.getYankConfiguration()); // A DropWizard Managed Object
    environment.lifecycle().manage(ym); // Assign the management of the object to the Service
    environment.jersey().register(new YankBookResource());

    // TASKS ////////////////////////////

    // tasks are things that should run triggered by a POST, but don't need to respond
    environment.admin().addTask(new MyJobTask());
    environment.admin().addTask(new SampleJob3Task());
    environment.admin().addTask(new LockSundialSchedulerTask());
    environment.admin().addTask(new UnlockSundialSchedulerTask());

    // RESOURCES ////////////////////////////

    environment.jersey().register(new XChartResource());
    environment.jersey().register(new ViewBookResource());
    environment.jersey().register(new ViewMarkdownResource());
    environment.jersey().register(new RandomNumberResource());

  }
}
