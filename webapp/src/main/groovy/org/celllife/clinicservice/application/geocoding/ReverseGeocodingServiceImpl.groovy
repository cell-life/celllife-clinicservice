package org.celllife.clinicservice.application.geocoding

import org.celllife.clinicservice.domain.Coordinate
import org.springframework.stereotype.Service;

/**
 * Default implementation of ReverseGeocodingService
 * See: http://maps.googleapis.com/maps/api/geocode/json?latlng=-33.933782,18.417606&sensor=true
 */
@Service
class ReverseGeocodingServiceImpl implements ReverseGeocodingService {

	public String getAddressFromCoordinates(Coordinate coordinates) {
		String address = null
		def client = new groovyx.net.http.RESTClient(getUrl(coordinates.getXCoordinate(), coordinates.getYCoordinate()))
		Map<String, Object> data = client.get([:]).data
		List<Map<String, Object>> results = data.get("results")
		if (results.size() >= 1) {
			Map<String, Object> addressComponents = results.get(0)
			address = addressComponents.get("formatted_address");
		}
		return address
	}
	
	private String getUrl(Double latitude, Double longitude) {
		return "http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&sensor=true"
	}
}
