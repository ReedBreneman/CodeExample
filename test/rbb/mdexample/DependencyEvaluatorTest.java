package rbb.mdexample;

import static org.junit.Assert.*;

import org.junit.Test;

public class DependencyEvaluatorTest {

	/**
	 * Test the constructor with no data passed in.
	 */
	@Test
	public void testEmptyConstructor() {
		DependencyEvaluator de = new DependencyEvaluator();
		String output = de.generateOutput();
		assertEquals( "Output does not match expected output", "Example: \n", output);
	}
	
	/** 
	 * Test the constructor passing in a sample data String;
	 */
	@Test
	public void testStringArrayConstructor() {
		String[] inputData = { "A B" };
		
		DependencyEvaluator de = new DependencyEvaluator(inputData);
		String output = de.generateOutput();
		assertTrue("\'Example:\' string not found in output", output.contains("Example:"));
		assertTrue("\'A B\' string not found in output", output.contains("A B"));
	}
	
	/**
	 * Test the empty constructor and setInputData() method.
	 */
	@Test
	public void testSetInputData() {
		String[] inputData = { "A B" };
		
		DependencyEvaluator de = new DependencyEvaluator();
		de.setInputData(inputData);
		String output = de.generateOutput();
		assertTrue("\'Example:\' string not found in output", output.contains("Example:"));
		assertTrue("\'A B\' string not found in output", output.contains("A B"));
	}	

}
