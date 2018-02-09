package org.celllife.clinicservice.interfaces.service;

import java.util.List;

import org.celllife.clinicservice.application.ClinicService;
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
 * Controller that provides Clinic related services.
 * For example: finds the nearest clinic to a specified GPS location
 */
@Controller
public class ClinicController {

	@Autowired
	ClinicLocationBasedService clinicLocationBasedService;
	
	@Autowired
	ClinicService clinicService;

	@ResponseBody
	@RequestMapping(value = "/service/locateNearestClinic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ClinicDTO locateNearestClinic(
			@RequestParam("longitude") Double longitude,
			@RequestParam("latitude") Double latitude) {
		return clinicLocationBasedService.locateNearestClinic(longitude, latitude);
	}

    @ResponseBody
    @RequestMapping(value = "/service/locateNearestClinicWithGroups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ClinicDTO locateNearestClinic(
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude, 
            @RequestParam("includeGroups") String includeGroups,
            @RequestParam("excludeGroups") String excludeGroups) {
        String[] inGroups = includeGroups.split(",");
        String[] exGroups = excludeGroups.split(",");
        return clinicLocationBasedService.locateNearestClinic(longitude, latitude, inGroups, exGroups);
    }
    
    @ResponseBody
    @RequestMapping(value = "/service/locateNearestClinicsWithGroups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClinicDTO> locateNearestClinics(
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude, 
            @RequestParam("includeGroups") String includeGroups,
            @RequestParam("excludeGroups") String excludeGroups,
            @RequestParam("radiusMeters") Double radiusMeters) {
        String[] inGroups = includeGroups.split(",");
        String[] exGroups = excludeGroups.split(",");
        return clinicLocationBasedService.locateNearestClinics(longitude, latitude, inGroups, exGroups, radiusMeters);
    }
	
	@ResponseBody
	@RequestMapping(value = "/service/searchClinic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ClinicDTO findClinic(@RequestParam("code") String code) {
		return clinicService.findClinic(code);
	}

    @ResponseBody
    @RequestMapping(value = "/service/searchClinicByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ClinicDTO> searchClinic(@RequestParam("name") String name) {
        return clinicService.findClinicByName(name);
    }
}