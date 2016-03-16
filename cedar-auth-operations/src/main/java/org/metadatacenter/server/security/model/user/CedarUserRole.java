package org.metadatacenter.server.security.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

import java.lang.String;

public enum CedarUserRole {
  TEMPLATE_CREATOR("templateCreator"),
  TEMPLATE_INSTANTIATOR("templateInstantiator");

  private final String value;

  CedarUserRole(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}
