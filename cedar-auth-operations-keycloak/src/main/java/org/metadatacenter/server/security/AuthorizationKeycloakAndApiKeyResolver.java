package org.metadatacenter.server.security;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.keycloak.representations.AccessToken;
import org.metadatacenter.server.security.exception.*;
import org.metadatacenter.server.security.model.IAuthRequest;
import org.metadatacenter.server.security.model.auth.AuthorisedUser;
import org.metadatacenter.server.security.model.auth.CedarPermission;
import org.metadatacenter.server.security.model.user.CedarUser;

import java.io.IOException;

public class AuthorizationKeycloakAndApiKeyResolver implements IAuthorizationResolver {

  IUserService userService;

  public AuthorizationKeycloakAndApiKeyResolver(IUserService userService) {
    this.userService = userService;
  }

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

  public CedarUser getAccountInfo(IAuthRequest authRequest) throws CedarAccessException {
    if (authRequest instanceof CedarBearerAuthRequest) {
      AccessToken accessToken = null;
      try {
        accessToken = KeycloakUtils.parseToken(authRequest.getAuthString(), AccessToken.class);
      } catch (IOException e) {
        throw new InvalidOfflineAccessTokenException();
      }
      AuthorisedUser userFromToken = KeycloakUtils.getUserFromToken(accessToken);
      try {
        return userService.findUser(userFromToken.getId());
      } catch (IOException | ProcessingException e) {
        throw new AuthorizationNotFoundException(e);
      }
    } else if (authRequest instanceof CedarApiKeyAuthRequest) {
      try {
      return userService.findUserByApiKey(authRequest.getAuthString());
      } catch (IOException | ProcessingException e) {
        throw new AuthorizationNotFoundException(e);
      }
    } else {
      throw new AuthorizationNotFoundException();
    }
  }

}
