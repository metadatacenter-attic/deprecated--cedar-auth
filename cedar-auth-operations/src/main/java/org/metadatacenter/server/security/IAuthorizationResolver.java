package org.metadatacenter.server.security;

import org.metadatacenter.server.security.exception.CedarAccessException;
import org.metadatacenter.server.security.model.CedarCapability;
import org.metadatacenter.server.security.model.IAccountInfo;
import org.metadatacenter.server.security.model.IAuthRequest;
import com.fasterxml.jackson.databind.JsonNode;


public interface IAuthorizationResolver {

  void mustHaveCapability(IAuthRequest request, CedarCapability capability, IUserService<String, String, JsonNode>
      userService) throws CedarAccessException;

  IAccountInfo getAccountInfo(IAuthRequest authRequest) throws CedarAccessException;

}