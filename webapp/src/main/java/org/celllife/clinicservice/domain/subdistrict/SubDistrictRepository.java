package org.celllife.clinicservice.domain.subdistrict;

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
@RestResource(path = "subDistricts")
public interface SubDistrictRepository extends PagingAndSortingRepository<SubDistrict, Long> {

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SubDistrict findByExternalId(String externalId);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SubDistrict findOneByName(String name);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Iterable<SubDistrict> findByDistrictName(String districtName);
}
