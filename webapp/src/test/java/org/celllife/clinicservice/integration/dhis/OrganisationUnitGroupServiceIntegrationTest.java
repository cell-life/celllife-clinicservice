package org.celllife.clinicservice.integration.dhis;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-17
 * Time: 18h03
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ResourceServiceIntegrationTest.Config.class)
public class OrganisationUnitGroupServiceIntegrationTest {

    @Autowired
    private OrganisationUnitGroupService organisationUnitGroupService;

    @Test
    public void testFindLinkByName() throws Exception {

        String organisationUnitGroupsLink = organisationUnitGroupService.findLinkByName("Province");

        System.out.println(organisationUnitGroupsLink);

    }

    @Configuration
    @ImportResource({
            "classpath:/META-INF/spring/spring-config.xml",
            "classpath:/META-INF/spring/spring-json.xml"
    })
    @ComponentScan("org.celllife.clinicservice.integration.dhis")
    public static class Config {

    }
}
