package org.metadatacenter.server.security.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PopulateATemplateStatus {

  MINIMIZED("minimized"), NORMAL("normal");

  private final String value;

  PopulateATemplateStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
