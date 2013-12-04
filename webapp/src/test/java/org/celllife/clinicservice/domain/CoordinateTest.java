package org.celllife.clinicservice.domain;

import junit.framework.Assert;

import org.celllife.clinicservice.domain.exception.InvalidCoordinateException;
import org.junit.Test;

public class CoordinateTest {

	@Test
	public void testCreateCoordinateFromString() throws Exception {
		Coordinate c = new Coordinate("[26.7103,-30.6893]");
		Assert.assertEquals(new Double(26.7103), c.getYCoordinate());
		Assert.assertEquals(new Double(-30.6893), c.getXCoordinate());
	}
	
	@Test(expected = InvalidCoordinateException.class)
	public void testInvalidNumber() throws Exception {
		new Coordinate("[XYZ,-30.6893]");
	}
	
	@Test(expected = InvalidCoordinateException.class)
	public void testInvalidString() throws Exception {
		new Coordinate("blah blah");
	}
	
	@Test(expected = InvalidCoordinateException.class)
	public void testInvalidStringTooManyParameters() throws Exception {
		new Coordinate("[26.7103,-30.6893,-30.6893]");
	}

	@Test(expected = InvalidCoordinateException.class)
	public void testInvalidStringEmpty() throws Exception {
		new Coordinate(" ");
	}
	
	@Test(expected = InvalidCoordinateException.class)
	public void testInvalidStringNull() throws Exception {
		new Coordinate(null);
	}
	
	@Test
	public void testStringWithWhitespace() throws Exception {
		Coordinate c = new Coordinate(" [ 26.7103, -30.6893 ] ");
		Assert.assertEquals(new Double(26.7103), c.getYCoordinate());
		Assert.assertEquals(new Double(-30.6893), c.getXCoordinate());
	}
}
