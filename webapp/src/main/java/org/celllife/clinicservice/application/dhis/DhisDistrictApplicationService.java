package org.celllife.clinicservice.application.dhis;

import org.springframework.scheduling.annotation.Async;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 12h30
 */
public interface DhisDistrictApplicationService {

    @Async("dhisTaskExecutor")
    void synchroniseDistrict(String externalId);

}
