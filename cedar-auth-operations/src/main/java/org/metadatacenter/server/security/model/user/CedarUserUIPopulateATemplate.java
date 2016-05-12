package org.metadatacenter.server.security.model.user;

public class CedarUserUIPopulateATemplate implements CedarUserUIPreferences {

  private boolean opened;
  private String sortBy;
  private SortOrder sortDirection;
  private ViewMode viewMode;

  public CedarUserUIPopulateATemplate() {
  }

  public boolean isOpened() {
    return opened;
  }

  public void setOpened(boolean opened) {
    this.opened = opened;
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
