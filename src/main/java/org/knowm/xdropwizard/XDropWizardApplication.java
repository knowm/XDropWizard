package org.knowm.xdropwizard;

import org.knowm.dropwizard.sundial.SundialBundle;
import org.knowm.dropwizard.sundial.SundialConfiguration;
import org.knowm.dropwizard.sundial.tasks.AddCronJobTriggerTask;
import org.knowm.dropwizard.sundial.tasks.AddJobTask;
import org.knowm.dropwizard.sundial.tasks.LockSundialSchedulerTask;
import org.knowm.dropwizard.sundial.tasks.RemoveJobTask;
import org.knowm.dropwizard.sundial.tasks.RemoveJobTriggerTask;
import org.knowm.dropwizard.sundial.tasks.StartJobTask;
import org.knowm.dropwizard.sundial.tasks.StopJobTask;
import org.knowm.dropwizard.sundial.tasks.UnlockSundialSchedulerTask;
import org.knowm.xdropwizard.health.TemplateHealthCheck;
import org.knowm.xdropwizard.manager.YankManager;
import org.knowm.xdropwizard.resources.HelloWorldResource;
import org.knowm.xdropwizard.resources.YankBookResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

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
    bootstrap.addBundle(new ViewBundle<XDropWizardApplicationConfiguration>());

    bootstrap.addBundle(new SundialBundle<XDropWizardApplicationConfiguration>() {

      @Override
      public SundialConfiguration getSundialConfiguration(XDropWizardApplicationConfiguration configuration) {
        return configuration.getSundialConfiguration();
      }
    });
  }

  @Override
  public void run(XDropWizardApplicationConfiguration configuration, Environment environment) throws Exception {

    logger.info("running DropWizard!");

    // Add object to ServletContext for accessing from Sundial Jobs
    environment.getApplicationContext().setAttribute("MyKey", "MyObject");

    final String template = configuration.getTemplate();
    final String defaultName = configuration.getDefaultName();
    environment.jersey().register(new HelloWorldResource(template, defaultName));
    environment.healthChecks().register("TemplateHealth", new TemplateHealthCheck(template));

    // MANAGERS /////////////////////////

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

    environment.jersey().packages("org.knowm.xdropwizard.resources");

  }
}
