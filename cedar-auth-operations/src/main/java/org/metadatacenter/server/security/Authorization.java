package org.metadatacenter.server.security;

import com.fasterxml.jackson.databind.JsonNode;

import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.model.CedarCapability;
import org.metadatacenter.server.security.model.IAccountInfo;
import org.metadatacenter.server.security.model.IAuthRequest;

public final class Authorization {

  private static IAuthorizationResolver resolver;
  private static IUserService<String, String, JsonNode> userService;

  private Authorization() {
  }

  public static void setAuthorizationResolver(IAuthorizationResolver r) {
    resolver = r;
  }

  public static void setUserService(IUserService<String, String, JsonNode> us) {
    userService = us;
  }

  public static void mustHaveCapability(IAuthRequest authRequest, CedarCapability capability) throws
      CedarAccessException {
    resolver.mustHaveCapability(authRequest, capability, userService);
  }

  public static IAccountInfo getAccountInfo(IAuthRequest authRequest) throws CedarAccessException {
    return resolver.getAccountInfo(authRequest);
  }

}