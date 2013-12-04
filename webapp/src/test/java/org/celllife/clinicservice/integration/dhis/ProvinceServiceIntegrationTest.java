package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.province.Province;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-17
 * Time: 18h03
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ResourceServiceIntegrationTest.Config.class)
public class ProvinceServiceIntegrationTest {

    @Autowired
    private DhisProvinceService dhisProvinceService;

    @Test
    public void testFindLinkByName() throws Exception {

        List<String> provinceExternalIds = dhisProvinceService.findAllExternalIds();

        for (String provinceExternalId : provinceExternalIds) {

            Province province = dhisProvinceService.findOne(provinceExternalId);

            System.out.println(province.getExternalId() + ":" + province.getName());
        }

    }

    @Test
    public void testFindOne() throws Exception {

        Province province = dhisProvinceService.findOne("S9Ee1ePYDOS");

        System.out.println(province);

    }

    @Configuration
    @ImportResource({
            "classpath:/META-INF/spring/spring-cache.xml",
            "classpath:/META-INF/spring/spring-config.xml",
            "classpath:/META-INF/spring/spring-json.xml"
    })
    @ComponentScan("org.celllife.clinicservice.integration.dhis")
    public static class Config {

    }
}
