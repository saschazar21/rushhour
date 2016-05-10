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
