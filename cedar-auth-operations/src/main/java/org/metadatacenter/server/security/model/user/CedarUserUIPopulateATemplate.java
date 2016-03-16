package org.metadatacenter.server.security.model.user;

public class CedarUserUIPopulateATemplate implements CedarUserUIPreferences {

  private PopulateATemplateStatus status;
  private String sortBy;
  private SortOrder sortDirection;
  private ViewMode viewMode;

  public CedarUserUIPopulateATemplate() {
  }

  public PopulateATemplateStatus getStatus() {
    return status;
  }

  public void setStatus(PopulateATemplateStatus status) {
    this.status = status;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public SortOrder getSortDirection() {
    return sortDirection;
  }

  public void setSortDirection(SortOrder sortDirection) {
    this.sortDirection = sortDirection;
  }

  public ViewMode getViewMode() {
    return viewMode;
  }

  public void setViewMode(ViewMode viewMode) {
    this.viewMode = viewMode;
  }
}
