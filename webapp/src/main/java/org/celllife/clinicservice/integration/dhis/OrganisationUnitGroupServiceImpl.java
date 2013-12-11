package org.celllife.clinicservice.integration.dhis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-17
 * Time: 17h10
 */
@Service
public class OrganisationUnitGroupServiceImpl implements OrganisationUnitGroupService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DhisClient dhisClient;

    private String initialLink;

    @Cacheable("organisationUnitGroupLinkByName")
    public String findLinkByName(String name) {
        return findLink(name, getInitialLink());
    }

    private String findLink(String name, String initialLink) {

        Map<String, ?> organisationUnitGroupsMap = dhisClient.getMap(initialLink);

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnitGroups =
                (List<Map<String, ?>>) organisationUnitGroupsMap.get("organisationUnitGroups");

        for (Map<String, ?> organisationUnitGroup : organisationUnitGroups) {
            if (organisationUnitGroup.get("name").equals(name)) {
                return (String) organisationUnitGroup.get("href");
            }
        }

        @SuppressWarnings("unchecked")
        Map<String, ?> pager = (Map<String, ?>) organisationUnitGroupsMap.get("pager");
        String nextPageLink = (String) pager.get("nextPage");
        if (nextPageLink != null) {
            return findLink(name, nextPageLink);
        }

        return null;
    }

    private String getInitialLink() {

        if (initialLink == null) {
            initialLink = resourceService.findLinkByName("OrganisationUnitGroups");
        }

        return initialLink;
    }
}
