package org.metadatacenter.server.security.exception;

public class AuthorizationNotFoundException extends CedarAccessException {

  public AuthorizationNotFoundException() {
    this(null);
  }

  public AuthorizationNotFoundException(Exception e) {
    super("Authorization data not found.", "authorizationNotFound", null, e);
  }
}
