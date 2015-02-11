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
import com.xeiam.xdropwizard.resources.YankBookResource;
import com.xeiam.xdropwizard.task.AddCronJobTriggerTask;
import com.xeiam.xdropwizard.task.AddJobTask;
import com.xeiam.xdropwizard.task.LockSundialSchedulerTask;
import com.xeiam.xdropwizard.task.RemoveJobTask;
import com.xeiam.xdropwizard.task.RemoveJobTriggerTask;
import com.xeiam.xdropwizard.task.StartJobTask;
import com.xeiam.xdropwizard.task.StopJobTask;
import com.xeiam.xdropwizard.task.UnlockSundialSchedulerTask;

/**
 * @author timmolter
 */
public class XDropWizardApplication extends Application<XDropWizardApplicationConfiguration> {

  private final Logger logger = LoggerFactory.getLogger(XDropWizardApplication.class);

  public static void main(String[] args) throws Exception {

    new XDropWizardApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<XDropWizardApplicationConfiguration> bootstrap) {

    bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    bootstrap.addBundle(new ViewBundle());
  }

  @Override
  public void run(XDropWizardApplicationConfiguration configuration, Environment environment) throws Exception {

    logger.info("running DropWizard!");

    environment.jersey().setUrlPattern("/service/*");

    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();
    environment.jersey().register(new HelloWorldResource(template, defaultName));
    environment.healthChecks().register("TemplateHealth", new TemplateHealthCheck(template));

    // MANAGERS /////////////////////////

    // Sundial
    SundialManager sm = new SundialManager(configuration.getSundialConfiguration(), environment); // A DropWizard Managed Object
    environment.lifecycle().manage(sm); // Assign the management of the object to the Service

    // Yank
    YankManager ym = new YankManager(configuration.getYankConfiguration()); // A DropWizard Managed Object
    environment.lifecycle().manage(ym); // Assign the management of the object to the Service
    environment.jersey().register(new YankBookResource());

    // TASKS ////////////////////////////

    // tasks are things that should run triggered by a POST, but don't need to respond
    environment.admin().addTask(new LockSundialSchedulerTask());
    environment.admin().addTask(new UnlockSundialSchedulerTask());
    environment.admin().addTask(new RemoveJobTriggerTask());
    environment.admin().addTask(new AddCronJobTriggerTask());
    environment.admin().addTask(new StartJobTask());
    environment.admin().addTask(new StopJobTask());
    environment.admin().addTask(new RemoveJobTask());
    environment.admin().addTask(new AddJobTask());

    // RESOURCES ////////////////////////////

    //    environment.jersey().register(new XChartResource());
    //    environment.jersey().register(new ViewBookResource());
    //    environment.jersey().register(new ViewMarkdownResource());
    //    environment.jersey().register(new RandomNumberResource());

    environment.jersey().packages("com.xeiam.xdropwizard.resources");

  }
}
