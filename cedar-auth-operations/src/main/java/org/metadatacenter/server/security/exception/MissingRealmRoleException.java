package org.metadatacenter.server.security.exception;

public class MissingRealmRoleException extends CedarAccessException {

  private String roleName;

  public MissingRealmRoleException(String roleName) {
    super("Missing role named: " + roleName, "roleMissing", "requestRole");
    this.roleName = roleName;
  }

  public String getRoleName() {
    return roleName;
  }
}
