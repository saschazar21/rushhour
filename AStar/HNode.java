package AStar;

public class HNode extends Node implements Comparable<HNode> {
	
	// private Node node;
	private int f;
	private int g;
	private int h;
	
	public HNode(Node node, int h) {
		super(node.getState(), node.getDepth(), node.getParent());
		// this.node = node;
		this.h = h;
		this.g = node.getDepth();
		this.f = this.g + this.h;
		
	}

	/**
	 * Comparator
	 */
	@Override
	public int compareTo(HNode other) {
		return this.f - other.f;
	}

	@Override
	public int hashCode() {
		return this.getState().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof Node || obj instanceof HNode) {
			return ((Node) obj).getState().equals(this.getState());
		}
		
		return false;
	}

}
