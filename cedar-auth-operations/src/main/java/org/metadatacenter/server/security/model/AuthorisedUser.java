package org.metadatacenter.server.security.model;


import java.util.List;

public class AuthorisedUser {

  private List<? extends SecurityRole> roles;
  private List<? extends UserPermission> permissions;
  private String identifier;

  public void setRoles(List<? extends SecurityRole> roles) {
    this.roles = roles;
  }

  public void setPermissions(List<? extends UserPermission> permissions) {
    this.permissions = permissions;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public List<? extends SecurityRole> getRoles() {
    return roles;
  }

  public List<? extends UserPermission> getPermissions() {
    return permissions;
  }

  public String getIdentifier() {
    return identifier;
  }
}
