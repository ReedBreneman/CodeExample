/**
 * This is the main method. 
 * It handles the input line parameters and reading the data into strings.
 * The DependencyEvaluator does most of the work after the data is read in.
 * 
 * I use the global LOGGER throughout to log message at various levels.
 * 
 * @author reed
 */
package rbb.mdexample;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static void main(String[] args) {

		LOGGER.setLevel(Level.SEVERE);
		
		String[] inputData = {
				   "A B C",
				   "B C E",
				   "C G",
				   "D A F",
				   "E F",
				   "F H"
				};
		
		DependencyEvaluator de = new DependencyEvaluator(inputData);
		String result = de.generateOutput();
		System.out.println("Result: \n" + result);
	}

}
