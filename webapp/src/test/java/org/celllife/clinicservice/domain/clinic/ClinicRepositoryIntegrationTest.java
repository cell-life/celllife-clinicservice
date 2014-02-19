package org.celllife.clinicservice.domain.clinic;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 21h45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:/META-INF/spring/spring-cache.xml",
        "classpath:/META-INF/spring/spring-config.xml",
        "classpath:/META-INF/spring/spring-domain.xml",
        "classpath:/META-INF/spring/spring-jdbc.xml",
        "classpath:/META-INF/spring/spring-orm.xml",
        "classpath:/META-INF/spring/spring-tx.xml",
        "classpath:/META-INF/spring-data/spring-data-jpa.xml"
})
public class ClinicRepositoryIntegrationTest {

    @Autowired
    private ClinicRepository clinicRepository;

    @Test
    public void testFindOneByCode() throws Exception {
    	Clinic clinic = clinicRepository.findOneByCode("0001");
    	Assert.assertEquals("Demo Clinic 2", clinic.getName());
    }
    
    @Test
    @Ignore("No way to actually run this test reliably")
    public void testFindLatestCode() throws Exception {
    	String lastCode = clinicRepository.getLastCode();
    	Assert.assertEquals("7140", lastCode);
    }
}
