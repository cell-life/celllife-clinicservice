package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.district.DistrictRepository;
import org.celllife.clinicservice.domain.province.Province;
import org.celllife.clinicservice.domain.province.ProvinceRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisDistrictService;
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
public class DhisDistrictApplicationServiceImpl implements DhisDistrictApplicationService {
	
	private static Logger log = LoggerFactory.getLogger(DhisDistrictApplicationServiceImpl.class);

    @Autowired
    private DhisDistrictService dhisDistrictService;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseDistrict(String externalId) {
        District dhisDistrict = dhisDistrictService.findOne(externalId);
        
        if (dhisDistrict == null) {
        	log.warn("Could not find Distrinct with externalId '"+externalId+"'");
        	return;
        }

        Province dhisProvince = dhisDistrict.getProvince();
        Province savedProvince = saveProvince(dhisProvince);
        dhisDistrict.setProvince(savedProvince);

        District existingDistrict = districtRepository.findByExternalId(externalId);
        if (existingDistrict == null) {
        	// taking into account that DHIS external ids can change
        	existingDistrict = districtRepository.findOneByName(dhisDistrict.getName());
        }
        if (existingDistrict == null) {
        	log.info("Creating a new district with externalId '"+dhisDistrict.getExternalId()+"' and name '"+dhisDistrict.getName()+"'");
            districtRepository.save(dhisDistrict);
        } else {
        	if (log.isDebugEnabled()) {
        		log.debug("Merging existing district with UID '"+existingDistrict.getExternalId()+"' with district externalId '"+dhisDistrict.getExternalId()+"' and name='"+dhisDistrict.getName()+"'");
        	}
            mapper.map(dhisDistrict, existingDistrict);
            districtRepository.save(existingDistrict);
        }
    }

    private Province saveProvince(Province province) {

        if (province == null || province.getExternalId() == null) {
            return null;
        }

        Province savedProvince = provinceRepository.findByExternalId(province.getExternalId());

        if (savedProvince != null) {
            return savedProvince;
        }

        return provinceRepository.save(province);
    }
}
