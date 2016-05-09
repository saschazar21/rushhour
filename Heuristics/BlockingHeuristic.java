package Heuristics;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a template for the class corresponding to the blocking heuristic.
 * This heuristic returns zero for goal states, and otherwise returns one plus
 * the number of cars blocking the path of the goal car to the exit. This class
 * is an implementation of the <tt>Heuristic</tt> interface, and must be
 * implemented by filling in the constructor and the <tt>getValue</tt> method.
 */
public class BlockingHeuristic implements Heuristic {
	
	private Puzzle puzzle;		// The current puzzle to evaluate heuristics on
	private int numCars;        // The total number of cars on the jam
	private int carPosFixed;	// The initial fixed position of our car
	private int carSize;		// The size of our car
	private int blocking;		// No of blocking cars in the way of our car

	/**
	 * This is the required constructor, which must be of the given form.
	 */
	public BlockingHeuristic(Puzzle puzzle) {
		this.puzzle = puzzle;							// Store current puzzle
		this.numCars = this.puzzle.getNumCars();        // Get the total number of cars
		this.carPosFixed = puzzle.getFixedPosition(0);	// Get fixed position of our car.
		
		// -1 needed due to variable position not being identified as 0 otherwise
		this.carSize = this.puzzle.getCarSize(0) - 1;  // Get size of our car
	}

	/**
	 * This method returns the value of the heuristic function at the given
	 * state.
	 */
	public int getValue(State state) {
		
		// Return 0, if state equals goal state (No further heuristics needed)
		if (state.isGoal()) {
			return 0;
		}
		
		this.blocking = 1;	// At least one car is still blocking our way.
		
		// Calculate the outermost position of our car,
		int carPosFront = state.getVariablePosition(0) + this.carSize;
		
		for (int i = 0; i < this.numCars; i++) {
			
			if (!this.puzzle.getCarOrient(i)) {			// Car is horizontally aligned as well,
				continue;								// does not block our car.
			}
			
			if (this.puzzle.getFixedPosition(i) <= carPosFront) {
				continue;								// Car is behind of our car, does not block.
			}
			
			/* Now calculate how many cars are really standing between us and the goal, idea:
			* -----------------
			* 
			* Check on behalf of our car's point of view, if possibly blocking car reaches from our
			* right (variablePosition of i smaller than our fixedPosition) to our 
			* left (variablePosition + length of i greater than our fixedPosition),
			* as we're looking left to the top of the game board, right to the bottom.
			*/
			int currentCarPos = state.getVariablePosition(i);
			int currentCarPosFront = currentCarPos + this.puzzle.getCarSize(i);
			
			if (currentCarPosFront >= this.carPosFixed && currentCarPos <= this.carPosFixed) {
				this.blocking++;
			}
		}
		
		return this.blocking;
	}

}
