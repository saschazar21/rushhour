package Heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import AStar.Puzzle;
import AStar.State;

/**
 * This is a template for the class corresponding to your original advanced
 * heuristic. This class is an implementation of the <tt>Heuristic</tt>
 * interface. After thinking of an original heuristic, you should implement it
 * here, filling in the constructor and the <tt>getValue</tt> method.
 */
public class AdvancedHeuristic3 implements Heuristic {

	private Puzzle puzzle;
	private State state;
	private int numCars;
	ArrayList<Integer> visited;
	
	/**
	 * This is the required constructor, which must be of the given form.
	 */
	public AdvancedHeuristic3(Puzzle puzzle) {
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
		visited.add(0);
		
		int value = 1;
		
		for (Integer car : getInitialBlockingCars()) {
			
			int needsSpaceFront = needsSpaceFwd(0, car, 0);
			int needsSpaceBack = needsSpaceBwd(0, car, 0);
			
			value += getBlockingValue(car, needsSpaceFront, needsSpaceBack);
			
		}
		
		return value;
	}
	
	private ArrayList<Integer> getInitialBlockingCars() {
		
		ArrayList<Integer> blocking = new ArrayList<Integer>();
		
		boolean orientation = puzzle.getCarOrient(0);
		int size =puzzle.getCarSize(0);
		int pos = state.getVariablePosition(0);
		int posFixed = puzzle.getFixedPosition(0);
		
		for (int i = 1; i < this.numCars; i++) {
			
			if (orientation == puzzle.getCarOrient(i)) {
				continue;
			}
			
			int iFixed = puzzle.getFixedPosition(i);
			
			if (iFixed < pos + size) {
			 	continue;
			}
			
			int iPos = state.getVariablePosition(i);
			int iPosFront = iPos + puzzle.getCarSize(i);
			
			if (posFixed >= iPos && posFixed < iPosFront) {
				blocking.add(i);
			}
			
		}
		
		return blocking;	
	}
	
	private int getBlockingValue(int car, int needsSpaceFront, int needsSpaceBack) {
		if (visited.contains(car)) {
			return 0;
		}
		
		visited.add(0);
		
		int value = 1;
		
		for (int next = 1; next < numCars; next++) {
			
			if (!isIntersecting(car, next)) {
				continue;
			}
			
			int valueFwd = 0, valueBwd = 0;
			
			boolean fwdMoveable = canMoveFwd(car, next, needsSpaceFront);
			boolean bwdMoveable = canMoveBwd(car, next, needsSpaceBack);
			
			int needsSpaceFwd = needsSpaceFwd(car, next, needsSpaceFront);
			int needsSpaceBwd = needsSpaceBwd(car, next, needsSpaceBack);
			
			if (!fwdMoveable) {
				valueFwd = getBlockingValue(next, needsSpaceFwd, needsSpaceBwd);
			} else if (isWallAhead(car, needsSpaceFront)) {
				valueFwd = Integer.MAX_VALUE;
			}
			
			if (!bwdMoveable) {
				valueBwd = getBlockingValue(next, needsSpaceFwd, needsSpaceBwd);
			} else if (isWallBehind(car, needsSpaceBack)) {
				valueBwd = Integer.MAX_VALUE;
			}
			
			value += Math.min(valueFwd, valueBwd);
			
		}
		
		return value;
	}
	
	private boolean canMoveFwd(int car, int next, int needsSpaceFwd) {
		if (isBehind(car, next)) {
			return true;
		}
		
		int hasSpace = hasSpaceFwd(car, next);
		return hasSpace >= needsSpaceFwd;
	}
	
	private int needsSpaceFwd(int car, int next, int needsSpaceFwdOld) {
		if (puzzle.getCarOrient(car) == puzzle.getCarOrient(next)) {
			int hasSpace = hasSpaceFwd(car, next);
			return needsSpaceFwdOld - hasSpace;
		}
		
		int carFixed = puzzle.getFixedPosition(car);
		int nextPos = state.getVariablePosition(next);
		int nextFront = nextPos + puzzle.getCarSize(next);
		
		return Math.abs(carFixed - nextPos) + 1;
	}
	
