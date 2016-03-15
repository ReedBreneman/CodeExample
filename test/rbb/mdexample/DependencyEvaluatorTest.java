/**
 * The jUnit tests for DependencyEvaluator.
 * 
 * @author reed
 */
package rbb.mdexample;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

@FixMethodOrder(MethodSorters.JVM)
public class DependencyEvaluatorTest {

	@Mock DependencyNode mockedDependencyNode;

	static ArrayList<String> mInputData = null;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/** 
	 * Run before the class is used.
	 * 
	 * Setup the Logger so that logging level is as expected during junit Tests.
	 */
	@BeforeClass
	public static void init() {
		LOGGER.setLevel(Level.SEVERE);
		mInputData = new ArrayList<>(Arrays.asList("A B"));
	}

	/**
	 * Run before each test case - initialize/re-initialize the mock objecs;
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test the constructor with no data passed in.
	 */
	@Test
	public void testEmptyConstructor() {
		DependencyEvaluator de = new DependencyEvaluator();
		String output = de.generateOutput();
		// With no input data, there will be no output
		assertEquals( "Output does not match expected output", "", output);
	}
	
	
	/** 
	 * Test the constructor passing in a sample data String;
	 */
	@Test
	public void testStringArrayConstructor() {
		
		DependencyEvaluator de = new DependencyEvaluator(mInputData);
		evaluateOutputWithInputData(de.generateOutput());
	}
	
	
	/**
	 * Test the empty constructor and setInputData() method.
	 */
	@Test
	public void testSetInputData() {
		DependencyEvaluator de = new DependencyEvaluator();
		de.setInputData(mInputData);
		evaluateOutputWithInputData(de.generateOutput());
	}	
	
	/**
	 * Test the empty constructor and setInputData() with invalid data .
	 */
	@Test
	public void testInvalidInputData() {
		ArrayList<String> invalidData = new ArrayList<>(Arrays.asList("", "W"));
		
		DependencyEvaluator de = new DependencyEvaluator();
		de.setInputData(invalidData);
		String output = de.generateOutput();
		assertEquals("Empty String is expected output for invalid data", "", output);
	}	
	
	/** 
	 * Validate that the output string matches what we expect with the default 'A B' input data.
	 * @param output The output string to validate.
	 */
	private void evaluateOutputWithInputData(String output) {
		assertTrue("\'A  B\' string not found in output", output.contains("A  B"));
	}	
	
	/**
	 * Test processNodes with returned entries on processing
	 */
	@Test 
	public void testProcessNodesWithReprocessing() {
		when(mockedDependencyNode.determineDescendents(anyMap())).thenReturn(false);
		
		String key = "A";
		Map<String, DependencyNode> nodeMap = new TreeMap<>();
		nodeMap.put(key, mockedDependencyNode);

		DependencyEvaluator de = new DependencyEvaluator(mInputData);
		Collection<DependencyNode> result = de.processNodes(nodeMap.values());
		
		assertEquals("Returned list size not as expected", 1, result.size());
		assertTrue("Retruned list does not contain expected DependencyNode", result.contains(mockedDependencyNode));
	}
	
	/**
	 * Test processNodes with No returns on processing
	 */
	@Test 
	public void testProcessNodesWithNoReprocessing() {
		when(mockedDependencyNode.determineDescendents(anyMap())).thenReturn(true);

		String key = "A";
		Map<String, DependencyNode> nodeMap = new TreeMap<>();
		nodeMap.put(key, mockedDependencyNode);

		DependencyEvaluator de = new DependencyEvaluator(mInputData);
		Collection<DependencyNode> result = de.processNodes(nodeMap.values());
		
		assertEquals("Returned list size not as expected", 0, result.size());
	}	
}
