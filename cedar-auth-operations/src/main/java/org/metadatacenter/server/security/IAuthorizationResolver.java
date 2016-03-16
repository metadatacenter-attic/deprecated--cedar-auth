package org.metadatacenter.server.security;

import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.model.auth.CedarPermission;
import org.metadatacenter.server.security.model.auth.IAccountInfo;
import org.metadatacenter.server.security.model.IAuthRequest;


public interface IAuthorizationResolver {

  void mustHavePermission(IAuthRequest request, CedarPermission permission, IUserService userService) throws
      CedarAccessException;

  IAccountInfo getAccountInfo(IAuthRequest authRequest) throws CedarAccessException;

}