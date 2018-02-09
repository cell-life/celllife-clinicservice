package org.celllife.clinicservice.application.lbs;

import java.util.List;

import org.celllife.clinicservice.domain.clinic.ClinicDTO;

/**
 * An Location Based Service (LBS) to find the nearest Clinic given a GPS co-ordinate
 */
public interface ClinicLocationBasedService {

    /**
     * Finds general public health care facilities with clinics 
     * @param xCoordinate Double longitude
     * @param yCoordinate Double latitude
     * @return
     */
	ClinicDTO locateNearestClinic(Double xCoordinate, Double yCoordinate);

	/**
	 * Finds health care facilities in the specified groups.
	 *
	 * Examples of types are: Clinic, Community Day Centre, Community Health Centre, 
	 * District Hospital, Satellite Clinic, For-profit Facility
	 * 
	 * @param xCoordinate Double longitude
     * @param yCoordinate Double latitude
	 * @param includeGroups String[] containing the request type of facilities to include in the search
	 * @param excludeGroups String[] containing the types of facilities to exclude in the search
	 * @return
	 */
	ClinicDTO locateNearestClinic(Double xCoordinate, Double yCoordinate, String[] includeGroups, String[] excludeGroups);
	
	/**
	 * Finds the clinics within the specified radius
	 * 
     * @param xCoordinate Double longitude
     * @param yCoordinate Double latitude
     * @param includeGroups String[] containing the request type of facilities to include in the search
     * @param excludeGroups String[] containing the types of facilities to exclude in the search
	 * @param radiusMeters Double distance in meters in which to return clinics
	 * @return
	 */
	List<ClinicDTO> locateNearestClinics(Double xCoordinate, Double yCoordinate, String[] includeGroups, String[] excludeGroups, Double radiusMeters);
}
