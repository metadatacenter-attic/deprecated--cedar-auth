package org.metadatacenter.server.security.exception;

public class CedarUserNotFoundException extends CedarAccessException {

  public CedarUserNotFoundException(String id) {
    super("Cedar user not found for id:" + id, "cedarUserNotFound", null);
  }
}
