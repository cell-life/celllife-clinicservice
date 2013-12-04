package org.celllife.clinicservice.domain.clinic;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.celllife.clinicservice.domain.subdistrict.SubDistrict;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-15
 * Time: 16h04
 */
@Entity
@Cacheable
public final class Clinic implements Serializable {

	private static final long serialVersionUID = -5814051904749589348L;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String externalId;

    private String name;

    private String code;

    private String shortName;
    
    private String phoneNumber;
    
    private String address;

    @Lob
    private String coordinates;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubDistrict subDistrict;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Group> groups;

    public Clinic() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public SubDistrict getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(SubDistrict subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(String name) {

        if (this.groups == null) {
            this.groups = new HashSet<>();
        }

        this.groups.add(new Group(name));
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clinic other = (Clinic) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }

}