	private int hasSpaceFwd(int car, int next) {
		
		// int carFixed = puzzle.getFixedPosition(car);
		int carPos = state.getVariablePosition(car);
		int carPosFront = carPos + puzzle.getCarSize(car);
		
		int nextPos = state.getVariablePosition(next);
		// int nextFront = nextPos + puzzle.getCarSize(next);
		int nextFixed = puzzle.getFixedPosition(next);
		
		if (puzzle.getCarOrient(car) != puzzle.getCarOrient(next)) {
			return Math.abs(carPosFront - nextFixed);
		}
		
		return Math.abs(carPosFront - nextPos);
	}
	
	private boolean isWallAhead(int car, int needsSpaceFwd) {
		int carPos = state.getVariablePosition(car);
		int carPosFront = carPos + puzzle.getCarSize(car);
		
		if (carPosFront + needsSpaceFwd > puzzle.getGridSize()) {
			return true;
		}
		
		return false;
	}
	
	private boolean canMoveBwd(int car, int next, int needsSpaceBwd) {
		if (!isBehind(car, next)) {
			return true;
		}
		
		int hasSpace = hasSpaceBwd(car, next);
		return hasSpace >= needsSpaceBwd;
	}
	
	private int needsSpaceBwd(int car, int next, int needsSpaceBwdOld) {
		if (puzzle.getCarOrient(car) == puzzle.getCarOrient(next)) {
			int hasSpace = hasSpaceBwd(car, next);
			return needsSpaceBwdOld - hasSpace;
		}
		
		int carFixed = puzzle.getFixedPosition(car);
		int nextPos = state.getVariablePosition(next);
		int nextFront = nextPos + puzzle.getCarSize(next);
		
		return Math.abs(carFixed - nextFront);
	}
	
	private int hasSpaceBwd(int car, int next) {
		
		int carFixed = puzzle.getFixedPosition(car);
		int carPos = state.getVariablePosition(car);
		int carPosFront = carPos + puzzle.getCarSize(car);
		
		int nextPos = state.getVariablePosition(next);
		int nextFront = nextPos + puzzle.getCarSize(next);
		int nextFixed = puzzle.getFixedPosition(next);
		
		if (puzzle.getCarOrient(car) != puzzle.getCarOrient(next)) {
			return Math.abs(carPos - nextFixed) + 1;
		}
		
		return Math.abs(carPos - nextFront);
	}
	
	private boolean isWallBehind(int car, int needsSpaceBwd) {
		int carPos = state.getVariablePosition(car);
		// int carPosFront = carPos + puzzle.getCarSize(car);
		
		if (carPos - needsSpaceBwd < 0) {
			return true;
		}
		
		return false;
	}
	
	private boolean isIntersecting(int v, int i) {
		boolean vOrient = this.puzzle.getCarOrient(v);
		int vSize = this.puzzle.getCarSize(v);
		int vPos = state.getVariablePosition(v);
		int vPosFront = vPos + vSize;
		int vFixed = this.puzzle.getFixedPosition(v);
		
		boolean iOrient = this.puzzle.getCarOrient(i);
		int iSize = this.puzzle.getCarSize(i);
		int iPos = state.getVariablePosition(i);
		int iPosFront = iPos + iSize;
		int iFixed = this.puzzle.getFixedPosition(i);
		
		if (vOrient == iOrient) {
			return vFixed == iFixed;
		}
		
		return vFixed >= iPos && vFixed < iPosFront;
	}
	
	private boolean isBehind(int v, int i) {
		boolean vOrient = this.puzzle.getCarOrient(v);
		int vSize = this.puzzle.getCarSize(v);
		int vPos = state.getVariablePosition(v);
		int vPosFront = vPos + vSize;
		int vFixed = this.puzzle.getFixedPosition(v);
		
		boolean iOrient = this.puzzle.getCarOrient(i);
		int iSize = this.puzzle.getCarSize(i);
		int iPos = state.getVariablePosition(i);
		int iPosFront = iPos + iSize;
		int iFixed = this.puzzle.getFixedPosition(i);
		
		if (vOrient == iOrient) {
			return iPos + iSize <= vPos;
		}
		
		return iFixed < vPos + vSize;
	}

}
