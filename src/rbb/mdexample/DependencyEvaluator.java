/**
 * This is the main dependency evaluator controller.  
 * 
 * It gets the list of strings that represent the single parent -> dependency relationship.
 * From each row of parent -> child dependencies, it will create a node in a dependency set.
 * 
 * void   setInputData() can be used to set the input data.
 * void   calculate() is used to generate the dependency lists.
 * String generateOutput() will generate the output string, calling calculate() first if necessary.
 * 
 * TODO - consider adding a clear() to all evaluating multiple input data sets.
 * 
 * @author reed
 */
package rbb.mdexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class DependencyEvaluator {

	// TreeMap is expensive for processing time, but makes a ordering the output data easy.  
	// For larger data sets, use a HashSet() and Collections.sort just before output. 
	private Map<String, DependencyNode> mNodes = new TreeMap<>();

	private boolean mCalculated = false;

	private static final String FIELD_DELIMITER = " ";
	
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
    	if (inputData.length > 0) {
    		for (int i = 0; i < inputData.length; i++) {
    			processInputLine(inputData[i]);
    		}
    	}
    }
    
    /**
     * Process a single line of input data into a node.  Do not evaluate the children immediately,
     * Wait until all input data is added and the calculate method is called.  
     * Once the data is parsed, create a node & put it into the collection of nodes;
     * 
     * A single line of input is Key Dependency [Dependency...]
     * @param line
     */
	private void processInputLine(String line) {
		LOGGER.finer(line);
		
		if (null != line) {		
			String[] elements = line.split(FIELD_DELIMITER);
			
			if (elements.length > 0) {
				String key = elements[0];

				// Wrap Arrays.asList in ArrayList to allow for modifications.
				ArrayList<String> children = new ArrayList<>(Arrays.asList(elements));
				children.remove(key); //Since doing a bulk add of all entries on the line, remove the key from the list of children.
				
				LOGGER.finer("Getting descendents of " + key + " - " + children);
				DependencyNode node = new DependencyNode(key, children);
				mNodes.put(key, node);
			}
		}
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
		 * Generate a line for each of the parent nodes listing all of the expanded descendants.
		 */
		StringBuilder output = new StringBuilder();
		for (DependencyNode node : mNodes.values()) {
			output.append(node.getKey());
			output.append(" ");
			output.append(node.getChildren().toString()).append("\n");
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
