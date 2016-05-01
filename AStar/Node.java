package AStar;

/**
 * This is the class for representing a single search node.  Such a
 * node consists of a <tt>State</tt> representing the actual
 * configuration of the puzzle, as well as the depth of the search
 * node from the initial state, and the parent node of the current
 * node in the search tree, i.e., the node from which this node was
 * expanded.  Thus, the path from the initial node to this node can be
 * computed by tracing backward using the parent links.  (The parent
 * of the initial node is set to <tt>null</tt>.)  The total distance
 * from the initial node is equal to the depth of this node.
 */
public class Node {

    private State state;
    private int depth;
    private Node parent;

    /**
     * The main constructor for constructing a search node.  You
     * probably will never need to use this constructor directly,
     * since ordinarily, new nodes will be gotten from the
     * <tt>expand</tt> method.
     *
     *   @param state the state that resides at this node
     *   @param depth the search depth of this node
     *   @param parent the parent of this node
     */
    public Node(State state,
		int depth,
		Node parent) {
	this.state = state;
	this.depth = depth;
	this.parent = parent;
    }

    /** Returns the state associated with this node. */
    public State getState() {
	return state;
    }

    /** Returns this node's parent node. */
    public Node getParent() {
	return parent;
    }

    /** Returns the depth of this node. */
    public int getDepth() {
	return depth;
    }

    /**
     * Expands this node, in other words, computes all of the nodes
     * immediately reachable from this node according to the rules of
     * the puzzle and returns them as an array of nodes.
     */
    public Node[] expand() {
	State[] new_states = state.expand();
	Node[] new_nodes = new Node[new_states.length];

	for (int i = 0; i < new_states.length; i++)
	    new_nodes[i] = new Node(new_states[i], depth+1, this);

	return new_nodes;
    }

}
