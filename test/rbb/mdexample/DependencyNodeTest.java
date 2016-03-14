/**
 * The jUnit tests for DependencyNode.
 * 
 * @author reed
 */
package rbb.mdexample;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DependencyNodeTest {

	final private static String M_KEY = "A";
	final private static String CHILD1 = "B";
	final private static  String CHILD2 = "C";
	private ArrayList<String> children;
	
	@Before
	public void init() {
		children = new ArrayList<>();
		children.add(CHILD1);
		children.add(CHILD2);		
	}
	
	@Test
	public void testConstructor() {
		Set<String> childrenResult = new HashSet<>();
		childrenResult.add(CHILD1);
		childrenResult.add(CHILD2);
		Set<String> descendentResult = new HashSet<>();  //Will be empty unless calculated.
		
		DependencyNode dn = new DependencyNode(M_KEY, children);
		
		assertEquals("DependencyNode key not as expected", M_KEY, dn.getKey());
		assertEquals("DependencyNode children not as expected", childrenResult, dn.getChildren());
		assertEquals("DependencyNode allDescendents not as expected", descendentResult, dn.getAllDescendants());
		assertFalse("DependencyNode processing not as expected", dn.isProcessing());
		assertFalse("DependencyNode childrenProcessing not as expected", dn.isDescendantProcessing());
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
	 * Test the setChildrenProcessing() & areChildrenProcessing() methods
	 */
	@Test
	public void testGetSetChildrenProcessing() {
		DependencyNode dn = new DependencyNode(M_KEY, children);

		// Initial isProcessing tested in testConstructor
		dn.setDescendantProcessing(true);
		assertTrue("DependencyNode processing not as expected", dn.isDescendantProcessing());
		dn.setDescendantProcessing(false);
		assertFalse("DependencyNode processing not as expected", dn.isDescendantProcessing());
	}

}