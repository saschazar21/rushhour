package Heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a template for the class corresponding to your original advanced
 * heuristic. This class is an implementation of the <tt>Heuristic</tt>
 * interface. After thinking of an original heuristic, you should implement it
 * here, filling in the constructor and the <tt>getValue</tt> method.
 */
public class WorkInProgressHeuristic implements Heuristic {

	private Puzzle puzzle;
	private State state;
	private int numCars;
	private Map<Position, Integer> occupied;
	private Set<Integer> blocking;
	
	/**
	 * This is the required constructor, which must be of the given form.
	 */
	public WorkInProgressHeuristic(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.numCars = this.puzzle.getNumCars();
	}

	/**
	 * This method returns the value of the heuristic function at the given
	 * state.
	 */
	public int getValue(State state) {
		if (state.isGoal()) return 0;
		
		this.occupied = getOccupiedPositions();
		this.blocking = getInitialBlockingCars();
		
		
		return blocking.size() + 1;

	}
	
	private Set<Integer> getInitialBlockingCars() {
		Set<Integer> blocking = new HashSet<Integer>();
		
		int carPosFixed = puzzle.getFixedPosition(0);
		int carPosFront = state.getVariablePosition(0) + puzzle.getCarSize(0);
		
		for (int i = 1; i < numCars; i++) {
			if (!puzzle.getCarOrient(i)) {
				continue;
			}
			
			if (puzzle.getFixedPosition(i) < carPosFront) {
				continue;
			}
			
			int currentCarPos = state.getVariablePosition(i);
			int currentCarPosFront = currentCarPos + this.puzzle.getCarSize(i);
			
			if (carPosFixed >= currentCarPos && carPosFixed < currentCarPosFront) {
				blocking.add(i);
			}
		}
		
		return blocking;
	}
	
	private Map<Position, Integer> getOccupiedPositions() {
		Map<Position, Integer> occupied = new HashMap<Position, Integer>();
		
		for (int car = 0; car < numCars; car++) {
			int x, y, size;
			
			if (puzzle.getCarOrient(car)) {
				x = puzzle.getFixedPosition(car);
				y = state.getVariablePosition(car);
				size = puzzle.getCarSize(car);
					  
				for (int i = 0; i < size; i++) {
					occupied.put((new Position(x, y + i)), car);				  
				}
			} else {
				x = state.getVariablePosition(car);
				y = puzzle.getFixedPosition(car);
				size = puzzle.getCarSize(car);
			
				for (int i = 0; i < size; i++){
					occupied.put(new Position(x + i, y), car);					 
				}
			}
		}
		   
		return occupied;
	}

}
