package com.xeiam.xdropwizard.manager;

import io.dropwizard.lifecycle.Managed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xdropwizard.XDropWizardApplicationConfiguration.YankConfiguration;
import com.xeiam.yank.PropertiesUtils;
import com.xeiam.yank.Yank;

/**
 * <p>
 * This is where Yank is bound to the main DropWizard thread.
 * </p>
 *
 * @author timmolter
 */
public class YankManager implements Managed {

  private final Logger logger = LoggerFactory.getLogger(YankManager.class);

  private final YankConfiguration yankConfiguration;

  /**
   * Constructor
   */
  public YankManager(YankConfiguration yankConfiguration) {

    this.yankConfiguration = yankConfiguration;
  }

  @Override
  public void start() throws Exception {

    logger.info("initializing Yank...");

    // setup connection pool
    if (yankConfiguration.getDbPropsFileName() == null) {
      Yank.addConnectionPool("myconnectionpoolname", PropertiesUtils.getPropertiesFromClasspath("DB.properties"));
    } else {
      Yank.addConnectionPool("myconnectionpoolname", PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getDbPropsFileName()));
    }

    // setup sql statements
    if (yankConfiguration.getSqlPropsFileName() != null) {
      Yank.addSQLStatements(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getSqlPropsFileName()));
    }

    logger.info("Yank started successfully.");

  }

  @Override
  public void stop() throws Exception {

    logger.info("shutting down Yank...");

    Yank.release();

    logger.info("Yank shutdown successfully.");
  }

}
