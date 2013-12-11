package org.celllife.clinicservice.integration.dhis;

import junit.framework.Assert;

import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ClinicServiceIntegrationTest {

	@Autowired
	private DhisClinicService clinicService;
	
	@Test
	@Ignore("An integration test, so shouldn't run with unit tests")
	public void testGetOutreachTeam() throws Exception {
		Clinic clinic = clinicService.findOne("qwIwS3azf5N");
		Assert.assertNull(clinic);
	}
}
