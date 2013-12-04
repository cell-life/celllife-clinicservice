package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.domain.clinic.ClinicRepository;
import org.celllife.clinicservice.test.TestConfigurationNoAsync;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 14h15
 */

@Ignore
@ContextConfiguration(classes = TestConfigurationNoAsync.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DhisClinicApplicationServiceIntegrationTest {

    @Autowired
    private DhisClinicApplicationService dhisClinicApplicationService;

    @Autowired
    private ClinicRepository clinicRepository;

    @Test
    public void testSynchroniseClinic() throws Exception {

        dhisClinicApplicationService.synchroniseClinic("ZA5bOrg55Vg");

        for (Clinic clinic : clinicRepository.findAll()) {
            System.out.println(clinic);
        }
    }
    
    @Test
    public void testSynchroniseDemoClinic() throws Exception {

        dhisClinicApplicationService.synchroniseClinic("0001");
        Assert.assertNotNull(clinicRepository.findOneByCode("0001"));
    }
}
