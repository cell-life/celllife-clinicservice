package org.celllife.clinicservice.application.dhis;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.district.DistrictRepository;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;
import org.celllife.clinicservice.domain.subdistrict.SubDistrictRepository;
import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.celllife.clinicservice.integration.dhis.DhisSubDistrictService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h15
 */
@Service
public class DhisSubDistrictApplicationServiceImpl implements DhisSubDistrictApplicationService {

    @Autowired
    private DhisSubDistrictService dhisSubDistrictService;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SubDistrictRepository subDistrictRepository;

    @Autowired
    private Mapper mapper;

    @Loggable(value = LogLevel.INFO, exception = LogLevel.ERROR)
    public void synchroniseSubDistrict(String externalId) {
        SubDistrict dhisSubDistrict;
        dhisSubDistrict = dhisSubDistrictService.findOne(externalId);

        District dhisDistrict = dhisSubDistrict.getDistrict();
        District savedDistrict = saveDistrict(dhisDistrict);
        dhisSubDistrict.setDistrict(savedDistrict);

        SubDistrict existingSubDistrict = subDistrictRepository.findByExternalId(externalId);
        if (existingSubDistrict == null) {
            subDistrictRepository.save(dhisSubDistrict);
        } else {
            mapper.map(dhisSubDistrict, existingSubDistrict);
            subDistrictRepository.save(existingSubDistrict);
        }
    }

    private District saveDistrict(District district) {

        if (district == null || district.getExternalId() == null) {
            return null;
        }

        District savedDistrict = districtRepository.findByExternalId(district.getExternalId());

        if (savedDistrict != null) {
            return savedDistrict;
        }

        return districtRepository.save(district);
    }
}
