package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.country.Country;
import org.celllife.clinicservice.domain.province.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-17
 * Time: 17h10
 */
@Service
public class DhisProvinceServiceImpl implements DhisProvinceService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private OrganisationUnitGroupService organisationUnitGroupService;

    @Autowired
    private DhisClient dhisClient;

    private String provinceUrl;

    private String organisationUnitsUrl;

    public List<String> findAllExternalIds() {

        List<String> results = new ArrayList<>();

        Map<String, ?> organisationUnitGroupMap = dhisClient.getMap(getProvinceUrl());

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnits =
                (List<Map<String, ?>>) organisationUnitGroupMap.get("organisationUnits");

        for (Map<String, ?> organisationUnit : organisationUnits) {
            results.add((String) organisationUnit.get("id"));
        }

        return results;
    }

    public Province findOne(String externalId) {

        Map<String, ?> provinceMap = dhisClient.getMap(getOrganisationUnitsUrl() + "/" + externalId);

        if (provinceMap == null) {
            return null;
        }

        return newProvince(provinceMap);
    }

    private Province newProvince(Map<String, ?> organisationUnit) {

        Province province = new Province();
        province.setExternalId((String) organisationUnit.get("id"));
        province.setName((String) organisationUnit.get("name"));
        province.setShortName((String) organisationUnit.get("shortName"));
        province.setCoordinates((String) organisationUnit.get("coordinates"));

        @SuppressWarnings("unchecked")
        Map<String, ?> parent = (Map<String, ?>) organisationUnit.get("parent");
        if (parent != null) {
            String id = (String) parent.get("id");
            String name = (String) parent.get("name");
            province.setCountry(new Country(id, "STUBBED: " + name));
        }

        return province;
    }

    private String getProvinceUrl() {

        if (provinceUrl == null) {
            provinceUrl = organisationUnitGroupService.findLinkByName("Province");
        }

        return provinceUrl;
    }

    private String getOrganisationUnitsUrl() {

        if (organisationUnitsUrl == null) {
            organisationUnitsUrl = resourceService.findLinkByName("OrganisationUnits");
        }

        return organisationUnitsUrl;
    }
}
