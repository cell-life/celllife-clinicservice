package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 11h47
 */
@Service
public class DhisSubDistrictServiceImpl implements DhisSubDistrictService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private OrganisationUnitGroupService organisationUnitGroupService;

    @Autowired
    private DhisClient dhisClient;

    private String subDistrictUrl;

    private String organisationUnitsUrl;

    public List<String> findAllExternalIds() {

        List<String> results = new ArrayList<>();

        Map<String, ?> organisationUnitGroupMap = dhisClient.getMap(getSubDistrictUrl());

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnits =
                (List<Map<String, ?>>) organisationUnitGroupMap.get("organisationUnits");

        for (Map<String, ?> organisationUnit : organisationUnits) {
            results.add((String) organisationUnit.get("id"));
        }

        return results;
    }

    public SubDistrict findOne(String externalId) {

        Map<String, ?> subDistrictMap = dhisClient.getMap(getOrganisationUnitsUrl() + "/" + externalId);

        if (subDistrictMap == null) {
            return null;
        }

        return newSubDistrict(subDistrictMap);
    }

    private SubDistrict newSubDistrict(Map<String, ?> organisationUnit) {

        SubDistrict subDistrict = new SubDistrict();
        subDistrict.setExternalId((String) organisationUnit.get("id"));
        subDistrict.setName((String) organisationUnit.get("name"));
        subDistrict.setShortName((String) organisationUnit.get("shortName"));
        subDistrict.setCoordinates((String) organisationUnit.get("coordinates"));

        @SuppressWarnings("unchecked")
        Map<String, ?> parent = (Map<String, ?>) organisationUnit.get("parent");
        if (parent != null) {
            String id = (String) parent.get("id");
            String name = (String) parent.get("name");
            subDistrict.setDistrict(new District(id, "STUBBED: " + name));
        }

        return subDistrict;
    }

    private String getSubDistrictUrl() {

        if (subDistrictUrl == null) {
            subDistrictUrl = organisationUnitGroupService.findLinkByName("Health sub-District");
        }

        return subDistrictUrl;
    }

    private String getOrganisationUnitsUrl() {

        if (organisationUnitsUrl == null) {
            organisationUnitsUrl = resourceService.findLinkByName("OrganisationUnits");
        }

        return organisationUnitsUrl;
    }
}
