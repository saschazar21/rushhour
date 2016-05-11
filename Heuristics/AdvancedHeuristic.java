package Heuristics;

import java.util.ArrayList;
import java.util.Collections;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a template for the class corresponding to your original advanced
 * heuristic. This class is an implementation of the <tt>Heuristic</tt>
 * interface. After thinking of an original heuristic, you should implement it
 * here, filling in the constructor and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic implements Heuristic {

	private Puzzle puzzle;
	private State state;
	private int numCars;
	ArrayList<Integer> visited;
	
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
		this.state = state;
		this.visited.clear();
		
		if (state.isGoal()) {
			return 0;
		}
		
		return this.getMinimumRequiredMoves(state);

	}
	
	private int getMinimumRequiredMoves(State state) {
		return this.getMinimumRequiredMoves(state, 0);
	}
	
	private int getMinimumRequiredMoves(State state, int v) {
		return this.getMinimumRequiredMoves(state, v, -1);
	}
	
	private int getMinimumRequiredMoves(State state, int v, int previousV) {
		if (visited.contains(v)) {
			return 0;
		}
		
		visited.add(v);
		
		int value = 1;
		
		// int carPos = state.getVariablePosition(v);
		// int carPosFront = carPos + this.puzzle.getCarSize(v) - 1;
		// int carPosFixed = this.puzzle.getFixedPosition(v);
		
		// Memorize calculated values
		ArrayList<Integer> values = new ArrayList<Integer>();
		// boolean carCouldMove = false;
		
		for (int i = 0; i < this.numCars; i++) {
			
			// do not process the current v
			if (i == v) {
				continue;
			}
			
			// with a depth > 0 cars with same orientation could be next to each other => do not continue
			if (v== 0 && this.puzzle.getCarOrient(i) == this.puzzle.getCarOrient(v)) {
				continue;
			}
			
			// for the car that has to escape, ignore cars behind
			if (v == 0 && this.puzzle.getFixedPosition(i) <= state.getVariablePosition(v) + this.puzzle.getCarSize(v) - 1) {
			 	continue;
			}
			
			// int currentCarPos = state.getVariablePosition(i);
			// int currentCarPosFront = currentCarPos + this.puzzle.getCarSize(i);
			
			// if (carPosFixed >= currentCarPos && carPosFixed < currentCarPosFront) {
			
			if (isIntersecting(v, i)) {
				
				// if the road could be cleared for previousV by moving v, i is not really blocking v
				// for the car that has to escape, count any other car intersecting
				if (v != 0 && previousV > -1 && !isBlocking(previousV, v, i)) {
					continue;
				}
				
				/**
				 * i intersects with v
				 * ===================
				 * 
				 * - check if v could move to make space for ??? (previous v), or if i is really blocking
				 * -               (only check i's right next to v) ??? No?!
				 * - if only one car is blocking and no wall is blocking, stop here, v could move
				 * - if two cars are really blocking, calculate both routes and use the lower result for value
				 */
				
				// only do this checks if we have a previousV
				if (v != 0 && previousV > -1) {
					// check if v could move as far - although i is intersecting - to allow previousV to move
				}
				
				values.add(getMinimumRequiredMoves(state, i, v));
			}
			// 
		}
		
		if (v == 0) {
			for (Integer val : values) {
				value += val;
			}
			values.clear();
		}
		
		return values.isEmpty() ? value : Collections.min(values) + value;
	}
	
	private boolean isIntersecting(int car1, int car2) {
		// int car1Pos = state.getVariablePosition(car1);
		// int car1PosFront = car1Pos + this.puzzle.getCarSize(car1);
		int car1PosFixed = this.puzzle.getFixedPosition(car1);
		
		int car2Pos = state.getVariablePosition(car2);
		int car2PosFront = car2Pos + this.puzzle.getCarSize(car2);
		
		if (car1PosFixed >= car2Pos && car1PosFixed < car2PosFront) {
			return true;
		}
		
		return false;
	}
	
	// car2 is blocking car1
	// check if car2 could move to clear the road for car1, or if car3 is blocking
	private boolean isBlocking(int car1, int car2, int car3) {
		
		int car1Pos = state.getVariablePosition(car1);
		int car1PosFront = car1Pos + this.puzzle.getCarSize(car1);
		int car1PosFixed = this.puzzle.getFixedPosition(car1);
		boolean car1Orient = puzzle.getCarOrient(car1);
		
		int car2Pos = state.getVariablePosition(car2);
		int car2PosFront = car2Pos + this.puzzle.getCarSize(car2);
		int car2PosFixed = this.puzzle.getFixedPosition(car2);
		boolean car2Orient = puzzle.getCarOrient(car2);
		
		int car3Pos = state.getVariablePosition(car3);
		int car3PosFront = car2Pos + this.puzzle.getCarSize(car3);
		int car3PosFixed = this.puzzle.getFixedPosition(car3);
		boolean car3Orient = puzzle.getCarOrient(car3);
		
		// ???
		if (car1Orient == car2Orient) {
			return true;
		}
		
		
		
		return true;
	}

}
