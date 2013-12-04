package org.celllife.clinicservice.application.dhis;

import org.springframework.scheduling.annotation.Async;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 10h28
 */
public interface DhisCountryApplicationService {

    @Async("dhisTaskExecutor")
    void synchroniseCountry(String externalId);

}
