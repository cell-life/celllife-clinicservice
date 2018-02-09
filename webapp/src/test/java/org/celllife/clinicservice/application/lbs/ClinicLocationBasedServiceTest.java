package org.celllife.clinicservice.application.lbs;

import java.util.List;

import junit.framework.Assert;

import org.celllife.clinicservice.domain.clinic.ClinicDTO;
import org.celllife.clinicservice.test.TestConfigurationNoAsync;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = TestConfigurationNoAsync.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ClinicLocationBasedServiceTest {

	@Autowired
	private ClinicLocationBasedService clinicLocationBasedService;
	
	@Test
	public void testMasiphumelele() throws Exception {
	    String[] includeGroups = new String[] { "Clinic", "Community Day Centre",
	            "Community Health Centre", "District Hospital", "Satellite Clinic", 
	            "Province Facility", "Rural", "Mobile Service", "Urban", "Province Aided Facility", "Frail Care", 
	            "ART Accred Site", "Peri-Urban", "Crisis Centre", "For-profit Facility", "Private Hospital", "Community Health Centre",
	            "Non-medical Site", "Specialised Hospital", "Correctional Service", "Other dept Facility", "Not-for-profit Facility",
	            "Faith-Based Facility", "Provincial Tertiary Hospital", "Pharmacy", "Occupational Health Clinic", "Health Post",
	            "General Practitioner", "Specialised Psychiatric Hospital", "Municipality Facility", "NGO Facility", "Specialised TB Hospital",
	            "Regional Hospital", "Industry Clinic", "Psychiatry Service", "School Health Service", "Special Clinic", "Occupational Health Service",
	            "National Central Hospital", "Nurse Practitioner", "Medical Centre", "Midwife Obstetrics Unit", "Community Day Centre",
	            "HIV/AIDS Support Centre", "Surgical Centre", "VCT Clinic", "Support Centre", "Home Based Care", "Hospital",
	            "Reproductive Service", "Place of Safety", "Rehabilitation Service", "Psychiatry Home", "Specialised Chronic Hospital",
	            "Step Down Facility", "Hospice", "Gateway Clinic", "EMS Station", "Province EMS", "Oral Health Service", "Health Education Service",
	            "Ward", "Gov Province", "Tier 1", "Not an ART Site", "For-profit", "Mental Health Service", "Not-for-profit",
	            "Occupational Health Centre", "Priority Facility", "Large District Hospital", "Small District Hospital",
	            "Oral Health Centre", "Medium District Hospital", "Private Clinic", "Gov Municipality", "Placeholder", "Specialised Hospital (Gaz)",
	            "Province Aided", "Provincial Tertiary Hospital (Gaz)", "Specialised TB Hospital (Gaz)", "STI Sentinel Site",
	            "Regional Hospital (Gaz)", "Forensic Pathology", "VCT", "Correctional Centre", "Environmental Health Service",
	            "Community Health Centre / Clinic", "Community Health Centre (After hours)", "Specialist", "Central Hospital"};
	    List<ClinicDTO> clinics = clinicLocationBasedService.locateNearestClinics(-34.1291,18.3784, includeGroups, new String[] {}, 10000.00);
	    for (ClinicDTO clinic : clinics) {
	        System.out.println("Found "+clinic.getName()+" ("+clinic.getShortName()+") phone="+clinic.getPhoneNumber()+" address="+clinic.getAddress()+" gps="+clinic.getCoordinates());
	    }
	}
	
	@Test
	public void test123HopeStreet() throws Exception {
		ClinicDTO clinic = clinicLocationBasedService.locateNearestClinic(-33.933782,18.417606);
		Assert.assertNotNull(clinic);
		Assert.assertTrue(clinic.getName().contains("Nurock"));
	}

	@Test
	public void test6MotramdaleRd() throws Exception {
		ClinicDTO clinic = clinicLocationBasedService.locateNearestClinic(-29.827954,30.958925);
		Assert.assertNotNull(clinic);
		Assert.assertTrue(clinic.getName().contains("Clare Estate Clinic")); 
	}

	@Test
	public void testSandton() throws Exception {
		ClinicDTO clinic = clinicLocationBasedService.locateNearestClinic(-26.107046,28.056679);
		Assert.assertNotNull(clinic);
		Assert.assertTrue(clinic.getName().contains("Sandown"));
	}
	
	@Test
	public void testLadybrand() throws Exception {
		ClinicDTO clinic = clinicLocationBasedService.locateNearestClinic(-29.194504,27.46067);
		Assert.assertNotNull(clinic);
		Assert.assertTrue(clinic.getName().contains("Ladybrand"));
	}
	
	@Test
	public void testSanddrif() throws Exception {
		ClinicDTO clinic = clinicLocationBasedService.locateNearestClinic(0.0,0.0);
		Assert.assertNotNull(clinic);
		Assert.assertTrue(clinic.getName().contains("Sanddrift"));
		Assert.assertEquals("158 Reier Avenue, Sanddrift, South Africa", clinic.getAddress());
		
		// this time it should pull from the cache
		clinic = clinicLocationBasedService.locateNearestClinic(0.0,0.0);
		Assert.assertEquals("158 Reier Avenue, Sanddrift, South Africa", clinic.getAddress());
	}
	
	@Test
	public void testIsInvalidAddressUnknown() throws Exception {
		ClinicLocationBasedServiceImpl implService = new ClinicLocationBasedServiceImpl();
		boolean invalidAddress1 = implService.isInvalidAddress("unknown");
		Assert.assertTrue("unknown",invalidAddress1);
		boolean invalidAddress2 = implService.isInvalidAddress("Unknown");
		Assert.assertTrue("Unknown",invalidAddress2);
		boolean invalidAddress3 = implService.isInvalidAddress("UNKNOWN");
		Assert.assertTrue("Unknown",invalidAddress3);
	}

	@Test
	public void testIsInvalidAddressEmpty() throws Exception {
		ClinicLocationBasedServiceImpl implService = new ClinicLocationBasedServiceImpl();
		boolean invalidAddress1 = implService.isInvalidAddress("");
		Assert.assertTrue("unknown",invalidAddress1);
		boolean invalidAddress2 = implService.isInvalidAddress(null);
		Assert.assertTrue("null",invalidAddress2);
	}
	
	@Test
	public void testIsInvalidAddressNumber() throws Exception {
		ClinicLocationBasedServiceImpl implService = new ClinicLocationBasedServiceImpl();
		boolean invalidAddress1 = implService.isInvalidAddress("1");
		Assert.assertTrue("1",invalidAddress1);
		boolean invalidAddress2 = implService.isInvalidAddress("021636372");
		Assert.assertTrue("021636372",invalidAddress2);
	}

	@Test
	public void testNoForProfitFacility() throws Exception {
	    ClinicDTO clinic = clinicLocationBasedService.locateNearestClinic(-33.986793,18.4780121);
        Assert.assertNotNull(clinic);
        System.out.println(clinic.getName());
        Assert.assertFalse(clinic.getName().contains("Kenilworth Medicross Private Clinic"));
	}
}
