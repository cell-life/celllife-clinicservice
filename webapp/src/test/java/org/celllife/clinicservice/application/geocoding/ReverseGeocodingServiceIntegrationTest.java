package org.celllife.clinicservice.application.geocoding;

import org.celllife.clinicservice.domain.Coordinate;
import org.celllife.clinicservice.test.TestConfigurationNoAsync;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = TestConfigurationNoAsync.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ReverseGeocodingServiceIntegrationTest {

	@Autowired
	ReverseGeocodingService geocodingService;
	
	@Test
	public void test() throws Exception {
		String address = geocodingService.getAddressFromCoordinates(new Coordinate(-33.933782,18.417606));
		Assert.assertEquals("29 Mill Street, Cape Town 8001, South Africa",address);
	}
}
