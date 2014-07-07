package org.celllife.clinicservice.application.lbs;

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
}
