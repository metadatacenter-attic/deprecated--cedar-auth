package org.metadatacenter.server.security;

import org.metadatacenter.server.security.exception.ApiKeyNotFoundException;
import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.exception.FailedToLoadUserByApiKeyException;
import org.metadatacenter.server.security.model.CedarCapability;
import org.metadatacenter.server.security.model.IAccountInfo;
import org.metadatacenter.server.security.model.IAuthRequest;
import com.fasterxml.jackson.databind.JsonNode;


import java.io.IOException;

public class AuthorizationKeycloakAndApiKeyResolver implements IAuthorizationResolver {

  @Override
  public void mustHaveCapability(IAuthRequest authRequest, CedarCapability capability, IUserService<String, String,
      JsonNode> userService) throws
      CedarAccessException {
    if (authRequest instanceof CedarBearerAuthRequest) {
      String cn = capability.getCapabilityName();
      KeycloakUtils.enforceRealmRoleOnOfflineToken(authRequest.getAuthString(), cn);
      KeycloakUtils.checkIfTokenIsStillActiveByUserInfo(authRequest.getAuthString());
    } else if (authRequest instanceof CedarApiKeyAuthRequest) {
      String cn = capability.getCapabilityName();
      JsonNode user = null;
      try {
        user = userService.findUserByApiKey(authRequest.getAuthString());
      } catch (Exception e) {
        throw new FailedToLoadUserByApiKeyException(e);
      }
      if (user == null) {
        throw new ApiKeyNotFoundException(authRequest.getAuthString());
      }
      //TODO we could read user data, but we do not do that for the moment.
    }
  }

  public IAccountInfo getAccountInfo(IAuthRequest authRequest) throws CedarAccessException {
    if (authRequest instanceof CedarBearerAuthRequest) {
      return KeycloakUtils.getAccountInfoUsingToken(authRequest.getAuthString());
    }
    return null;
  }

}
