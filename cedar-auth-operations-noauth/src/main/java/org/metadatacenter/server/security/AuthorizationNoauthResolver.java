package org.metadatacenter.server.security;

import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.model.CedarCapability;
import org.metadatacenter.server.security.model.IAccountInfo;
import org.metadatacenter.server.security.model.IAuthRequest;

public class AuthorizationNoauthResolver implements IAuthorizationResolver {

  @Override
  public void mustHaveCapability(IAuthRequest authRequest, CedarCapability capability, IUserService userService) throws
      CedarAccessException {
    // Do nothing. If we do not throw exception, it means the user has the capability
  }

  @Override
  public IAccountInfo getAccountInfo(IAuthRequest authRequest) throws CedarAccessException {
    return null;
  }

}