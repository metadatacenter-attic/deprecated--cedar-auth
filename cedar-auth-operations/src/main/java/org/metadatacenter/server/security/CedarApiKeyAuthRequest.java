package org.metadatacenter.server.security;

import org.metadatacenter.constant.HttpConstants;
import org.metadatacenter.server.security.model.IAuthRequest;
import play.mvc.Http;

public class CedarApiKeyAuthRequest implements IAuthRequest {

  private String apiKey;

  private CedarApiKeyAuthRequest() {
  }

  CedarApiKeyAuthRequest(Http.Request request) {
    if (request != null) {
      String auth = request.getHeader(Http.HeaderNames.AUTHORIZATION);
      if (auth != null) {
        if (auth.startsWith(HttpConstants.HTTP_AUTH_HEADER_APIKEY_PREFIX)) {
          apiKey = auth.substring(HttpConstants.HTTP_AUTH_HEADER_APIKEY_PREFIX.length());
        }
      }
    }
  }

  @Override
  public String getAuthString() {
    return apiKey;
  }
}
