package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.district.District;

import java.util.List;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 11h47
 */
public interface DhisDistrictService {

    List<String> findAllExternalIds();

    District findOne(String id);

}
