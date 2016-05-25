package org.metadatacenter.server.security.util;


import org.metadatacenter.server.security.model.user.*;

import java.util.Date;
import java.util.UUID;

public class CedarUserUtil {

  private CedarUserUtil() {

  }

  public static CedarUser createUserFromBlueprint(String id, String screenName) {
    CedarUser user = new CedarUser();
    user.setUserId(id);
    user.setScreenName(screenName);

    CedarUserApiKey apiKey = new CedarUserApiKey();
    apiKey.setKey(UUID.randomUUID().toString());
    apiKey.setCreationDate(new Date());
    apiKey.setEnabled(true);
    apiKey.setServiceName("CEDAR");
    apiKey.setDescription("Auto-generated apiKey for CEDAR");

    user.getApiKeys().add(apiKey);

    user.getRoles().add(CedarUserRole.TEMPLATE_CREATOR);
    user.getRoles().add(CedarUserRole.TEMPLATE_INSTANTIATOR);

    CedarUserUIFolderView folderView = user.getFolderView();
    folderView.setSortBy("title");
    folderView.setSortDirection(SortOrder.ASC);
    folderView.setViewMode(ViewMode.GRID);

    CedarUserUIResourceTypeFilters resourceTypeFilters = user.getResourceTypeFilters();
    resourceTypeFilters.setField(true);
    resourceTypeFilters.setElement(true);
    resourceTypeFilters.setTemplate(true);
    resourceTypeFilters.setInstance(true);

    CedarUserUIPopulateATemplate populateATemplate = user.getPopulateATemplate();
    populateATemplate.setViewMode(ViewMode.GRID);
    populateATemplate.setOpened(true);
    populateATemplate.setSortBy("title");
    populateATemplate.setSortDirection(SortOrder.ASC);
    return user;
  }


}
