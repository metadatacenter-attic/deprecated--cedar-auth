package org.metadatacenter.server.security;

import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.model.IAuthRequest;
import org.metadatacenter.server.security.model.auth.CedarPermission;
import org.metadatacenter.server.security.model.user.CedarUser;

public class AuthorizationNoauthResolver implements IAuthorizationResolver {

  @Override
  public void mustHavePermission(IAuthRequest authRequest, CedarPermission permission, IUserService userService) throws
      CedarAccessException {
    // Do nothing. If we do not throw exception, it means the user has the permission
  }

  @Override
  public CedarUser getAccountInfo(IAuthRequest authRequest) throws CedarAccessException {
    return null;
  }

}