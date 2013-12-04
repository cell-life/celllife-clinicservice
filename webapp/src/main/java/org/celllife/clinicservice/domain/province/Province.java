package org.celllife.clinicservice.domain.province;

import org.celllife.clinicservice.domain.country.Country;

import javax.persistence.*;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-15
 * Time: 16h09
 */
@Entity
@Cacheable
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String externalId;

    private String name;

    private String shortName;

    @Lob
    private String coordinates;

    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    public Province() {
    }

    public Province(String externalId, String name) {

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
