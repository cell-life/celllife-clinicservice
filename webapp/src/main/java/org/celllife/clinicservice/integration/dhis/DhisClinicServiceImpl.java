package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 11h47
 */
@Service
public class DhisClinicServiceImpl implements DhisClinicService {

    private static final List<String> nonClinicOrganisationalUnitGroupCodes = Arrays.asList(
            "Country",
            "Health District",
            "Health sub-District",
            "District Managed Area",
            "Province",
            "Public Admin",
            "Rep Unit",
            "Training Facility"
    );

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DhisClient dhisClient;

    private String organisationUnitsUrl;

    public List<String> findAllExternalIds() {
        return findAllExternalIds(getOrganisationUnitsUrl());
    }

    private List<String> findAllExternalIds(String organisationUnitsUrl) {

        List<String> results = new ArrayList<>();

        Map<String, ?> organisationUnitsMap = dhisClient.getMap(organisationUnitsUrl);

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnits = (List<Map<String, ?>>) organisationUnitsMap.get("organisationUnits");

        for (Map<String, ?> organisationUnit : organisationUnits) {
            results.add((String) organisationUnit.get("id"));
        }

        @SuppressWarnings("unchecked")
        Map<String, ?> pager = (Map<String, ?>) organisationUnitsMap.get("pager");
        String nextPageUrl = (String) pager.get("nextPage");
        if (nextPageUrl != null) {
            results.addAll(findAllExternalIds(nextPageUrl));
        }

        return results;
    }

    public Clinic findOne(String externalId) {

        Map<String, ?> clinicMap = dhisClient.getMap(getOrganisationUnitsUrl() + "/" + externalId);

        if (clinicMap == null) {
            return null;
        }

        if (!isClinic(clinicMap)) {
            return null;
        }

        return newClinic(clinicMap);
    }

    private static boolean isClinic(Map<String, ?> clinicMap) {

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnitGroups = (List<Map<String, ?>>) clinicMap.get("organisationUnitGroups");

        for (Map<String, ?> organisationUnitGroup : organisationUnitGroups) {

            String organisationUnitGroupCode = (String) organisationUnitGroup.get("code");

            if (nonClinicOrganisationalUnitGroupCodes.contains(organisationUnitGroupCode)) {
                return false;
            }
        }

        return true;
    }

    private Clinic newClinic(Map<String, ?> organisationUnit) {

        Clinic clinic = new Clinic();
        clinic.setExternalId((String) organisationUnit.get("id"));
        clinic.setName((String) organisationUnit.get("name"));
        clinic.setShortName((String) organisationUnit.get("shortName"));
        clinic.setCoordinates((String) organisationUnit.get("coordinates"));

        // Get Parent Data

        @SuppressWarnings("unchecked")
        Map<String, ?> parent = (Map<String, ?>) organisationUnit.get("parent");
        if (parent != null) {
            String id = (String) parent.get("id");
            String name = (String) parent.get("name");
            clinic.setSubDistrict(new SubDistrict(id, "STUBBED: " + name));
        }

        // Get Organisation Group Memberships

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnitGroups =
                (List<Map<String, ?>>) organisationUnit.get("organisationUnitGroups");

        for (Map<String, ?> organisationUnitGroup : organisationUnitGroups) {
            clinic.addGroup((String) organisationUnitGroup.get("code"));
        }

        return clinic;
    }

    private String getOrganisationUnitsUrl() {

        if (organisationUnitsUrl == null) {
            organisationUnitsUrl = resourceService.findLinkByName("OrganisationUnits");
        }

        return organisationUnitsUrl;
    }
}
