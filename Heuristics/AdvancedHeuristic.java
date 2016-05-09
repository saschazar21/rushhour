package Heuristics;

import java.util.ArrayList;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a template for the class corresponding to your original advanced
 * heuristic. This class is an implementation of the <tt>Heuristic</tt>
 * interface. After thinking of an original heuristic, you should implement it
 * here, filling in the constructor and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {

	ArrayList<Integer> visited;
	private Puzzle puzzle;
	private int numCars;
	
	/**
	 * This is the required constructor, which must be of the given form.
	 */
	public AdvancedHeuristic(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.numCars = this.puzzle.getNumCars();
		this.visited = new ArrayList<Integer>();
	}

	/**
	 * This method returns the value of the heuristic function at the given
	 * state.
	 */
	public int getValue(State state) {
		this.visited.clear();
		
		if (state.isGoal()) {
			return 0;
		}
		
		return this.getMinimumRequiredMoves(state, 0);

	}
	
	private int getMinimumRequiredMoves(State state, int v) {
		if (visited.contains(v)) {
			return 0;
		}
		
		visited.add(v);
		
		int value = 1;
		
		int carPos = state.getVariablePosition(0);
		int carPosFront = carPos + this.puzzle.getCarSize(v) - 1;
		int carPosFixed = this.puzzle.getFixedPosition(v);
		
		for (int i = 0; i < this.numCars; i++) {
			if (i == v) {
				continue;
			}
			
			if (this.puzzle.getCarOrient(i) == this.puzzle.getCarOrient(v)) {
				continue;
			}
			
			if (this.puzzle.getFixedPosition(i) <= carPosFront) {
				continue;
			}
			
			int currentCarPos = state.getVariablePosition(i);
			int currentCarPosFront = currentCarPos + this.puzzle.getCarSize(i);
			
			if (carPosFixed >= currentCarPos && carPosFixed < currentCarPosFront) {
				value += getMinimumRequiredMoves(state, i);
			}
			
		}
		
		return value;
	}

}
