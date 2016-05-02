package AStar;

import java.util.ArrayList;
import java.util.List;

import Heuristics.Heuristic;

/**
 * This is the template for a class that performs A* search on a given rush hour
 * puzzle with a given heuristic. The main search computation is carried out by
 * the constructor for this class, which must be filled in. The solution (a path
 * from the initial state to a goal state) is returned as an array of
 * <tt>State</tt>s called <tt>path</tt> (where the first element
 * <tt>path[0]</tt> is the initial state). If no solution is found, the
 * <tt>path</tt> field should be set to <tt>null</tt>. You may also wish to
 * return other information by adding additional fields to the class.
 */
public class AStar {

    /** The solution path is stored here */
    public State[] path;
    
    private List<Node> open = new ArrayList<Node>();
    private List<Node> closed = new ArrayList<Node>();

    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {
    	open.add(puzzle.getInitNode());
    	
    	while(!open.isEmpty()) {
    		Node current = open.remove(0); // TODO: open list should be sorted
    		
    		if (current.getState().isGoal()) {
    			// TODO: save solution path in path array
    			break;
    		}
    		
    		for (Node successor : current.expand()) {
    			if (shouldSkip(successor)) {
    				continue;
    			}
    			
    			open.add(successor);
    		}

    		closed.add(current);
    	}

    }
    
    private boolean shouldSkip(Node successor) {
    	// TODO: http://web.mit.edu/eranki/www/tutorials/search/
    	return closed.contains(successor) || open.contains(successor);
    }

}
