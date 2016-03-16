package org.metadatacenter.server.security.model.auth;

import java.util.Date;

public interface IAccountInfo {

  String getId();

  String getUsername();

  boolean isEnabled();

  String getFirstName();

  String getLastName();

  String getEmail();

  Date getCreatedAt();

}
