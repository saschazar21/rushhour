package Heuristics;

class Position {
	private int x, y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Position (int x, int y){
		this.x = x;
		this.y = y;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = result * prime + x;
		result = result * prime + y;
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		
		if (this == other) {
			return true;
		}
		
		if (other instanceof Position) {
			Position pos = (Position) other;
			
			return x == pos.x && y == pos.y;
		}
		
		return false;
	}
	
}