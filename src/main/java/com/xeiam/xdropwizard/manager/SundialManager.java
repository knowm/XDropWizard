package com.xeiam.xdropwizard.manager;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import com.xeiam.sundial.ee.SundialInitializerListener;
import com.xeiam.xdropwizard.XDropWizardApplicationConfiguration.SundialConfiguration;

/**
 * @author timmolter
 */
public class SundialManager implements Managed {

  private final SundialConfiguration sundialConfiguration;
  private final Environment environment;

  /**
   * Constructor
   *
   * @param sundialConfiguration
   * @param environment
   */
  public SundialManager(SundialConfiguration sundialConfiguration, Environment environment) {

    this.sundialConfiguration = sundialConfiguration;
    this.environment = environment;
  }

  @Override
  public void start() throws Exception {

    // this sets up Sundial with all the default values
    environment.servlets().addServletListeners(new SundialInitializerListener());

    // here we can override the defaults
    if (sundialConfiguration.getThreadPoolSize() != null) {
      environment.servlets().setInitParameter("thread-pool-size", sundialConfiguration.getThreadPoolSize());
    }
    if (sundialConfiguration.getPerformShutdown() != null) {
      environment.servlets().setInitParameter("shutdown-on-unload", sundialConfiguration.getPerformShutdown());
    }
    if (sundialConfiguration.getWaitOnShutdown() != null) {
      environment.servlets().setInitParameter("wait-on-shutdown", sundialConfiguration.getWaitOnShutdown());
    }
    if (sundialConfiguration.getStartDelay() != null) {
      environment.servlets().setInitParameter("start-delay-seconds", sundialConfiguration.getStartDelay());
    }
    if (sundialConfiguration.getStartOnLoad() != null) {
      environment.servlets().setInitParameter("start-scheduler-on-load", sundialConfiguration.getStartOnLoad());
    }
    if (sundialConfiguration.getGlobalLockOnLoad() != null) {
      environment.servlets().setInitParameter("global-lock-on-load", sundialConfiguration.getGlobalLockOnLoad());
    }
    if (sundialConfiguration.getGlobalLockOnLoad() != null) {
      environment.servlets().setInitParameter("global-lock-on-load", sundialConfiguration.getGlobalLockOnLoad());
    }
    if (sundialConfiguration.getAnnotatedJobsPackageName() != null) {
      environment.servlets().setInitParameter("annotated-jobs-package-name", sundialConfiguration.getAnnotatedJobsPackageName());
    }

  }

  @Override
  public void stop() throws Exception {

    // Do nothing. It's taken care of behind the scenes.
  }

}
