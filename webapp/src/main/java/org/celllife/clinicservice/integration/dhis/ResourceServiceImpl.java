package org.celllife.clinicservice.integration.dhis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-17
 * Time: 17h32
 */
@Service("resourceService")
public final class ResourceServiceImpl implements ResourceService {

    public static final String RESOURCES = "resources";
    @Autowired
    private DhisClient dhisClient;

    @Value("${dhis.base_url}")
    private String dhisBaseUrl;

    private static final String RESOURCES_PATH = "/api/resources";

    @Cacheable("resourceLinkByName")
    public String findLinkByName(String name) {

        Map<String, ?> resourcesMap = dhisClient.getMap(dhisBaseUrl + RESOURCES_PATH);

        @SuppressWarnings("unchecked")
        List<Map<String, ?>> resources = (List<Map<String, ?>>) resourcesMap.get(RESOURCES);

        for (Map<String, ?> resource : resources) {
            if (resource.get("name").equals(name)) {
                return (String) resource.get("href");
            }
        }

        return null;
    }
}
