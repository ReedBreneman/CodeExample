/**
 * The jUnit tests for DependencyEvaluator.
 * 
 * @author reed
 */
package rbb.mdexample;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class DependencyEvaluatorTest {

	String[] mInputData = { "A B" };
	
	
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
	 * Validate that the output string matches what we expect with the default 'A B' input data.
	 * @param output The output string to validate.
	 */
	private void evaluateOutputWithInputData(String output) {
		System.out.println(output);
//		assertTrue("\'Example:\' string not found in output", output.contains("Example:"));
		assertTrue("\'A [B]\' string not found in output", output.contains("A [B]"));
	}

}
