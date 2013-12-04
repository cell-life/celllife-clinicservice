package org.celllife.clinicservice.domain.country;

import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import javax.persistence.QueryHint;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-15
 * Time: 14h15
 */
@Loggable(LogLevel.DEBUG)
@RestResource(path = "countries")
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Country findByExternalId(String externalId);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Country findOneByName(String countryName);
}
