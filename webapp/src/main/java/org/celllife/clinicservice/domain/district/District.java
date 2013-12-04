package org.celllife.clinicservice.domain.district;

import org.celllife.clinicservice.domain.province.Province;

import javax.persistence.*;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-15
 * Time: 16h09
 */
@Entity
@Cacheable
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String externalId;

    private String name;

    private String shortName;

    @Lob
    private String coordinates;

    @ManyToOne(fetch = FetchType.EAGER)
    private Province province;

    public District() {
    }

    public District(String externalId, String name) {

        this.externalId = externalId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", province=" + province +
                '}';
    }
}
