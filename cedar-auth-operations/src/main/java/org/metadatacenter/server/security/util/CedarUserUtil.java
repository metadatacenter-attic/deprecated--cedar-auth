package org.metadatacenter.server.security.util;

import org.metadatacenter.config.BlueprintUIPreferences;
import org.metadatacenter.config.BlueprintUserProfile;
import org.metadatacenter.config.CedarConfig;
import org.metadatacenter.server.security.CedarUserRolePermissionUtil;
import org.metadatacenter.server.security.model.user.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CedarUserUtil {

  private CedarUserUtil() {
  }

  public static CedarUser createUserFromBlueprint(ICedarUserRepresentation ur) {
    return createUserFromBlueprint(ur, null);
  }

  public static String buildScreenName(ICedarUserRepresentation ur) {
    if (ur != null) {
      CedarConfig cedarConfig = CedarConfig.getInstance();
      String screenName = cedarConfig.getBlueprintUserProfile().getScreenNameTemplate();
      if (screenName != null) {
        screenName = screenName.replace("{firstName}", ur.getFirstName() == null ? "" : ur.getFirstName());
        screenName = screenName.replace("{lastName}", ur.getLastName() == null? "" : ur.getLastName());
      }
      return screenName;
    } else {
      return null;
    }
  }

  public static void fillScreenName(ICedarUserRepresentation ur) {
    if (ur != null) {
      String screenName = CedarUserUtil.buildScreenName(ur);
      ur.setDisplayName(screenName);
    }
  }

  public static String buildHomeFolderDescription(ICedarUserRepresentation ur) {
    CedarConfig cedarConfig = CedarConfig.getInstance();
    String homeDescription = cedarConfig.getBlueprintUserProfile().getHomeFolderDescriptionTemplate();
    homeDescription = homeDescription.replace("{firstName}", ur.getFirstName());
    homeDescription = homeDescription.replace("{lastName}", ur.getLastName());
    return homeDescription;
  }

  public static CedarUser createUserFromBlueprint(ICedarUserRepresentation ur, List<CedarUserRole> roles) {
    CedarConfig cedarConfig = CedarConfig.getInstance();
    BlueprintUserProfile blueprint = cedarConfig.getBlueprintUserProfile();
    BlueprintUIPreferences uiPref = cedarConfig.getBlueprintUIPreferences();

    String screenName = buildScreenName(ur);

    CedarUser user = new CedarUser();
    user.setUserId(ur.getUserId());
    user.setScreenName(screenName);
    user.setFirstName(ur.getFirstName());
    user.setLastName(ur.getLastName());

    LocalDateTime now = LocalDateTime.now();
    // create a default API Key
    CedarUserApiKey apiKey = new CedarUserApiKey();
    apiKey.setKey(UUID.randomUUID().toString());
    apiKey.setCreationDate(now);
    apiKey.setEnabled(true);
    apiKey.setServiceName(blueprint.getDefaultAPIKey().getServiceName());
    apiKey.setDescription(blueprint.getDefaultAPIKey().getDescription());

    user.getApiKeys().add(apiKey);

    if (roles == null || roles.isEmpty()) {
      user.getRoles().add(CedarUserRole.TEMPLATE_CREATOR);
      user.getRoles().add(CedarUserRole.TEMPLATE_INSTANTIATOR);
    } else {
      user.getRoles().addAll(roles);
    }
    CedarUserRolePermissionUtil.expandRolesIntoPermissions(user);

    // set folder view defaults
    CedarUserUIFolderView folderView = user.getFolderView();
    folderView.setSortBy(uiPref.getFolderView().getSortBy());
    folderView.setSortDirection(SortDirection.forValue(uiPref.getFolderView().getSortDirection()));
    folderView.setViewMode(ViewMode.forValue(uiPref.getFolderView().getViewMode()));

    // set resource type filter defaults
    CedarUserUIResourceTypeFilters resourceTypeFilters = user.getResourceTypeFilters();
    resourceTypeFilters.setField(true);
    resourceTypeFilters.setElement(true);
    resourceTypeFilters.setTemplate(true);
    resourceTypeFilters.setInstance(true);

    // set populate-a-template view defaults
    CedarUserUIPopulateATemplate populateATemplate = user.getPopulateATemplate();
    populateATemplate.setOpened(uiPref.getPopulateATemplate().getOpened());
    populateATemplate.setSortBy(uiPref.getPopulateATemplate().getSortBy());
    populateATemplate.setSortDirection(SortDirection.forValue(uiPref.getPopulateATemplate().getSortDirection()));
    populateATemplate.setViewMode(ViewMode.forValue(uiPref.getPopulateATemplate().getViewMode()));
    return user;
  }

}
