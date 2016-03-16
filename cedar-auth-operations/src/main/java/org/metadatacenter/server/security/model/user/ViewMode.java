package org.metadatacenter.server.security.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ViewMode {

  GRID("grid"), LIST("list");

  private final String value;

  ViewMode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
