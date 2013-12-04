package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.country.Country;
import org.celllife.clinicservice.domain.country.CountryRepository;
import org.celllife.clinicservice.domain.province.Province;
import org.celllife.clinicservice.domain.province.ProvinceRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisProvinceService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h15
 */
@Service
public class DhisProvinceApplicationServiceImpl implements DhisProvinceApplicationService {

    @Autowired
    private DhisProvinceService dhisProvinceService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseProvince(String externalId) {
        Province dhisProvince;
        dhisProvince = dhisProvinceService.findOne(externalId);

        Country dhisCountry = dhisProvince.getCountry();
        Country savedCountry = saveCountry(dhisCountry);
        dhisProvince.setCountry(savedCountry);

        Province existingProvince = provinceRepository.findByExternalId(externalId);
        if (existingProvince == null) {
            provinceRepository.save(dhisProvince);
        } else {
            mapper.map(dhisProvince, existingProvince);
            provinceRepository.save(existingProvince);
        }
    }

    private Country saveCountry(Country country) {

        if (country == null || country.getExternalId() == null) {
            return null;
        }

        Country savedCountry = countryRepository.findByExternalId(country.getExternalId());

        if (savedCountry != null) {
            return savedCountry;
        }

        return countryRepository.save(country);
    }

}
