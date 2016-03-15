/**
 * The jUnit tests for DependencyNode.
 * 
 * @author reed
 */
package rbb.mdexample;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DependencyNodeTest {

	final private static String M_KEY = "A";
	final private static String CHILD1 = "B";
	final private static  String CHILD2 = "C";
	private ArrayList<String> children;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	
	/** 
	 * Setup the Logger so that logging level is as expected during junit Tests.
	 */
	@BeforeClass
	public static void init() {
		LOGGER.setLevel(Level.SEVERE);
	}
	
	/**
	 * Setup the initial children list used in most of the tests.
	 */
	@Before
	public void setup() {
		children = new ArrayList<>(Arrays.asList(CHILD1, CHILD2));
	}
	
	/**
	 * Test the default Constructor and the data returned from each getter matches what is expected from the initial state.
	 */
	@Test
	public void testConstructor() {
		Set<String> childrenResult = new HashSet<>(Arrays.asList(CHILD1, CHILD2));
		Set<String> descendentResult = new HashSet<>();  //Will be empty unless calculated.
		
		DependencyNode dn = new DependencyNode(M_KEY, children);
		
		assertEquals("DependencyNode key not as expected", M_KEY, dn.getKey());
		assertEquals("DependencyNode children not as expected", childrenResult, dn.getChildren());
		assertEquals("DependencyNode allDescendents not as expected", descendentResult, dn.getAllDescendants());
		assertFalse("DependencyNode processing not as expected", dn.isProcessing());
		assertFalse("DependencyNode expanded not as expected", dn.isExpanded());
	}
	
	/**
	 * Test the setProcessing() & isProcessing() methods
	 */
	@Test
	public void testGetSetProcessing() {
		DependencyNode dn = new DependencyNode(M_KEY, children);

		// Initial isProcessing tested in testConstructor
		dn.setProcessing(true);
		assertTrue("DependencyNode processing not as expected", dn.isProcessing());
		dn.setProcessing(false);
		assertFalse("DependencyNode processing not as expected", dn.isProcessing());
	}
	
	/**
	 * Test a simple calculate with only one node & no non-child descendants
	 */
	@Test
	public void testSimpleCalculate() {
		DependencyNode dn = new DependencyNode(M_KEY, children);
		
		Map<String, DependencyNode> nodeMap = new HashMap<>();
		nodeMap.put(M_KEY, dn);

		Set<String> descendentResult = new HashSet<>(Arrays.asList("B", "C"));  //Will be empty unless calculated.
		
		dn.determineDescendents(nodeMap);
		assertTrue("Post Caldulate expanded is incorrect", dn.isExpanded());
		assertFalse("Post Calculate prcessing is incorrect", dn.isProcessing());
		assertEquals("Post Calculate all Descendents is incorrect", descendentResult, dn.getAllDescendants());
	}

	/**
	 * Test a simple calculate with only one node & no non-child descendants
	 */
	@Test
	public void testDescendantCalculate() {
		DependencyNode dn = new DependencyNode(M_KEY, children);
		
		Map<String, DependencyNode> nodeMap = new HashMap<>();
		nodeMap.put(M_KEY, dn);
		ArrayList<String> childList = new ArrayList<>(Arrays.asList("C", "D"));
		DependencyNode dn2 = new DependencyNode("B", childList);
		nodeMap.put("B", dn2);
	
		Set<String> descendentResult = new HashSet<>(Arrays.asList("B", "C", "D"));  //Will be empty unless calculated.
		
		dn.determineDescendents(nodeMap);
		assertTrue("Post Caldulate isExpanded is incorrect", dn.isExpanded());
		assertFalse("Post Calculate isProcessing is incorrect", dn.isProcessing());
		assertEquals("Post Calculate all Descendents is incorrect", descendentResult, dn.getAllDescendants());
	}	

	/**
	 * Test a simple calculate with only one node & no non-child descendants
	 */
	@Test
	public void testCircularCalculate() {
		DependencyNode dn = new DependencyNode(M_KEY, children);
		
		Map<String, DependencyNode> nodeMap = new HashMap<>();
		nodeMap.put(M_KEY, dn);
		ArrayList<String> childList = new ArrayList<>(Arrays.asList("C", "A"));
		DependencyNode dn2 = new DependencyNode("B", childList);
		nodeMap.put("B", dn2);
	
		Set<String> descendentResult = new HashSet<>(Arrays.asList("A", "B", "C"));  //Will be empty unless calculated.
		
		dn.determineDescendents(nodeMap);
		assertTrue("Post Caldulate isExpanded is incorrect", dn.isExpanded());
		assertFalse("Post Calculate isProcessing is incorrect", dn.isProcessing());
		assertEquals("Post Calculate circular Descendents is incorrect", descendentResult, dn.getAllDescendants());
	}	
}
