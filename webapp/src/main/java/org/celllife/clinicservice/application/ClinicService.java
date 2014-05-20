package org.celllife.clinicservice.application;

import org.celllife.clinicservice.domain.clinic.ClinicDTO;

/**
 * General purpose Clinic service
 */
public interface ClinicService {

	ClinicDTO findClinic(String clinicCode);
}
