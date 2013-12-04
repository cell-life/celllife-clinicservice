package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.integration.dhis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h15
 */
@Service
public class DhisApplicationServiceImpl implements DhisApplicationService {

    @Autowired
    private DhisCountryService dhisCountryService;

    @Autowired
    private DhisProvinceService dhisProvinceService;

    @Autowired
    private DhisDistrictService dhisDistrictService;

    @Autowired
    private DhisSubDistrictService dhisSubDistrictService;

    @Autowired
    private DhisClinicService dhisClinicService;

    @Autowired
    private DhisClinicApplicationService dhisClinicApplicationService;

    @Autowired
    private DhisCountryApplicationService dhisCountryApplicationService;

    @Autowired
    private DhisProvinceApplicationService dhisProvinceApplicationService;

    @Autowired
    private DhisDistrictApplicationService dhisDistrictApplicationService;

    @Autowired
    private DhisSubDistrictApplicationService dhisSubDistrictApplicationService;

    @Scheduled(cron = "${dhis.synchronisation.cron}")
    public void synchroniseAll() {

        synchroniseCountries();

        synchroniseProvinces();

        synchroniseDistricts();

        synchroniseSubDistricts();

        synchroniseClinics();
    }

    public void synchroniseCountries() {

        List<String> dhisCountryExternalIds = dhisCountryService.findAllExternalIds();

        for (String dhisCountryExternalId : dhisCountryExternalIds) {
            dhisCountryApplicationService.synchroniseCountry(dhisCountryExternalId);
        }
    }

    public void synchroniseProvinces() {

        List<String> dhisProvinceExternalIds = dhisProvinceService.findAllExternalIds();

        for (String dhisProvinceExternalId : dhisProvinceExternalIds) {
            dhisProvinceApplicationService.synchroniseProvince(dhisProvinceExternalId);
        }
    }

    public void synchroniseDistricts() {

        List<String> dhisDistrictExternalIds = dhisDistrictService.findAllExternalIds();

        for (String dhisDistrictExternalId : dhisDistrictExternalIds) {
            dhisDistrictApplicationService.synchroniseDistrict(dhisDistrictExternalId);
        }
    }

    public void synchroniseSubDistricts() {

        List<String> dhisSubDistrictExternalIds = dhisSubDistrictService.findAllExternalIds();

        for (String dhisSubDistrictExternalId : dhisSubDistrictExternalIds) {
            dhisSubDistrictApplicationService.synchroniseSubDistrict(dhisSubDistrictExternalId);
        }
    }

    public void synchroniseClinics() {

        List<String> dhisClinicExternalIds = dhisClinicService.findAllExternalIds();

        for (String dhisClinicExternalId : dhisClinicExternalIds) {
            dhisClinicApplicationService.synchroniseClinic(dhisClinicExternalId);
        }
    }
}
