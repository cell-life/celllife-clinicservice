package org.celllife.clinicservice.integration.aat;

import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.util.*;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 14h38
 */
@Service
public class AatClinicServiceImpl implements AatClinicService, InitializingBean {

    Map<String, String> clinicShortNameToCodeMap = new HashMap<>();

    @Autowired
    private Smooks aatSmooks;

    @Override
    public String findClinicCodeByShortName(String shortName) {
        return clinicShortNameToCodeMap.get(shortName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        InputStream inputStream = getClass().getResourceAsStream("/clinics.csv");

        //Get the data to filter
        StreamSource source = new StreamSource(inputStream);

        //Create the JavaResult, which will contain the filter result after filtering
        JavaResult result = new JavaResult();

        //Filter the data from the source, putting the result into the JavaResult
        this.aatSmooks.filterSource(source, result);

        Clinics clinics = result.getBean(Clinics.class);
        for (Clinic clinic : clinics.getClinics()) {
            clinicShortNameToCodeMap.put(clinic.getShortName(), clinic.getCode());
        }
    }
}
