package AStar;

public class HeuristicsNode extends Node {
	
	private float f;
	private float g;
	private float heuristic;
	
	/**
	 * A wrapper for the node class enhanced by heuristics.
	 * 
	 * @param node: The current node
	 * @param gValue: The path cost already paid for reaching this node
	 * @param heuristics: The estimated path cost from this node to goal
	 */
	public HeuristicsNode(Node node, float gValue, float heuristic) {
		super(node.getState(), node.getDepth(), node.getParent());
		this.f = gValue + heuristic;
		this.g = gValue;
		this.heuristic = heuristic;
	}
	
	/**
	 * A wrapper for the node class using 0 for heuristics/path cost.
	 * 
	 * @param node: The current node
	 */
	public HeuristicsNode(Node node) {
		this(node, 0f, 0f);
	}

	/**
	 * Getter for the gValue.
	 * @return The already paid path cost for reaching this node
	 */
	public float getG() {
		return g;
	}

	/**
	 * Setter for the gValue
	 * @param g: Sets the path cost to reach this node
	 */
	public void setG(float g) {
		this.g = g;
		this.f = g + this.heuristic;
	}

	/**
	 * Getter for the heuristic value.
	 * @return The estimated path cost from this node to goal
	 */
	public float getHeuristic() {
		return heuristic;
	}

	/**
	 * Setter for the heuristic value.
	 * @param heuristic: Sets the estimated path cost for reaching the goal from this node
	 */
	public void setHeuristic(float heuristic) {
		this.heuristic = heuristic;
		this.f = this.g + heuristic;
	}

	/**
	 * Getter for the fValue
	 * @return The total amount of costs paid to reach this node + estimated costs 
	 * to reach goal from this node.
	 */
	public float getF() {
		return f;
	}
}
