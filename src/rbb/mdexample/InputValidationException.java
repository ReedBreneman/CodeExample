/**
 * An Exception wrapper that an input validation exception can be reported.
 * The wrapper is created to allow checking for this specific type of error.
 * 
 * @author reed
 *
 */
package rbb.mdexample;

@SuppressWarnings("serial")
public class InputValidationException extends Exception {
	
	InputValidationException() {
		super();
	}
		
	InputValidationException(String message) {
		super(message);
	}

}
