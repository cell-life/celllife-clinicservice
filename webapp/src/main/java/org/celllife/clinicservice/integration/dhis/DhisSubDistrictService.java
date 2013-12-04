package org.celllife.clinicservice.integration.dhis;

import org.celllife.clinicservice.domain.subdistrict.SubDistrict;

import java.util.List;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 11h47
 */
public interface DhisSubDistrictService {

    List<String> findAllExternalIds();

    SubDistrict findOne(String id);

}
