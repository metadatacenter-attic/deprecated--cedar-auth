package org.metadatacenter.server.security.model.auth;


import org.metadatacenter.server.security.model.CedarObjectConstants;

public enum CedarPermission {

  TEMPLATE_CREATE(CedarObjectConstants.RESOURCE_TEMPLATE, CedarObjectConstants.ACCESS_CREATE),
  TEMPLATE_READ(CedarObjectConstants.RESOURCE_TEMPLATE, CedarObjectConstants.ACCESS_READ),
  TEMPLATE_UPDATE(CedarObjectConstants.RESOURCE_TEMPLATE, CedarObjectConstants.ACCESS_UPDATE),
  TEMPLATE_DELETE(CedarObjectConstants.RESOURCE_TEMPLATE, CedarObjectConstants.ACCESS_DELETE),

  TEMPLATE_ELEMENT_CREATE(CedarObjectConstants.RESOURCE_TEMPLATE_ELEMENT, CedarObjectConstants.ACCESS_CREATE),
  TEMPLATE_ELEMENT_READ(CedarObjectConstants.RESOURCE_TEMPLATE_ELEMENT, CedarObjectConstants.ACCESS_READ),
  TEMPLATE_ELEMENT_UPDATE(CedarObjectConstants.RESOURCE_TEMPLATE_ELEMENT, CedarObjectConstants.ACCESS_UPDATE),
  TEMPLATE_ELEMENT_DELETE(CedarObjectConstants.RESOURCE_TEMPLATE_ELEMENT, CedarObjectConstants.ACCESS_DELETE),

  TEMPLATE_FIELD_CREATE(CedarObjectConstants.RESOURCE_TEMPLATE_FIELD, CedarObjectConstants.ACCESS_CREATE),
  TEMPLATE_FIELD_READ(CedarObjectConstants.RESOURCE_TEMPLATE_FIELD, CedarObjectConstants.ACCESS_READ),
  TEMPLATE_FIELD_UPDATE(CedarObjectConstants.RESOURCE_TEMPLATE_FIELD, CedarObjectConstants.ACCESS_UPDATE),
  TEMPLATE_FIELD_DELETE(CedarObjectConstants.RESOURCE_TEMPLATE_FIELD, CedarObjectConstants.ACCESS_DELETE),

  TEMPLATE_INSTANCE_CREATE(CedarObjectConstants.RESOURCE_TEMPLATE_INSTANCE, CedarObjectConstants.ACCESS_CREATE),
  TEMPLATE_INSTANCE_READ(CedarObjectConstants.RESOURCE_TEMPLATE_INSTANCE, CedarObjectConstants.ACCESS_READ),
  TEMPLATE_INSTANCE_UPDATE(CedarObjectConstants.RESOURCE_TEMPLATE_INSTANCE, CedarObjectConstants.ACCESS_UPDATE),
  TEMPLATE_INSTANCE_DELETE(CedarObjectConstants.RESOURCE_TEMPLATE_INSTANCE, CedarObjectConstants.ACCESS_DELETE),

  JUST_AUTHORIZED("just", "authorized"),
  USER_PROFILE_OWN_READ(CedarObjectConstants.USER_PROFILE_OWN, CedarObjectConstants.ACCESS_READ);

  private final String resourceType;
  private final String accessType;
  private final String permissionName;

  CedarPermission(String resourceType, String accessType) {
    this.resourceType = resourceType;
    this.accessType = accessType;
    StringBuilder sb = new StringBuilder();
    sb.append("permission_");
    sb.append(resourceType);
    sb.append("_");
    sb.append(accessType);
    permissionName = sb.toString();
  }

  public String getAccessType() {
    return accessType;
  }

  public String getResourceType() {
    return resourceType;
  }

  public String getPermissionName() {
    return permissionName;
  }
}
