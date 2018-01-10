/**
 * Copyright 2015-2018 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2013-2015 Xeiam LLC (http://xeiam.com) and contributors.
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
package org.knowm.xdropwizard.manager;

import io.dropwizard.lifecycle.Managed;
import org.knowm.xdropwizard.business.Book;
import org.knowm.xdropwizard.business.BooksDAO;
import org.knowm.yank.PropertiesUtils;
import org.knowm.yank.Yank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This is where Yank is bound to the main DropWizard thread.
 * </p>
 *
 * @author timmolter
 */
public class YankManager implements Managed {

  private final Logger logger = LoggerFactory.getLogger(YankManager.class);

  private final List<YankConfiguration> yankConfigurations;

  /**
   * Constructor
   */
  public YankManager(List<YankConfiguration> yankConfigurations) {

    this.yankConfigurations = yankConfigurations;
  }

  @Override public void start() throws Exception {

    logger.info("initializing Yank...");

    for (YankConfiguration yankConfiguration : yankConfigurations) {

      // setup connection pool
      if (yankConfiguration.getDbPropsFileName() == null || yankConfiguration.getSqlPropsFileName().trim().length() == 0) {
        if (yankConfiguration.getPoolName() != null) {
          Yank.setupConnectionPool(yankConfiguration.getPoolName(), PropertiesUtils.getPropertiesFromClasspath("DB.properties"));
        } else {
          Yank.setupDefaultConnectionPool(PropertiesUtils.getPropertiesFromClasspath("DB.properties"));
        }
      } else {
        if (yankConfiguration.getPoolName() != null) {
          Yank.setupConnectionPool(yankConfiguration.getPoolName(),
              PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getDbPropsFileName()));
        } else {
          Yank.setupDefaultConnectionPool(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getDbPropsFileName()));
        }
      }

      // setup sql statements
      if (yankConfiguration.getSqlPropsFileName() != null && yankConfiguration.getSqlPropsFileName().trim().length() > 0) {
        Yank.addSQLStatements(PropertiesUtils.getPropertiesFromClasspath(yankConfiguration.getSqlPropsFileName()));
      }

      logger.info("Yank started successfully.");

    }
  }

  @Override public void stop() throws Exception {

    logger.info("shutting down Yank...");

    Yank.releaseAllConnectionPools();

    logger.info("Yank shutdown successfully.");
  }

}
