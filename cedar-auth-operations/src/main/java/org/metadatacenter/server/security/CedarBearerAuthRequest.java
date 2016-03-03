package org.metadatacenter.server.security;

import org.metadatacenter.constant.HttpConstants;
import org.metadatacenter.server.security.model.IAuthRequest;
import play.mvc.Http;

public class CedarBearerAuthRequest implements IAuthRequest {

  private String tokenString;

  private CedarBearerAuthRequest() {
  }

  CedarBearerAuthRequest(Http.Request request) {
    if (request != null) {
      String auth = request.getHeader(Http.HeaderNames.AUTHORIZATION);
      if (auth != null) {
        if (auth.startsWith(HttpConstants.HTTP_AUTH_HEADER_BEARER_PREFIX)) {
          tokenString = auth.substring(HttpConstants.HTTP_AUTH_HEADER_BEARER_PREFIX.length());
        }
      }
    }
  }

  @Override
  public String getAuthString() {
    return tokenString;
  }
}
