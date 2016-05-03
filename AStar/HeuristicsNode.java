package AStar;

public class HeuristicsNode extends Node {
	
	private int f;
	private int g;
	private int heuristic;
	
	/**
	 * A wrapper for the node class enhanced by heuristics.
	 * 
	 * @param state: The state that resides in this node
	 * @param depth: The current depth of this node
	 * @param parent: The node's parent, otherwise null if root
	 * @param gValue: The path cost already paid for reaching this node
	 * @param heuristic: The estimated path cost from this node to goal
	 */
	public HeuristicsNode(State state, int depth, Node parent, int gValue, int heuristic) {
		super(state, depth, parent);
		this.f = gValue + heuristic;
		this.g = gValue;
		this.heuristic = heuristic;
	}

	/**
	 * A wrapper for the node class enhanced by heuristics.
	 * 
	 * @param node: The current node
	 * @param gValue: The path cost already paid for reaching this node
	 * @param heuristics: The estimated path cost from this node to goal
	 */
	public HeuristicsNode(Node node, int gValue, int heuristic) {
		this(node.getState(), node.getDepth(), node.getParent(), gValue, heuristic);
	}
	
	/**
	 * A wrapper for the node class using 0 for heuristics/path cost.
	 * 
	 * @param node: The current node
	 */
	public HeuristicsNode(Node node) {
		this(node, 0, 0);
	}

	/**
	 * Getter for the gValue.
	 * @return The already paid path cost for reaching this node
	 */
	public int getG() {
		return g;
	}

	/**
	 * Setter for the gValue
	 * @param g: Sets the path cost to reach this node
	 */
	public void setG(int g) {
		this.g = g;
		this.f = g + this.heuristic;
	}

	/**
	 * Getter for the heuristic value.
	 * @return The estimated path cost from this node to goal
	 */
	public int getHeuristic() {
		return heuristic;
	}

	/**
	 * Setter for the heuristic value.
	 * @param heuristic: Sets the estimated path cost for reaching the goal from this node
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
		this.f = this.g + heuristic;
	}

	/**
	 * Getter for the fValue
	 * @return The total amount of costs paid to reach this node + estimated costs 
	 * to reach goal from this node.
	 */
	public int getF() {
		return f;
	}
}
