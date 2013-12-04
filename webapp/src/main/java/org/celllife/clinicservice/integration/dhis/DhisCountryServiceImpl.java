package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.country.Country;
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
public class DhisCountryServiceImpl implements DhisCountryService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private OrganisationUnitGroupService organisationUnitGroupService;

    @Autowired
    private DhisClient dhisClient;

    private String countryUrl;

    private String organisationUnitsUrl;

    public List<String> findAllExternalIds() {

        List<String> results = new ArrayList<>();

        Map<String, ?> organisationUnitGroupMap = dhisClient.getMap(getCountryUrl());

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> organisationUnits =
                (List<Map<String, ?>>) organisationUnitGroupMap.get("organisationUnits");

        for (Map<String, ?> organisationUnit : organisationUnits) {
            results.add((String) organisationUnit.get("id"));
        }

        return results;
    }

    public Country findOne(String externalId) {

        Map<String, ?> provinceMap = dhisClient.getMap(getOrganisationUnitsUrl() + "/" + externalId);

        if (provinceMap == null) {
            return null;
        }

        return newCountry(provinceMap);
    }

    private Country newCountry(Map<String, ?> organisationUnit) {

        Country country = new Country();
        country.setExternalId((String) organisationUnit.get("id"));
        country.setName((String) organisationUnit.get("name"));
        country.setShortName((String) organisationUnit.get("shortName"));

        return country;
    }

    private String getCountryUrl() {

        if (countryUrl == null) {
            countryUrl = organisationUnitGroupService.findLinkByName("Country");
        }

        return countryUrl;
    }

    private String getOrganisationUnitsUrl() {

        if (organisationUnitsUrl == null) {
            organisationUnitsUrl = resourceService.findLinkByName("OrganisationUnits");
        }

        return organisationUnitsUrl;
    }
}
