package org.celllife.clinicservice.application.geocoding

import org.celllife.clinicservice.domain.Coordinate;

/**
 * Service that uses Google APIs to determine an address from a GPS location
 */
interface ReverseGeocodingService {

	String getAddressFromCoordinates(Coordinate coordinates);
}
