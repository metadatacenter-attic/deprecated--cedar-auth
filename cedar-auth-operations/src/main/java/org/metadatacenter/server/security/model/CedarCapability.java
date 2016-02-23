package org.metadatacenter.server.security.model;


public enum CedarCapability {

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
  TEMPLATE_INSTANCE_DELETE(CedarObjectConstants.RESOURCE_TEMPLATE_INSTANCE, CedarObjectConstants.ACCESS_DELETE);

  private final String resourceType;
  private final String accessType;
  private final String capabilityName;

  CedarCapability(String resourceType, String accessType) {
    this.resourceType = resourceType;
    this.accessType = accessType;
    StringBuilder sb = new StringBuilder();
    sb.append("capability_");
    sb.append(resourceType);
    sb.append("_");
    sb.append(accessType);
    capabilityName = sb.toString();
  }

  public String getAccessType() {
    return accessType;
  }

  public String getResourceType() {
    return resourceType;
  }

  public String getCapabilityName() {
    return capabilityName;
  }
}
