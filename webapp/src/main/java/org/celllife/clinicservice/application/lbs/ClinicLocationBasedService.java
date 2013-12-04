package org.celllife.clinicservice.application.lbs;

import org.celllife.clinicservice.domain.clinic.ClinicDTO;

/**
 * An Location Based Service (LBS) to find the nearest Clinic given a GPS co-ordinate
 */
public interface ClinicLocationBasedService {

	ClinicDTO locateNearestClinic(Double xCoordinate, Double yCoordinate);
}
