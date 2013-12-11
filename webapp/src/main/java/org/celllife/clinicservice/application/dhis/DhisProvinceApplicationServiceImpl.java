package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.country.Country;
import org.celllife.clinicservice.domain.country.CountryRepository;
import org.celllife.clinicservice.domain.province.Province;
import org.celllife.clinicservice.domain.province.ProvinceRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisProvinceService;
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
public class DhisProvinceApplicationServiceImpl implements DhisProvinceApplicationService {
	
	private static Logger log = LoggerFactory.getLogger(DhisProvinceApplicationServiceImpl.class);

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
        Province dhisProvince = dhisProvinceService.findOne(externalId);
        if (dhisProvince == null) {
        	log.warn("Could not find Province with externalId '"+externalId+"'");
        	return;
        }

        Country dhisCountry = dhisProvince.getCountry();
        Country savedCountry = saveCountry(dhisCountry);
        dhisProvince.setCountry(savedCountry);

        Province existingProvince = provinceRepository.findByExternalId(externalId);
        if (existingProvince == null) {
        	// taking into account that DHIS external ids can change
        	existingProvince = provinceRepository.findOneByName(dhisProvince.getName());
        }
        
        if (existingProvince == null) {
        	log.info("Creating a new province with externalId '"+dhisProvince.getExternalId()+"' and name '"+dhisProvince.getName()+"'");
            provinceRepository.save(dhisProvince);
        } else {
        	if (log.isDebugEnabled()) {
        		log.debug("Merging existing province with UID '"+existingProvince.getExternalId()+"' with province externalId '"+dhisProvince.getExternalId()+"' and name='"+dhisProvince.getName()+"'");
        	}
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
