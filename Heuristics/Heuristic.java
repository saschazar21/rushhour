package Heuristics;

import AStar.State;

/**
 * This is the interface that all heuristics must implement. In particular, all
 * such implementations must include a method <tt>getValue</tt> that returns the
 * value of the heuristic function at any given state.
 */
public interface Heuristic {

	/** Returns the value of the heuristic function at the given state. */
	public int getValue(State state);
}
