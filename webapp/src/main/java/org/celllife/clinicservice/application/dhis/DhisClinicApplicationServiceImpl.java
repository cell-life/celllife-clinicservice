package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.domain.clinic.ClinicRepository;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;
import org.celllife.clinicservice.domain.subdistrict.SubDistrictRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.aat.AatClinicService;
import org.celllife.clinicservice.integration.dhis.DhisClinicService;
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
public class DhisClinicApplicationServiceImpl implements DhisClinicApplicationService {
	
	private static Logger log = LoggerFactory.getLogger(DhisClinicApplicationServiceImpl.class);

    @Autowired
    private DhisClinicService dhisClinicService;

    @Autowired
    private AatClinicService aatClinicService;

    @Autowired
    private SubDistrictRepository subDistrictRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseClinic(String externalId) {

        Clinic dhisClinic = dhisClinicService.findOne(externalId);

        if (dhisClinic == null) {
        	log.warn("Could not find Clinic with externalId '"+externalId+"'");
            return;
        }

        // Wire up Sub-District
        SubDistrict dhisSubDistrict = dhisClinic.getSubDistrict();
        SubDistrict savedSubDistrict = saveSubDistrict(dhisSubDistrict);
        dhisClinic.setSubDistrict(savedSubDistrict);

        // Inject Clinic Code from AAT
        String clinicCode = aatClinicService.findClinicCodeByShortName(dhisClinic.getShortName());
        dhisClinic.setCode(clinicCode);

        // Check if clinic already exists
        Clinic existingClinic = clinicRepository.findByExternalId(externalId);
        if (existingClinic == null) {
        	// taking into account that DHIS external ids can change
        	existingClinic = clinicRepository.findOneByName(dhisClinic.getName());
        }

        if (existingClinic == null) {
        	log.info("Creating a new clinic with externalId '"+dhisClinic.getExternalId()+"' and name '"+dhisClinic.getName()+"'");
        	// Save new clinic
        	dhisClinic = clinicRepository.save(dhisClinic);
        	// now set the code = to the id
        	String lastCode = clinicRepository.getLastCode();
        	dhisClinic.updateCode(lastCode);
        	clinicRepository.save(dhisClinic);
        } else {
        	if (log.isDebugEnabled()) {
        		log.debug("Merging existing clinic with UID '"+existingClinic.getExternalId()+"' with clinic externalId '"+dhisClinic.getExternalId()+"' and name='"+dhisClinic.getName()+"'");
        	}
            // Merge incoming clinic with existing
            mapper.map(dhisClinic, existingClinic);
            clinicRepository.save(existingClinic);
        }
    }

    private SubDistrict saveSubDistrict(SubDistrict subDistrict) {

        if (subDistrict == null || subDistrict.getExternalId() == null) {
            return null;
        }

        SubDistrict savedSubDistrict = subDistrictRepository.findByExternalId(subDistrict.getExternalId());

        if (savedSubDistrict != null) {
            return savedSubDistrict;
        }

        return subDistrictRepository.save(subDistrict);
    }
}
