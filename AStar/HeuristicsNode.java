package AStar;

public class HeuristicsNode extends Node implements Comparable<HeuristicsNode> {
	
	private Node node;
	private int f;
	private int g;
	private int h;
	
	public HeuristicsNode(Node node, int g, int h) {
		super(node.getState(), node.getDepth(), node.getParent());
		this.node = node;
		this.h = h;
		this.g = g;
		this.f = g + h;
		
	}
	
	public int getF() {
		return this.f;
	}

	/**
	 * Comparator
	 */
	@Override
	public int compareTo(HeuristicsNode other) {
		return this.getF() - other.getF();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + f;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof HeuristicsNode) {
			return ((HeuristicsNode) obj).getState().equals(this.getState());
		}
		
		if (obj instanceof Node) {
			return ((Node) obj).getState().equals(this.getState());
		}
		
		if (obj instanceof State) {
			return ((State) obj).equals(this.getState());
		}
		
		return false;
	}

	
	
}
