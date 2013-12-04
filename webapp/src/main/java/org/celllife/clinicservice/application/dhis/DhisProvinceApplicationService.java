package org.celllife.clinicservice.application.dhis;

import org.springframework.scheduling.annotation.Async;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 12h27
 */
public interface DhisProvinceApplicationService {

    @Async("dhisTaskExecutor")
    void synchroniseProvince(String externalId) ;

}
