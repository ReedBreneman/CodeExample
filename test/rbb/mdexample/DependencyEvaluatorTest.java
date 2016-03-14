/**
 * The jUnit tests for DependencyEvaluator.
 * 
 * @author reed
 */
package rbb.mdexample;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class DependencyEvaluatorTest {

	String[] mInputData = { "A B" };

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/** 
	 * Setup the Logger so that logging level is as expected during junit Tests.
	 */
	@BeforeClass
	public static void init() {
		LOGGER.setLevel(Level.SEVERE);
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
		String[] invalidData = { "", "W" };
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
		System.out.println(output);
		assertTrue("\'A  B\' string not found in output", output.contains("A  B"));
	}
	
}
