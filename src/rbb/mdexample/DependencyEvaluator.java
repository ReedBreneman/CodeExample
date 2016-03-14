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
 * @author reed
 * 
 * TODO - consider adding a clear() to all evaluating multiple input data sets.
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
    			try {
    				processInputLine(inputData[i]);
    			} catch (InputValidationException ive) {
    				// Perhaps throw again if desire to exit without results generated if that is desired behavior.
    				LOGGER.severe("Exception encounterered processing input on line " + (i+1) + ": " + ive.getMessage());
    			}
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
     * @throws InputValidationException 
     */
	private void processInputLine(String line) throws InputValidationException {
		LOGGER.finer(line);
		
		if (null != line) {		
			String[] elements = line.split(FIELD_DELIMITER);
			
			String key = elements[0];
			if (!key.isEmpty()) {
				// Make sure there are children in the input line
				if (elements.length > 1) {			
					// Wrap Arrays.asList in ArrayList to allow for modifications.
					ArrayList<String> children = new ArrayList<>(Arrays.asList(elements));
					//Since doing a bulk add of all entries on the line, remove the key from the list of children.
					//This also handles the case where a key is part of the children.
					children.remove(key); 
					
					LOGGER.finer("Getting descendents of " + key + " - " + children);
					DependencyNode node = mNodes.get(key);
					if (null == node) {
						node = new DependencyNode(key, children);
						mNodes.put(key, node);
					} else { // The node already exists, so add the children to the list.
						node.addChildren(children);
					}
				} else {
					throw new InputValidationException("Row with key \'" + key + "\' does not contain any dependencies");
				}
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
			for (String dependency : node.getAllDescendants()) {
				output.append(" ").append(dependency);
			}
			output.append("\n");
		}
		
		return output.toString();
	}
	
	/**
	 * Process the nodes recursively processing dependencies.
	 */
	public void calculate() {
		LOGGER.info("Calculating Dependencies");

		// Iterate thru the nodes and determine all descendants of each node. 
		// Each node will determine the descendants for its children and children's children, etc. 
		for (DependencyNode node : mNodes.values()) {
			node.determineDescendents(mNodes);
		}
	}
}
