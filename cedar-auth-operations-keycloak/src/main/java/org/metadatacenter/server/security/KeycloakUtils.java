package org.metadatacenter.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.common.util.Base64Url;
import org.keycloak.representations.AccessToken;
import org.keycloak.util.JsonSerialization;
import org.metadatacenter.constant.HttpConstants;
import org.metadatacenter.constant.KeycloakConstants;
import org.metadatacenter.server.security.exception.*;
import org.metadatacenter.server.security.model.*;
import org.metadatacenter.server.security.model.auth.AuthorisedUser;
import org.metadatacenter.server.security.model.auth.CedarPermission;
import org.metadatacenter.server.security.model.auth.IAccountInfo;
import org.metadatacenter.server.security.model.auth.SecurityRole;
import org.metadatacenter.server.security.model.user.CedarUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KeycloakUtils {

  private final static String SECRET_KEY = "secret";
  private static final int connectTimeout = 1000;
  private static final int socketTimeout = 10000;
  private static Logger log = LoggerFactory.getLogger(KeycloakUtils.class);

  public static <T> T parseToken(String encoded, Class<T> clazz) throws IOException {
    if (encoded == null) {
      return null;
    }

    String[] parts = encoded.split("\\.");
    if (parts.length < 2 || parts.length > 3) {
      throw new IllegalArgumentException("Parsing error");
    }

    byte[] bytes = Base64Url.decode(parts[1]);
    return JsonSerialization.readValue(bytes, clazz);
  }

  public static KeycloakDeployment buildDeployment() {
    InputStream config = Thread.currentThread().getContextClassLoader().getResourceAsStream(KeycloakConstants.JSON);
    return KeycloakDeploymentBuilder.build(config);
  }

  public static String getRefreshTokenPostData(String keycloakRefreshToken) {
    List<NameValuePair> formparams = new ArrayList<>();
    formparams.add(new BasicNameValuePair("grant_type", "refresh_token"));
    formparams.add(new BasicNameValuePair("refresh_token", keycloakRefreshToken));
    UrlEncodedFormEntity form = null;
    try {
      form = new UrlEncodedFormEntity(formparams, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      log.error("Error while encoding form content", e);
    }

    String content = null;
    try {
      content = IOUtils.toString(form.getContent(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      log.error("Error while converting content", e);
      e.printStackTrace();
    }
    return content;
  }

  public static String getBasicAuthToken(KeycloakDeployment deployment) {
    String resourceId = deployment.getResourceName();
    String clientSecret = (String) (deployment.getResourceCredentials().get(SECRET_KEY));
    StringBuilder sb = new StringBuilder();
    sb.append(resourceId).append(":").append(clientSecret);
    return Base64Url.encode(sb.toString().getBytes());
  }

  public static IAccountInfo getAccountInfoUsingToken(String token) {
    final KeycloakDeployment deployment = KeycloakDeploymentProvider.getInstance().getKeycloakDeployment();

    IAccountInfo accountInfo = null;

    String url = deployment.getRealmInfoUrl() + KeycloakConstants.ACCOUNT_URL_SUFFIX;
    String authString = HttpConstants.HTTP_AUTH_HEADER_BEARER_PREFIX + token;

    try {
      HttpResponse response = Request.Get(url)
          .addHeader(HttpConstants.HTTP_HEADER_CONTENT_TYPE, HttpConstants.CONTENT_TYPE_FORM)
          .addHeader(HttpConstants.HTTP_HEADER_AUTHORIZATION, authString)
          .addHeader(HttpConstants.HTTP_HEADER_ACCEPT, HttpConstants.CONTENT_TYPE_APPLICATION_JSON)
          .connectTimeout(connectTimeout)
          .socketTimeout(socketTimeout)
          .execute()
          .returnResponse();

      int statusCode = response.getStatusLine().getStatusCode();
      //System.out.println("Status code:" + statusCode);
      String responseAsString = EntityUtils.toString(response.getEntity());
      if (statusCode == HttpConstants.OK) {
        ObjectMapper mapper = new ObjectMapper();
        accountInfo = mapper.readValue(responseAsString, KeycloakAccountInfo.class);
      } else {
        //System.out.println("Reponse:" + responseAsString);
      }

    } catch (IOException ex) {
      log.error("Error while reading user details from Keycloak", ex);
      ex.printStackTrace();
    }

    return accountInfo;
  }

  private static IUserInfo getUserInfoUsingToken(String token) {
    final KeycloakDeployment deployment = KeycloakDeploymentProvider.getInstance().getKeycloakDeployment();

    IUserInfo userInfo = null;

    String url = deployment.getRealmInfoUrl() + KeycloakConstants.USERINFO_URL_SUFFIX;
    String authString = HttpConstants.HTTP_AUTH_HEADER_BEARER_PREFIX + token;

    try {
      HttpResponse response = Request.Get(url)
          .addHeader(HttpConstants.HTTP_HEADER_CONTENT_TYPE, HttpConstants.CONTENT_TYPE_FORM)
          .addHeader(HttpConstants.HTTP_HEADER_AUTHORIZATION, authString)
          .addHeader(HttpConstants.HTTP_HEADER_ACCEPT, HttpConstants.CONTENT_TYPE_APPLICATION_JSON)
          .connectTimeout(connectTimeout)
          .socketTimeout(socketTimeout)
          .execute()
          .returnResponse();

      int statusCode = response.getStatusLine().getStatusCode();
      //System.out.println("Status code:" + statusCode);
      String responseAsString = EntityUtils.toString(response.getEntity());
      if (statusCode == HttpConstants.OK) {
        ObjectMapper mapper = new ObjectMapper();
        userInfo = mapper.readValue(responseAsString, KeycloakUserInfo.class);
      } else {
        //System.out.println("Reponse:" + responseAsString);
      }

    } catch (IOException ex) {
      log.error("Error while reading user details from Keycloak", ex);
      ex.printStackTrace();
    }

    return userInfo;
  }

  public static void enforceRealmRoleOnOfflineToken(String token, String permissionName) throws
      CedarAccessException {
    try {
      if (token == null) {
        throw new AccessTokenMissingException();
      }
      AccessToken accessToken = KeycloakUtils.parseToken(token, AccessToken.class);
      if (accessToken == null) {
        throw new InvalidOfflineAccessTokenException();
      } else if (accessToken.isExpired()) {
        throw new AccessTokenExpiredException(accessToken.getExpiration());
      } else {
        if (accessToken.getRealmAccess() == null
            || accessToken.getRealmAccess().getRoles() == null
            || !accessToken.getRealmAccess().getRoles().contains(permissionName)) {
          throw new MissingPermissionException(permissionName);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new InvalidOfflineAccessTokenException();
    }
  }

  public static void checkIfTokenIsStillActiveByUserInfo(String token) throws CedarAccessException {
    AccessToken accessToken = null;
    try {
      accessToken = KeycloakUtils.parseToken(token, AccessToken.class);
    } catch (IOException e) {
      throw new InvalidOfflineAccessTokenException();
    }
    if (accessToken == null) {
      throw new InvalidOfflineAccessTokenException();
    } else if (accessToken.isExpired()) {
      throw new AccessTokenExpiredException(accessToken.getExpiration());
    }

    IUserInfo userInfo = getUserInfoUsingToken(token);
    if (userInfo == null) {
      throw new FailedToLoadUserInfoException();
    }
  }

  public static AuthorisedUser getUserFromToken(AccessToken accessToken) {
    AuthorisedUser au = new AuthorisedUser();
    if (accessToken != null) {
      au.setIdentifier(accessToken.getPreferredUsername());
      List<SecurityRole> roles = new ArrayList<>();
      Set<String> realmRoleNames = accessToken.getRealmAccess().getRoles();
      for (String roleName : realmRoleNames) {
        SecurityRole sr = new SecurityRole();
        sr.setName(roleName);
        roles.add(sr);
      }
      au.setRoles(roles);
    }
    return au;
  }

  public static void enforceCedarUserRole(String token, String permissionName, IUserService userService) throws
      CedarAccessException {
    try {
      if (token == null) {
        throw new AccessTokenMissingException();
      }
      AccessToken accessToken = KeycloakUtils.parseToken(token, AccessToken.class);
      if (accessToken == null) {
        throw new InvalidOfflineAccessTokenException();
      } else if (accessToken.isExpired()) {
        throw new AccessTokenExpiredException(accessToken.getExpiration());
      } else {
        if (!CedarPermission.JUST_AUTHORIZED.getPermissionName().equals(permissionName)) {
          String userId = accessToken.getSubject();
          CedarUser user = null;
          try {
            user = userService.findUser(userId);
          } catch (ProcessingException e) {
            throw new FailedToLoadUserByIdException(e);
          }
          if (user == null) {
            throw new CedarUserNotFoundException(userId);
          }
          CedarUserRolePermissionUtil.expandRolesIntoPermissions(user);
          List<String> permissions = user.getPermissions();
          if (permissions == null || !permissions.contains(permissionName)) {
            throw new MissingPermissionException(permissionName);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new InvalidOfflineAccessTokenException();
    }
  }
}