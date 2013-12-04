package org.celllife.clinicservice.application.dhis;

import org.springframework.scheduling.annotation.Async;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 12h32
 */
public interface DhisSubDistrictApplicationService {

    @Async("dhisTaskExecutor")
    void synchroniseSubDistrict(String externalId);

}
