package org.celllife.clinicservice.domain.exception;

/**
 * Error that occurs when an invalid Coordinate is found
 */
public class InvalidCoordinateException extends RuntimeException {

	private static final long serialVersionUID = -7737751756645711819L;

	public InvalidCoordinateException() {
		super();
	}

	public InvalidCoordinateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCoordinateException(String message) {
		super(message);
	}

	public InvalidCoordinateException(Throwable cause) {
		super(cause);
	}
}
