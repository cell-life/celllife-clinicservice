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
}
