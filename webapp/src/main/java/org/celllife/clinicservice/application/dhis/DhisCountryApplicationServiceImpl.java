package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.country.Country;
import org.celllife.clinicservice.domain.country.CountryRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisCountryService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h15
 */
@Service
public class DhisCountryApplicationServiceImpl implements DhisCountryApplicationService {
	
	private static Logger log = LoggerFactory.getLogger(DhisCountryApplicationServiceImpl.class);

    @Autowired
    private DhisCountryService dhisCountryService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseCountry(String externalId) {

        Country dhisCountry = dhisCountryService.findOne(externalId);
        
        if (dhisCountry == null) {
        	log.warn("Could not find Country with externalId '"+externalId+"'");
        	return;
        }

        Country existingCountry = countryRepository.findByExternalId(dhisCountry.getExternalId());
        if (existingCountry == null) {
        	// taking into account that DHIS external ids can change
        	existingCountry = countryRepository.findOneByName(dhisCountry.getName());
        }

        if (existingCountry == null) {
        	log.info("Creating a new country with externalId '"+dhisCountry.getExternalId()+"' and name '"+dhisCountry.getName()+"'");
            countryRepository.save(dhisCountry);
        } else {
        	if (log.isDebugEnabled()) {
        		log.debug("Merging existing country with UID '"+existingCountry.getExternalId()+"' with country externalId '"+dhisCountry.getExternalId()+"' and name='"+dhisCountry.getName()+"'");
        	}
            mapper.map(dhisCountry, existingCountry);
            countryRepository.save(existingCountry);
        }
    }
}
