package org.metadatacenter.server.security.model.user;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SortOrder {

  ASC("asc"), DESC("desc");

  private final String value;

  SortOrder(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
