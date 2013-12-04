package org.celllife.clinicservice.domain.province;

import org.celllife.clinicservice.framework.logging.LogLevel;
import org.celllife.clinicservice.framework.logging.Loggable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-15
 * Time: 14h15
 */
@Loggable(LogLevel.DEBUG)
@RestResource(path = "provinces")
public interface ProvinceRepository extends PagingAndSortingRepository<Province, Long> {

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Province findByExternalId(String externalId);

    @Query("select distinct p.shortName from Province p")
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<String> findAllProvinceNames();

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Province findOneByName(String provinceName);

    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    Iterable<Province> findByCountryName(String countryName);
}

