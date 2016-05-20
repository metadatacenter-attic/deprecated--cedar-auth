package org.metadatacenter.server.security.model.user;

public class CedarUserUIFolderView implements CedarUserUIPreferences {

  private String currentFolderId;
  private String sortBy;
  private SortOrder sortDirection;
  private ViewMode viewMode;

  public CedarUserUIFolderView() {
  }

  public String getCurrentFolderId() {
    return currentFolderId;
  }

  public void setCurrentFolderId(String currentFolderId) {
    this.currentFolderId = currentFolderId;
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
