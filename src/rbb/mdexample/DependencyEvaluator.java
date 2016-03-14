/**
 * This is the main dependency evaluator controller.  
 * 
 * It gets the list of strings that represent the single parent -> dependency relationship.
 * From each row of parent -> child dependencies, it will create a node in a dependency set.
 * 
 * String generateOutput() will generate the output string;
 * 
 * @author reed
 */
package rbb.mdexample;

import java.util.logging.Logger;

public class DependencyEvaluator {

	private String[] mInputData = null;
	
	private boolean mCalculated = false;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Default Constructor
	 */
	DependencyEvaluator() {
		// Do nothing - empty constructor.
	}
	
	/**
	 * Construct the DependencyEvaluator and initialize the data.
	 * 
	 * @param inputData  The Dependency data to evaluate in the form of
	 *                   'Key dependency [dependency dependency]'
	 */
    DependencyEvaluator(String[] inputData) {
    	setInputData(inputData);
    }
	
    /** 
     * Set the input data for the DepenencyEvaluator
     * 
     * @param inputData
     */
    public void setInputData(String[] inputData) {
    	LOGGER.info("Processing input");
    	mInputData = inputData;
    }
 
	
	/**
	 * Generate the output from the dependency set.  
	 */
	public String generateOutput() {
		LOGGER.info("Generating Output");
		// If we haven't calculated, do the calculation.  
		if (!mCalculated) {
			calculate();
		}
		
		/** 
		 * Echo the input into the output string;
		 */
		StringBuffer output = new StringBuffer("Example: \n");
		if (null != mInputData) {
			for (int i = 0; i<mInputData.length; i++) {
				output.append(mInputData[i]).append("\n");
			}
		}
		
		return output.toString();
	}
	
	/**
	 * Process the nodes recursively processing dependencies.
	 */
	public void calculate() {
		LOGGER.info("Calculating Dependencies");
		//TODO - add calculation code
		mCalculated = true;
	}
}
