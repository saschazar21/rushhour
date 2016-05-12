package AStar;

public class HNode extends Node implements Comparable<HNode> {
	
	private int f;
	
	public HNode(Node node, int h) {
		super(node.getState(), node.getDepth(), node.getParent());
		this.f = node.getDepth() + h;
		
	}

	/**
	 * Comparator
	 */
	@Override
	public int compareTo(HNode other) {
		if (this.f == other.f) {
			return 0;
		}
		
		if (this.f > other.f) {
			return 1;
		}
		
		return -1;
	}

	@Override
	public int hashCode() {
		return this.getState().hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		
		if (this == other) {
			return true;
		}
		
		
		if (other instanceof Node || other instanceof HNode) {
			return ((Node) other).getState().equals(this.getState());
		}
		
		return false;
	}

}
