package org.celllife.clinicservice.application.dhis;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h28
 */
public interface DhisApplicationService {

    void synchroniseAll();

    void synchroniseCountries();

    void synchroniseProvinces();

    void synchroniseDistricts();

    void synchroniseSubDistricts();

    void synchroniseClinics();
}
