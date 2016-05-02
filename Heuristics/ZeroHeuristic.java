package Heuristics;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a trivial heuristic function that always returns zero.
 */
public class ZeroHeuristic implements Heuristic {

	/**
	 * A vacuous constructor, provided in this form for consistancy with the
	 * other <tt>Heuristic</tt> implementations.
	 */
	public ZeroHeuristic(Puzzle puzzle) {

	}

	/**
	 * Returns the value of the heuristic, which is always zero.
	 */
	public int getValue(State state) {
		return 0;
	}

}
