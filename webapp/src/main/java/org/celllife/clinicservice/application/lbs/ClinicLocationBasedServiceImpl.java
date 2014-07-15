package org.celllife.clinicservice.application.lbs;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.celllife.clinicservice.application.geocoding.ReverseGeocodingService;
import org.celllife.clinicservice.domain.Coordinate;
import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.domain.clinic.ClinicDTO;
import org.celllife.clinicservice.domain.clinic.ClinicRepository;
import org.celllife.clinicservice.domain.clinic.Group;
import org.celllife.clinicservice.domain.exception.InvalidCoordinateException;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicLocationBasedServiceImpl implements ClinicLocationBasedService {

	/** only checks clinics in the specified groups */
	private static final String[] GROUPS_NAMES = new String[] { "Clinic", "Community Day Centre",
			"Community Health Centre", "District Hospital", "Satellite Clinic" };
	
	private static final String[] GROUPS_NAMES_EXCLUDE = new String[] { "For-profit Facility" };
	
	/** indicates an invalid address */
	private static final String[] INVALID_ADDRESS_PREFIX = new String[] { 
		"Box", "P.O Box", "P.O. Box", "PO Box", "PO. Box", "P.Bag", "P.B", "P.B.", "PB", "N/A"
	};
	private static final String[] INVALID_ADDRESSES = new String[] {
		"", "Unknown", "None", "No", "No address", "No street address", "No street name"
	};

	private static Logger log = LoggerFactory.getLogger(ClinicLocationBasedServiceImpl.class);

	@Autowired
	ClinicRepository clinicRepository;
	
	@Autowired
	ReverseGeocodingService reverseGeocodingService;

	private final Map<String, String> simpleAddressCache = new ConcurrentHashMap<String, String>();

	@Override
	@Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
	public ClinicDTO locateNearestClinic(Double xCoordinate, Double yCoordinate) {
	    return locateNearestClinic(xCoordinate, yCoordinate, GROUPS_NAMES, GROUPS_NAMES_EXCLUDE);
	}
	
	@Override
	@Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
	public ClinicDTO locateNearestClinic(Double xCoordinate, Double yCoordinate, String[] includeGroups, String[] excludeGroups) {
        // At the moment this just loops through all the clinics.
        // In the future, we should first determine which region the specific
        // location falls in and then only search those clinics
        Iterable<Clinic> clinicIterable = clinicRepository.findDistinctByGroupsNameIn(includeGroups);
        Iterator<Clinic> it = clinicIterable.iterator();
        Double closestDistance = null;
        Clinic closestClinic = null;
        int counter = 0;
        while (it.hasNext()) {
            Clinic clinic = it.next();
            if (exclude(clinic, excludeGroups)) {
                continue;
            }
            counter++;
            try {
                Coordinate clinicLocation = new Coordinate(clinic.getCoordinates());
                double distance = getDistance(xCoordinate, yCoordinate, clinicLocation.getXCoordinate(),
                        clinicLocation.getYCoordinate());
                if (closestClinic == null || distance < closestDistance) {
                    closestDistance = distance;
                    closestClinic = clinic;
                }
            } catch (InvalidCoordinateException e) {
                log.trace("Ignoring clinic " + clinic.getShortName() + " (" + clinic.getId() + ") - " + e.getMessage());
            }
        }
        log.info("Checked " + counter + " clinics. The nearest clinic is " + closestClinic.getShortName()
                + " with a distance of " + closestDistance + " groups=" + closestClinic.getGroups() + " coordinates="
                + closestClinic.getCoordinates());
        
        ClinicDTO clinicDTO = new ClinicDTO(closestClinic);
        if (isInvalidAddress(clinicDTO.getAddress())) {
            String newAddress = simpleAddressCache.get(clinicDTO.getCoordinates());
            if (newAddress == null || newAddress.trim().isEmpty()) {
                log.debug("Using Google reverse geocoding service in order to determine the address at '"+clinicDTO.getCoordinates()+"'.");
                newAddress = reverseGeocodingService.getAddressFromCoordinates(new Coordinate(clinicDTO.getCoordinates()));
                if (newAddress != null) {
                    simpleAddressCache.put(clinicDTO.getCoordinates(), newAddress);
                }
            }
            log.debug("Clinic "+closestClinic.getShortName()+" has an invalid address '"+closestClinic.getAddress()+"'. Using address is '"+newAddress+"' instead.");
            clinicDTO.setAddress(newAddress);
        }
        return clinicDTO;
	}
	
	boolean exclude(Clinic clinic, String[] excludeGroups) {
	    for (Group group : clinic.getGroups()) {
	        for (String groupName : excludeGroups) {
	            if (group.getName().equals(groupName)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	boolean isInvalidAddress(String currentAddress) {
		if (currentAddress == null) {
			return true;
		} else {
			if (currentAddress.trim().equals("")) {
				return true;
			}
			if (currentAddress.matches("[0-9]+")) {
				return true;
			}
			for (String prefix : INVALID_ADDRESS_PREFIX) {
				if (currentAddress.toUpperCase().startsWith(prefix.toUpperCase())) {
					return true;
				}
			}
			for (String address : INVALID_ADDRESSES) {
				if (currentAddress.toUpperCase().trim().equals(address.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}

	// taken from:
	// https://github.com/janantala/GPS-distance/blob/master/java/Distance.java
	private static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double a = 6378137, b = 6356752.314245, f = 1 / 298.257223563;
		double L = Math.toRadians(lon2 - lon1);
		double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
		double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
		double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
		double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
		double cosSqAlpha;
		double sinSigma;
		double cos2SigmaM;
		double cosSigma;
		double sigma;

		double lambda = L, lambdaP, iterLimit = 100;
		do {
			double sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda)
					+ (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
			if (sinSigma == 0) {
				return 0;
			}

			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			double sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;

			double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
			lambdaP = lambda;
			lambda = L + (1 - C) * f * sinAlpha
					* (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));

		} while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

		if (iterLimit == 0) {
			return 0;
		}

		double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
		double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double deltaSigma = B
				* sinSigma
				* (cos2SigmaM + B
						/ 4
						* (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM
								* (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));

		double s = b * A * (sigma - deltaSigma);

		return s;
	}
}
