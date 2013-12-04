package org.celllife.clinicservice.interfaces.service;

import org.celllife.clinicservice.application.lbs.ClinicLocationBasedService;
import org.celllife.clinicservice.domain.clinic.ClinicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller that finds the nearest clinic to a specified GPS location
 */
@Controller
public class ClinicController {

	@Autowired
	ClinicLocationBasedService clinicLocationBasedService;

	@ResponseBody
	@RequestMapping(
		value = "/service/locateNearestClinic", 
		method = RequestMethod.GET, 
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ClinicDTO locateNearestClinic(
			@RequestParam("longitude") Double longitude,
			@RequestParam("latitude") Double latitude) {
		return clinicLocationBasedService.locateNearestClinic(longitude, latitude);
	}

}
