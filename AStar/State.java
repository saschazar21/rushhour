package AStar;
import java.util.*;

/**
 * This is the class for representing a single state of the rush hour
 * puzzle.  Methods are provided for constructing a state, for
 * accessing information about a state, for printing a state, and for
 * expanding a state (i.e., obtaining a list of all states immediately
 * reachable from it).
 * <p>
 * Every car is constrained to only move horizontally or vertically.
 * Therefore, each car has one dimension along which it is fixed, and
 * another dimension along which it can be moved.  This variable
 * dimension is stored here as part of the state.  A link to the
 * puzzle with which this state is associated is also stored.
 * Note that the goal car is always assigned index 0.  
 * <p>
 * To make it easier to use <tt>State</tt> objects with some of the
 * data structures provided as part of the Standard Java Platform, we
 * also have provided <tt>hashCode</tt> and <tt>equals</tt> methods.
 * You probably will not need to access these methods directly, but
 * they are likely to be used implicitly if you take advantage of the
 * Java Platform.  These methods define two <tt>State</tt> objects to
 * be equal if they refer to the same <tt>Puzzle</tt> object, and if
 * they indicate that the cars have the identical variable positions
 * in both states.  The hashcode is designed to satisfy the general
 * contract of the <tt>Object.hashCode</tt> method that it overrides,
 * with regard to the redefinition of <tt>equals</tt>.
 */
public class State {

    private Puzzle puzzle;
    private int varPos[];

    /**
     * The main constructor for constructing a state.  You probably
     * will never need to use this constructor.
     *
     *   @param puzzle the puzzle that this state is associated with
     *   @param varPos the variable position of each of the cars in this state
     */
    public State(Puzzle puzzle,
		 int varPos[]) {
	this.puzzle = puzzle;
	this.varPos = varPos;
	computeHashCode();
    }

    /** Returns true if and only if this state is a goal state. */
    public boolean isGoal() {
	return (varPos[0] == puzzle.getGridSize() - 1);
    }

    /** Returns the variable position of car <tt>v</tt>. */
    public int getVariablePosition(int v) {
	return varPos[v];
    }

    /** Returns the puzzle associated with this state. */
    public Puzzle getPuzzle() {
	return puzzle;
    }

    /** Prints to standard output a primitive text representation of the state. */
    public void print() {
	int grid[][] = getGrid();
	int gridsize = puzzle.getGridSize();

	System.out.print("+-");
	for (int x = 0; x < gridsize; x++) {
	    System.out.print("--");
	}
	System.out.println("+");

	for (int y = 0; y < gridsize; y++) {
	    System.out.print("| ");
	    for (int x = 0; x < gridsize; x++) {
		int v = grid[x][y];
		if (v < 0) {
		    System.out.print(". ");
		} else {
		    int size = puzzle.getCarSize(v);
		    if (puzzle.getCarOrient(v)) {
			System.out.print((y == varPos[v] ?
					  "^ " :
					  ((y == varPos[v] + size - 1) ?
					   "v " : "| ")));
		    } else {
			System.out.print(x == varPos[v] ? "< " :
					 ((x == varPos[v] + size - 1) ? "> " :
					  "- "));
		    }
		}
	    }
	    System.out.println((puzzle.getCarOrient(0)
				|| y != puzzle.getFixedPosition(0))
			       ? "|"
			       : (isGoal() ? ">" : " "));
	}

	System.out.print("+-");
	for (int x = 0; x < gridsize; x++) {
	    System.out.print((!puzzle.getCarOrient(0)
				|| x != puzzle.getFixedPosition(0))
			       ? "--"
			       : (isGoal() ? "v-" : " -"));
	}
	System.out.println("+");

    }

