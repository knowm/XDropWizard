/**
 * Copyright (C) 2012 Xeiam  http://xeiam.com
 *
 * ***IMPORTANT*** THIS CODE IS PROPRIETARY!!! 
 */
package com.xeiam.xdropwizard;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

/**
 * @author timmolter
 */
public class HelloServiceConfiguration extends Configuration {

  // Hello world
  @NotEmpty
  @JsonProperty
  private String template;

  @NotEmpty
  @JsonProperty
  private String defaultName = "Stranger";

  public String getTemplate() {

    return template;
  }

  public String getDefaultName() {

    return defaultName;
  }

  // Sundial
  @Valid
  @JsonProperty
  private Map<String, String> sundial = new HashMap<String, String>();

  public Properties getSundialProperties() {

    Properties props = new Properties();

    // Sundial settings configured in YML file
    props.setProperty("threadPoolSize", sundial.get("thread-pool-size"));
    props.setProperty("performShutdown", sundial.get("shutdown-on-unload"));
    props.setProperty("waitOnShutdown", sundial.get("wait-on-shutdown"));
    props.setProperty("startDelay", sundial.get("start-delay-seconds"));
    props.setProperty("startOnLoad", sundial.get("start-scheduler-on-load"));
    props.setProperty("globalLockOnLoad", sundial.get("global-lock-on-load"));

    return props;
  }

  // Yank
  @Valid
  @NotNull
  @JsonProperty
  protected YankConfiguration yank = new YankConfiguration();

  public static class YankConfiguration {

    @NotEmpty
    @JsonProperty
    private String dbPropsFileName;

    @JsonProperty
    private String sqlPropsFileName;

    public String getDbPropsFileName() {

      return dbPropsFileName;
    }

    public String getSqlPropsFileName() {

      return sqlPropsFileName;
    }
  }

  public YankConfiguration getYankConfiguration() {

    return yank;
  }
}
