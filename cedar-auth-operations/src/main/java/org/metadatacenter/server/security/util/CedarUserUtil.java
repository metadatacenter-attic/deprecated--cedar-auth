package org.metadatacenter.server.security.util;

import org.metadatacenter.config.BlueprintUIPreferences;
import org.metadatacenter.config.BlueprintUserProfile;
import org.metadatacenter.server.security.model.user.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CedarUserUtil {

  private CedarUserUtil() {

  }

  public static CedarUser createUserFromBlueprint(String id, String screenName, BlueprintUserProfile blueprint,
                                                  BlueprintUIPreferences uipref) {
    return createUserFromBlueprint(id, screenName, null, blueprint, uipref);
  }

  public static CedarUser createUserFromBlueprint(String id, String screenName, List<CedarUserRole> roles,
                                                  BlueprintUserProfile blueprint, BlueprintUIPreferences uipref) {
    CedarUser user = new CedarUser();
    user.setUserId(id);
    user.setScreenName(screenName);

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

    // set folder view defaults
    CedarUserUIFolderView folderView = user.getFolderView();
    folderView.setSortBy(uipref.getFolderView().getSortBy());
    folderView.setSortDirection(SortDirection.forValue(uipref.getFolderView().getSortDirection()));
    folderView.setViewMode(ViewMode.forValue(uipref.getFolderView().getViewMode()));

    // set resource type filter defaults
    CedarUserUIResourceTypeFilters resourceTypeFilters = user.getResourceTypeFilters();
    resourceTypeFilters.setField(true);
    resourceTypeFilters.setElement(true);
    resourceTypeFilters.setTemplate(true);
    resourceTypeFilters.setInstance(true);

    // set populate-a-template view defaults
    CedarUserUIPopulateATemplate populateATemplate = user.getPopulateATemplate();
    populateATemplate.setOpened(uipref.getPopulateATemplate().getOpened());
    populateATemplate.setSortBy(uipref.getPopulateATemplate().getSortBy());
    populateATemplate.setSortDirection(SortDirection.forValue(uipref.getPopulateATemplate().getSortDirection()));
    populateATemplate.setViewMode(ViewMode.forValue(uipref.getPopulateATemplate().getViewMode()));
    return user;
  }


}