    /**
     * Computes a grid representation of the state.  In particular, an
     * nxn two-dimensional integer array is computed and returned,
     * where n is the size of the puzzle grid.  The <tt>(i,j)</tt>
     * element of this grid is equal to -1 if square <tt>(i,j)</tt> is
     * unoccupied, and otherwise contains the index of the car
     * occupying this square.  Note that the grid is recomputed each
     * time this method is called.
     */
    public int[][] getGrid() {
	int gridsize = puzzle.getGridSize();
	int grid[][] = new int[gridsize][gridsize];

	for (int i = 0; i < gridsize; i++)
	    for (int j = 0; j < gridsize; j++)
		grid[i][j] = -1;

	int num_cars = puzzle.getNumCars();

	for (int v = 0; v < num_cars; v++) {
	    boolean orient = puzzle.getCarOrient(v);
	    int size = puzzle.getCarSize(v);
	    int fp = puzzle.getFixedPosition(v);
	    if (v == 0 && varPos[v] + size > gridsize)
		size--;
	    if (orient) {
		for (int d = 0; d < size; d++)
		    grid[fp][varPos[v] + d] = v;
	    } else {
		for (int d = 0; d < size; d++)
		    grid[varPos[v] + d][fp] = v;
	    }
	}
	return grid;
	
    }

    /**
     * Computes all of the states immediately reachable from this
     * state and returns them as an array of states.  You probably
     * will not need to use this method directly, since ordinarily you
     * will be expanding <tt>Node</tt>s, not <tt>State</tt>s.
     */
    public State[] expand() {
	int gridsize = puzzle.getGridSize();
	int grid[][] = getGrid();
	int num_cars = puzzle.getNumCars();

	ArrayList new_states = new ArrayList();

	for (int v = 0; v < num_cars; v++) {
	    int p = varPos[v];
	    int fp = puzzle.getFixedPosition(v);
	    boolean orient = puzzle.getCarOrient(v);
	    for (int np = p - 1;
		 np >= 0 && (orient ? grid[fp][np] : grid[np][fp]) < 0;
		 np--) {
		int[] newVarPos = (int []) varPos.clone();
		newVarPos[v] = np;
		new_states.add(new State(puzzle, newVarPos));
	    }
		
	    int carsize = puzzle.getCarSize(v);
	    for (int np = p + carsize;
		 (np < gridsize
		  && (orient ? grid[fp][np] : grid[np][fp]) < 0)
		     ||
		     (v == 0 && np == gridsize);
		 np++) {
		int[] newVarPos = (int []) varPos.clone();
		newVarPos[v] = np - carsize + 1;
		new_states.add(new State(puzzle, newVarPos));
	    }
	}

	puzzle.incrementSearchCount(new_states.size());

	return (State[]) new_states.toArray(new State[0]);
    }

    private int hashcode;

    private void computeHashCode() {
	hashcode = puzzle.hashCode();
	for (int i = 0; i < varPos.length; i++)
	    hashcode =  31 * hashcode + varPos[i];
    }

    /**
     * Returns a hash code value for this <tt>State</tt> object.
     * Although you probably will not need to use it directly, this
     * method is provided for the benefit of hashtables given in the
     * Java Platform.  See documentation on <tt>Object.hashCode</tt>,
     * which this method overrides, for the general contract that
     * <tt>hashCode</tt> methods must satisfy.
     */
    public int hashCode() {
	return hashcode;
    }

    /**
     * Returns <tt>true</tt> if and only if this state is considered
     * equal to the given object.  In particular, equality is defined
     * to hold if the given object is also a <tt>State</tt> object, if
     * it is associated with the same <tt>Puzzle</tt> object, and if
     * the cars in both states are in the identical positions.  This
     * method overrides <tt>Object.equals</tt>.
     */
    public boolean equals(Object o) {
	State s;
	try {
	    s = (State) o;
	}
	catch (ClassCastException e) {
	    return false;
	}
	if (hashcode != s.hashcode || !puzzle.equals(s.puzzle))
	    return false;

	for (int i = 0; i < varPos.length; i++)
	    if (varPos[i] != s.varPos[i])
		return false;
	return true;
    }
}
