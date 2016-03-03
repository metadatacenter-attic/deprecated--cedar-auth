package org.metadatacenter.server.security.exception;

public class AuthorizationNotFoundException extends CedarAccessException {

  public AuthorizationNotFoundException() {
    super("Authorization data not found.", "authorizationNotFound", null);
  }
}
