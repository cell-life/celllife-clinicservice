package org.celllife.clinicservice.domain.clinic;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: Kevin W. Sewell
 * Date: 2013-03-18
 * Time: 20h09
 */
@Embeddable
public final class Group implements Serializable {

	private static final long serialVersionUID = 1903650577807942699L;

	@Basic
    private String name;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
