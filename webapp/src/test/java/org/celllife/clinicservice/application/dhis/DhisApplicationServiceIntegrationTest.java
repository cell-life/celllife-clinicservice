package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.clinic.Clinic;
import org.celllife.clinicservice.domain.clinic.ClinicRepository;
import org.celllife.clinicservice.domain.country.Country;
import org.celllife.clinicservice.domain.country.CountryRepository;
import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.district.DistrictRepository;
import org.celllife.clinicservice.domain.province.Province;
import org.celllife.clinicservice.domain.province.ProvinceRepository;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;
import org.celllife.clinicservice.domain.subdistrict.SubDistrictRepository;
import org.celllife.clinicservice.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h34
 */
@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DhisApplicationServiceIntegrationTest {

    @Autowired
    private DhisApplicationService dhisApplicationService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private TaskExecutor dhisTaskExecutor;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SubDistrictRepository subDistrictRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Test
    @Ignore("Run to synchronize the database")
    public void testSynchroniseAll() throws Exception {

        dhisApplicationService.synchroniseAll();

        while (((ThreadPoolTaskExecutor) dhisTaskExecutor).getActiveCount() != 0) {
            Thread.sleep(1000L);
        }

        for (Clinic clinic : clinicRepository.findAll()) {
            System.out.println(clinic);
        }
    }

    @Test
    @Ignore
    public void testSynchroniseCountries() throws Exception {

        dhisApplicationService.synchroniseCountries();

        for (Country country : countryRepository.findAll()) {
            System.out.println(country);
        }
    }

    @Test
    @Ignore
    public void testSynchroniseProvinces() throws Exception {

        dhisApplicationService.synchroniseProvinces();

        while (((ThreadPoolTaskExecutor) dhisTaskExecutor).getActiveCount() != 0) {
            Thread.sleep(1000L);
        }

        for (Province province : provinceRepository.findAll()) {
            System.out.println(province);
        }
    }

    @Test
    @Ignore
    public void testSynchroniseDistricts() throws Exception {

        dhisApplicationService.synchroniseDistricts();

        for (District district : districtRepository.findAll()) {
            System.out.println(district);
        }
    }

    @Test
    @Ignore
    public void testSynchroniseSubDistricts() throws Exception {

        dhisApplicationService.synchroniseSubDistricts();

        for (SubDistrict subDistrict : subDistrictRepository.findAll()) {
            System.out.println(subDistrict);
        }
    }

    @Test
    @Ignore
    public void testSynchroniseClinics() throws Exception {

        dhisApplicationService.synchroniseClinics();

        for (Clinic clinic : clinicRepository.findAll()) {
            System.out.println(clinic);
        }
    }
}
