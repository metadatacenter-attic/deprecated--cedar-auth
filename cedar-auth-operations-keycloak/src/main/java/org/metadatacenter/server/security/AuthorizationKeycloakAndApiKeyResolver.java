package org.metadatacenter.server.security;

import com.fasterxml.jackson.databind.JsonNode;
import org.metadatacenter.server.security.exception.ApiKeyNotFoundException;
import org.metadatacenter.server.security.exception.AuthorizationNotFoundException;
import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.exception.FailedToLoadUserByApiKeyException;
import org.metadatacenter.server.security.model.CedarCapability;
import org.metadatacenter.server.security.model.IAccountInfo;
import org.metadatacenter.server.security.model.IAuthRequest;

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
    } else {
      throw new AuthorizationNotFoundException();
    }
  }

  public IAccountInfo getAccountInfo(IAuthRequest authRequest) throws CedarAccessException {
    if (authRequest instanceof CedarBearerAuthRequest) {
      return KeycloakUtils.getAccountInfoUsingToken(authRequest.getAuthString());
    } else if (authRequest instanceof CedarApiKeyAuthRequest) {
      return null;
    } else {
      throw new AuthorizationNotFoundException();
    }
  }

}
