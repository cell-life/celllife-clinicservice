package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.province.Province;
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
public class DhisDistrictServiceImpl implements DhisDistrictService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private OrganisationUnitGroupService organisationUnitGroupService;

    @Autowired
    private DhisClient dhisClient;

    private String districtUrl;

    private String organisationUnitsUrl;

    public List<String> findAllExternalIds() {

        List<String> results = new ArrayList<>();

        Map<String, ?> organisationUnitGroupMap = dhisClient.getMap(getDistrictUrl());

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnits =
                (List<Map<String, ?>>) organisationUnitGroupMap.get("organisationUnits");

        for (Map<String, ?> organisationUnit : organisationUnits) {
            results.add((String) organisationUnit.get("id"));
        }

        return results;
    }

    public District findOne(String externalId) {

        Map<String, ?> districtMap = dhisClient.getMap(getOrganisationUnitsUrl() + "/" + externalId);

        if (districtMap == null) {
            return null;
        }

        return newDistrict(districtMap);
    }

    private District newDistrict(Map<String, ?> organisationUnit) {

        District district = new District();
        district.setExternalId((String) organisationUnit.get("id"));
        district.setName((String) organisationUnit.get("name"));
        district.setShortName((String) organisationUnit.get("shortName"));
        district.setCoordinates((String) organisationUnit.get("coordinates"));

        @SuppressWarnings("unchecked")
        Map<String, ?> parent = (Map<String, ?>) organisationUnit.get("parent");
        if (parent != null) {
            String id = (String) parent.get("id");
            String name = (String) parent.get("name");
            district.setProvince(new Province(id, "STUBBED: " + name));
        }

        return district;
    }

    private String getDistrictUrl() {

        if (districtUrl == null) {
            districtUrl = organisationUnitGroupService.findLinkByName("Health District");
        }

        return districtUrl;
    }

    private String getOrganisationUnitsUrl() {

        if (organisationUnitsUrl == null) {
            organisationUnitsUrl = resourceService.findLinkByName("OrganisationUnits");
        }

        return organisationUnitsUrl;
    }
}
