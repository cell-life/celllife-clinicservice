package org.celllife.clinicservice.application;

import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.domain.clinic.ClinicDTO;
import org.celllife.clinicservice.domain.clinic.ClinicRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl implements ClinicService {

	private static Logger log = LoggerFactory.getLogger(ClinicServiceImpl.class);

	@Autowired
	ClinicRepository clinicRepository;
	
	@Override
	@Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
	public ClinicDTO findClinic(String clinicCode) {
		ClinicDTO clinicFound = null;
		Clinic clinic = clinicRepository.findOneByCode(clinicCode);
		if (clinic != null) {
			clinicFound = new ClinicDTO(clinic);
		}
		return clinicFound;
	}
}
