package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.country.Country;

import java.util.List;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-17
 * Time: 18h19
 */
public interface DhisCountryService {

    List<String> findAllExternalIds();

    Country findOne(String id);
}
