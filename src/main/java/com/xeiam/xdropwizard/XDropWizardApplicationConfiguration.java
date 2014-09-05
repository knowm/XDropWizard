package com.xeiam.xdropwizard;

import io.dropwizard.Configuration;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
public class XDropWizardApplicationConfiguration extends Configuration {

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
  @JsonProperty("sundial")
  public SundialConfiguration sundialConfiguration = new SundialConfiguration();

  public static class SundialConfiguration {

    @JsonProperty("thread-pool-size")
    private String threadPoolSize;

    @JsonProperty("shutdown-on-unload")
    private String performShutdown;

    @JsonProperty("wait-on-shutdown")
    private String waitOnShutdown;

    @JsonProperty("start-delay-seconds")
    private String startDelay;

    @JsonProperty("start-scheduler-on-load")
    private String startOnLoad;

    @JsonProperty("global-lock-on-load")
    private String globalLockOnLoad;

    public String getThreadPoolSize() {

      return threadPoolSize;
    }

    public void setThreadPoolSize(String threadPoolSize) {

      this.threadPoolSize = threadPoolSize;
    }

    public String getPerformShutdown() {

      return performShutdown;
    }

    public void setPerformShutdown(String performShutdown) {

      this.performShutdown = performShutdown;
    }

    public String getWaitOnShutdown() {

      return waitOnShutdown;
    }

    public void setWaitOnShutdown(String waitOnShutdown) {

      this.waitOnShutdown = waitOnShutdown;
    }

    public String getStartDelay() {

      return startDelay;
    }

    public void setStartDelay(String startDelay) {

      this.startDelay = startDelay;
    }

    public String getStartOnLoad() {

      return startOnLoad;
    }

    public void setStartOnLoad(String startOnLoad) {

      this.startOnLoad = startOnLoad;
    }

    public String getGlobalLockOnLoad() {

      return globalLockOnLoad;
    }

    public void setGlobalLockOnLoad(String globalLockOnLoad) {

      this.globalLockOnLoad = globalLockOnLoad;
    }

  }

  public SundialConfiguration getSundialConfiguration() {

    return sundialConfiguration;
  }

  // Yank
  @Valid
  @JsonProperty("yank")
  protected YankConfiguration yankConfiguration = new YankConfiguration();

  public static class YankConfiguration {

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

    return yankConfiguration;
  }
}
