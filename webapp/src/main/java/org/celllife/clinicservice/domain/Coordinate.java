package org.celllife.clinicservice.domain;

import java.io.Serializable;

import org.celllife.clinicservice.domain.exception.InvalidCoordinateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinate implements Serializable {

	private static final long serialVersionUID = -3092636278034099973L;

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(Coordinate.class);

	Double xCoordinate;
	Double yCoordinate;
	
	public Coordinate() {
		
	}
	
	public Coordinate(Double xCoordinate, Double yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}

	/**
	 * Creates a Coordinate object given a String in the DHIS format: [26.7103,-30.6893]
	 * @param coordinates Will ignore any invalid string
	 */
	public Coordinate(String coordinates) throws InvalidCoordinateException {
		if (coordinates != null && !coordinates.trim().isEmpty()) {
			String[] c = coordinates.trim().split("[\\[,\\]]");
			if (c != null && c.length == 3) {
				try {
					this.yCoordinate = Double.parseDouble(c[1].trim());
					this.xCoordinate = Double.parseDouble(c[2].trim());
				} catch (NumberFormatException e) {
					throw new InvalidCoordinateException("Cannot parse coordinate string '"+coordinates+"'",e);
				}
			} else {
				throw new InvalidCoordinateException("Cannot parse coordinate string '"+coordinates+"'. It should be in the format [x,y].");
			}
		} else {
			throw new InvalidCoordinateException("Cannot parse an empty coordinate string.");
		}
	}

	@Override
	public String toString() {
		return "Coordinate [xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((xCoordinate == null) ? 0 : xCoordinate.hashCode());
		result = prime * result + ((yCoordinate == null) ? 0 : yCoordinate.hashCode());
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
		Coordinate other = (Coordinate) obj;
		if (xCoordinate == null) {
			if (other.xCoordinate != null)
				return false;
		} else if (!xCoordinate.equals(other.xCoordinate))
			return false;
		if (yCoordinate == null) {
			if (other.yCoordinate != null)
				return false;
		} else if (!yCoordinate.equals(other.yCoordinate))
			return false;
		return true;
	}

	public Double getXCoordinate() {
		return xCoordinate;
	}

	public void setXCoordinate(Double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public Double getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(Double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}
