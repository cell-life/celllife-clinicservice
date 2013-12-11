package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.district.DistrictRepository;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;
import org.celllife.clinicservice.domain.subdistrict.SubDistrictRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisSubDistrictService;
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
public class DhisSubDistrictApplicationServiceImpl implements DhisSubDistrictApplicationService {
	
	private static Logger log = LoggerFactory.getLogger(DhisSubDistrictApplicationServiceImpl.class);

    @Autowired
    private DhisSubDistrictService dhisSubDistrictService;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SubDistrictRepository subDistrictRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseSubDistrict(String externalId) {
        SubDistrict dhisSubDistrict = dhisSubDistrictService.findOne(externalId);
        if (dhisSubDistrict == null) {
        	log.warn("Could not find Sub District with externalId '"+externalId+"'");
        	return;
        }

        District dhisDistrict = dhisSubDistrict.getDistrict();
        District savedDistrict = saveDistrict(dhisDistrict);
        dhisSubDistrict.setDistrict(savedDistrict);

        SubDistrict existingSubDistrict = subDistrictRepository.findByExternalId(externalId);
        if (existingSubDistrict == null) {
        	// taking into account that DHIS external ids can change
        	existingSubDistrict = subDistrictRepository.findOneByName(dhisSubDistrict.getName());
        }
        
        if (existingSubDistrict == null) {
        	log.info("Creating a new subdistrict with externalId '"+dhisSubDistrict.getExternalId()+"' and name '"+dhisSubDistrict.getName()+"'");
            subDistrictRepository.save(dhisSubDistrict);
        } else {
        	if (log.isDebugEnabled()) {
        		log.debug("Merging existing subdistrict with UID '"+existingSubDistrict.getExternalId()+"' with subdistrict externalId '"+dhisSubDistrict.getExternalId()+"' and name='"+dhisSubDistrict.getName()+"'");
        	}
            mapper.map(dhisSubDistrict, existingSubDistrict);
            subDistrictRepository.save(existingSubDistrict);
        }
    }

    private District saveDistrict(District district) {

        if (district == null || district.getExternalId() == null) {
            return null;
        }

        District savedDistrict = districtRepository.findByExternalId(district.getExternalId());

        if (savedDistrict != null) {
            return savedDistrict;
        }

        return districtRepository.save(district);
    }
}
