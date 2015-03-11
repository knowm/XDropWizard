package com.xeiam.xdropwizard;

import io.dropwizard.Configuration;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.xeiam.dropwizard.sundial.SundialConfiguration;

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
  @NotNull
  public SundialConfiguration sundialConfiguration = new SundialConfiguration();

  @JsonProperty("sundial")
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

  @NotNull
  private ImmutableMap<String, ImmutableMap<String, String>> viewRendererConfiguration = ImmutableMap.of();

  @JsonProperty("viewRendererConfiguration")
  public ImmutableMap<String, ImmutableMap<String, String>> getViewRendererConfiguration() {
    return viewRendererConfiguration;
  }

  @JsonProperty("viewRendererConfiguration")
  public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
    ImmutableMap.Builder<String, ImmutableMap<String, String>> builder = ImmutableMap.builder();
    for (Map.Entry<String, Map<String, String>> entry : viewRendererConfiguration.entrySet()) {
      builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
    }
    this.viewRendererConfiguration = builder.build();
  }
}
