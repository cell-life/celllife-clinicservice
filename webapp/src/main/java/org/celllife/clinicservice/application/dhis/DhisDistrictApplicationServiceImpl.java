package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.district.DistrictRepository;
import org.celllife.clinicservice.domain.province.Province;
import org.celllife.clinicservice.domain.province.ProvinceRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisDistrictService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h15
 */
@Service
public class DhisDistrictApplicationServiceImpl implements DhisDistrictApplicationService {

    @Autowired
    private DhisDistrictService dhisDistrictService;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseDistrict(String externalId) {
        District dhisDistrict;
        dhisDistrict = dhisDistrictService.findOne(externalId);

        Province dhisProvince = dhisDistrict.getProvince();
        Province savedProvince = saveProvince(dhisProvince);
        dhisDistrict.setProvince(savedProvince);

        District existingDistrict = districtRepository.findByExternalId(externalId);
        if (existingDistrict == null) {
            districtRepository.save(dhisDistrict);
        } else {
            mapper.map(dhisDistrict, existingDistrict);
            districtRepository.save(existingDistrict);
        }
    }

    private Province saveProvince(Province province) {

        if (province == null || province.getExternalId() == null) {
            return null;
        }

        Province savedProvince = provinceRepository.findByExternalId(province.getExternalId());

        if (savedProvince != null) {
            return savedProvince;
        }

        return provinceRepository.save(province);
    }
}
