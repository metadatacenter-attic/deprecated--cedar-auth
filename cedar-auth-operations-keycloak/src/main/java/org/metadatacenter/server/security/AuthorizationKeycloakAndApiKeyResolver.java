package org.metadatacenter.server.security;

import org.metadatacenter.server.security.exception.ApiKeyNotFoundException;
import org.metadatacenter.server.security.exception.AuthorizationNotFoundException;
import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.exception.FailedToLoadUserByApiKeyException;
import org.metadatacenter.server.security.model.IAuthRequest;
import org.metadatacenter.server.security.model.auth.CedarPermission;
import org.metadatacenter.server.security.model.auth.IAccountInfo;
import org.metadatacenter.server.security.model.user.CedarUser;

public class AuthorizationKeycloakAndApiKeyResolver implements IAuthorizationResolver {

  @Override
  public void mustHavePermission(IAuthRequest authRequest, CedarPermission permission, IUserService userService) throws
      CedarAccessException {
    if (authRequest instanceof CedarBearerAuthRequest) {
      KeycloakUtils.checkIfTokenIsStillActiveByUserInfo(authRequest.getAuthString());
      KeycloakUtils.enforceCedarUserRole(authRequest.getAuthString(), permission.getPermissionName(), userService);
    } else if (authRequest instanceof CedarApiKeyAuthRequest) {
      String cn = permission.getPermissionName();
      CedarUser user = null;
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
