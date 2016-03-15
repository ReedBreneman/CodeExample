/**
 * This is a Dependency Node and maintains the key and list of immediate children and the fully evaluated descendants. 
 * 
 * mProcessing is used to break the recursive circular references.
 * mExpanded is set true when the node and all the nodes children have been evaluated and added to the allDependencies list
 * 
 * mKey is the parent node identifier
 * mChildren is the list of children set for this node.
 * 			 In the case where the parent exists in the input more than once, all the children are combined
 * mAllDescendents is the full expanded list of descendants.
 * 
 * @author reed
 */
package rbb.mdexample;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

public class DependencyNode {

	/**
	* Booleans to keep track of Node Processing status...
	* mProcessing is set once the node starts processing
	* mChildrenProcessing is set if it's identified that one or more descendants are processing
	* mExpanded is set when all descendants have been fully expanded.
	*/
	boolean mProcessing = false;
	boolean mExpanded = false;
	boolean mDescendantProcessing = false;
	
	// The key, children and all descendants Collections.
	private String mKey;
	private Set<String> mChildren = new HashSet<>();
	private Set<String> mAllDescendants = new TreeSet<>();

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public DependencyNode(String key, List<String> children) {
		mKey = key;
		mChildren.addAll(children);
	}
	
	/**
	 * Add the supplied children to the existing list of children for this Node.
	 * 
	 * @param children  The list of children strings to add.
	 */
	public void addChildren(List<String> children) {
		LOGGER.info("Adding children ");
		LOGGER.finer("Node " + mKey + " Children " + children);
		mChildren.addAll(children);
	}
	
	/**
	 * Returns the list of immediate children for this parent.
	 * @return
	 */
	public Set<String> getChildren() {
		return mChildren;
	}
	
	/**
	 * The processing flag is set when a node is first visited during the calculate process to allow 
	 * detection of recurrence loops. 
	 * 
	 * @return
	 */
	public boolean isProcessing() {
		return mProcessing;
	}

	/**
	 * Set the processing flag to the input value.
	 * @param processing
	 */
	public void setProcessing(boolean processing) {
		mProcessing = processing;
	}
 
	/**
	 * The processed flag will be true when all children have been fully expanded and no more processing is expected.
	 * This is controlled internally, so no setters provided.
	 * @return
	 */
	public boolean isExpanded() {
		return mExpanded;
	}

	/** 
	 * The isDescendentProcessing flag will be true when some descendant of this node is still processing it's children.
	 * @return
	 */
	public boolean isDescendantProcessing() {
		return mDescendantProcessing;
	}
	
	/** 
	 * Setter for areChildrenProcessing.
	 * @param processing
	 */
	public void setDescendantProcessing(boolean processing) {
		mDescendantProcessing = processing;
	}
	
	/**
	 * Returns the Key value for this node in the dependency tree.
	 * @return
	 */
	public String getKey() {
		return mKey;
	}
	
	/**
	 * Returns the list of all dependent nodes from this node.  This list is fully expanded.
	 * 
	 * @return
	 */
	public Set<String> getAllDescendants() {
		return mAllDescendants;
	}
	
	/** 
	 * Expand the child nodes for this node by evaluating the children against the nodeSet supplied to this node.
	 * 
	 * @param nodeSet
	 */
	protected boolean determineDescendents(Map<String, DependencyNode> nodeSet) {
		LOGGER.finer("Calculating Node " + mKey);
		
		if (!mExpanded) {
			mProcessing = true;
			mAllDescendants.addAll(mChildren);     //Add the direct children to the all descendants list.
	
			setDescendantProcessing(false);
			//Now iterate thru and add in all the grand children, etc...
			for (String child : mChildren) {
				LOGGER.finest("  Processing Node - Child: " + mKey + " - " + child);
				
				// Get the child node from the supplied nodeSet domain.
				DependencyNode childNode = nodeSet.get(child);
				if (null != childNode) {            // Make sure the child is also a parent to prevent errors.  
					if (!childNode.isExpanded()) {  // If the child is not already calculated, do the calculation.
						if (!childNode.isProcessing()) {  //If the node is already being processed, we don't need evaluate again.
							childNode.determineDescendents(nodeSet);
						} else {
							setDescendantProcessing(true);
						}
					}
					// Add all the descendants from the child to the list of this node descendants. 
					mAllDescendants.addAll(childNode.getAllDescendants());  
				}
			}
			// Don't set expanded to true until descendant processing is complete. 
			mProcessing = false;  //We are done, so set processing to false;
			mExpanded = true && !isDescendantProcessing();
		}
		return mExpanded;
	}	

	/**
	 * Return the key as the string for this node.
	 */
	@Override
	public String toString() {
		return mKey;
	}
}
