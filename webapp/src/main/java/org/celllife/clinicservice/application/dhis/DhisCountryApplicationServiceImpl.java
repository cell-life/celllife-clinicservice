package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.country.Country;
import org.celllife.clinicservice.domain.country.CountryRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisCountryService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h15
 */
@Service
public class DhisCountryApplicationServiceImpl implements DhisCountryApplicationService {

    @Autowired
    private DhisCountryService dhisCountryService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseCountry(String externalId) {

        Country dhisCountry = dhisCountryService.findOne(externalId);

        Country existingCountry = countryRepository.findByExternalId(dhisCountry.getExternalId());

        if (existingCountry == null) {
            countryRepository.save(dhisCountry);
        } else {
            mapper.map(dhisCountry, existingCountry);
            countryRepository.save(existingCountry);
        }
    }
}
