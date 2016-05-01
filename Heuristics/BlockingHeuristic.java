package Heuristics;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a template for the class corresponding to the blocking
 * heuristic.  This heuristic returns zero for goal states, and
 * otherwise returns one plus the number of cars blocking the path of
 * the goal car to the exit.  This class is an implementation of the
 * <tt>Heuristic</tt> interface, and must be implemented by filling in
 * the constructor and the <tt>getValue</tt> method.
 */
public class BlockingHeuristic implements Heuristic {

    /**
     * This is the required constructor, which must be of the given form.
     */
    public BlockingHeuristic(Puzzle puzzle) {

	// your code here

    }
	

    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {

	// your code here
    	return 0;

    }

}
