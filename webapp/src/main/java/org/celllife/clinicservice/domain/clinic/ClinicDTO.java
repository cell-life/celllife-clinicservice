package org.celllife.clinicservice.domain.clinic;

import java.io.Serializable;

import org.celllife.clinicservice.domain.district.District;
import org.celllife.clinicservice.domain.province.Province;
import org.celllife.clinicservice.domain.subdistrict.SubDistrict;

/**
 * Clinic Data Transfer Object - used to store clinic information that can be shared with clients (REST, UI, etc)
 */ 
public final class ClinicDTO implements Serializable {

	private static final long serialVersionUID = 8067394485735014091L;

	private Long id;

    private String name;

    private String code;

    private String shortName;
    
    private String phoneNumber;
    
    private String address;

    private String coordinates;

    private String subDistrictName;
    
    private String districtName;
    
    private String provinceName;
    
    public ClinicDTO() {
    }
    
    public ClinicDTO(Clinic clinic) {
    	this.id = clinic.getId();
    	this.code = clinic.getCode();
    	this.name = clinic.getName();
    	this.shortName = clinic.getShortName();
    	this.address = clinic.getAddress();
    	this.phoneNumber = clinic.getPhoneNumber();
    	this.coordinates = clinic.getCoordinates();
    	SubDistrict sd = clinic.getSubDistrict();
    	this.subDistrictName = sd.getShortName();
    	District d = sd.getDistrict();
    	this.districtName = d.getShortName();
    	Province p = d.getProvince();
    	this.provinceName = p.getShortName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

	public String getSubDistrictName() {
		return subDistrictName;
	}

	public void setSubDistrictName(String subDistrictName) {
		this.subDistrictName = subDistrictName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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
		ClinicDTO other = (ClinicDTO) obj;
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
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }

}
