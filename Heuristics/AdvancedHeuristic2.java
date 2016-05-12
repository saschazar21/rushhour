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
public class AdvancedHeuristic2 implements Heuristic {

	private Puzzle puzzle;
	private State state;
	private int numCars;
	ArrayList<Integer> visited;
	
	/**
	 * This is the required constructor, which must be of the given form.
	 */
	public AdvancedHeuristic2(Puzzle puzzle) {
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
		
		
		/**
		 * 
		 * exclusion list!
		 * 
		 * value = 1;
		 * 
		 * int[] blockingCars = getInititalBlockingCars();
		 * 
		 * for (blockingCars as car) {
		 * 
		 * 	int spaceNeeded = getSpaceNeeded(goalCar, car);
		 * 
		 * 	int[] blocking = getBlockingCars(car, spaceNeeded[0], spaceNeeded[1]);
		 * 	value += blocking.length;
		 * 
		 * }
		 * 
		 * return value;
		 */
		
		
		return 0;
	}
	
	private int[] getBlockingCars(int car, int needsSpaceFront, int needsSpaceBack) {
		
		
		/**
		 * 
		 * int[] blockingFront;
		 * int[] blockingBack;
		 * 
		 * for (all cars as next) {
		 * 
		 * 	 blockingFront.add(getBlockingCarFront(next, needsSpaceFront));
		 *   blockingBack.add(getBlockingCarBack(next, needsSpaceBack))
		 * 
		 * }
		 * 
		 * return min(blockingFront, blockingBack);
		 * 
		 */
		
		
		
		/**
		 * canMoveFwd(int car, int next, int needsSpaceFwd) {
		 * 
		 * 		int hasSpace = hasSpaceFwd();
		 *      
		 * 		return hasSpace >= needsSpaceFwd;
		 * 
		 * }
		 * 
		 * needsSpaceFwd(int car, int next, int needsSpaceFwdOld) {
		 * 
		 * 	if (car.orientation == next.orientation) {
		 * 		hasSpace = hasSpaceFwd(next)
		 * 		return needsSpaceFwdOld - hasSpace;
		 *  }
		 *  
		 *  return math;
		 * 
		 * }
		 * 
		 * hasSpaceFwd(int car, int next) {
		 * 	 return math;
		 * }
		 * 
		 * isWallAhead(int car, int needsSpaceFwd) {
		 * 	return math;
		 * }
		 * 
		 * isWallBehind(int car, int needsSpaceBwd) {
		 * 	return math;
		 * }
		 * 
		 */
		
		
		/**
		 * value = 1;
		 * 
		 * for (allCars as next) {
		 * 	
		 * 	needsSpaceFwd = needsSpaceFwd(car, next, needsSpaceFront);
		 *  needsSpaceBwd = needsSpaceBwd(car, next, needsSpaceBack);
		 * 
		 * 	if (!canMoveFwd(car, next, needsSpaceFront)) {
		 * 		valueFwd = getBlockingCars(next, needsSpaceFwd, needsSpaceBwd);
		 * 	} else if (isWallAhead(next, needsSpaceFwd)) {
		 * 		valueFwd = Integer.MAX_VALUE;
		 *  }
		 * 
		 * 
		 * 	if (!canMoveBwd(cat, next, needsSpaceBack)) {
		 * 		valueBwd = getBlockingCars(next, needsSpaceFwd, needsSpaceBwd);
		 * 	} else if (issWallBehind(next, needsSpaceBwd);
		 * 
		 * 	value += min(valueFwd, valueBwd)
		 * 
		 * }
		 * 
		 * return value;
		 * 
		 */
		
		
		// needsSpaceFwd?
		// hasSpaceFwd?
		// canMoveFwd?
		
		
		/**
		 * 
		 * int[] blockingFront;
		 * int[] blockingBack;
		 * 
		 * for (allCars as next) {
		 * 
		 * int blockingCarFront = canMoveFwd(car, needsSpaceFront);
		 * int blockingCarBack = canMoveBwd(car, needsSpaceBack);
		 * 
		 * if (blockingCarFront >= 0) {
		 * 	int spaceNeeded = getSpaceNeeded(car, next, needsSpaceFront, needsSpaceBack);
		 * 	blockingFront.add(getBlockingCars(blockingCarFront, spaceNeeded[0], spaceNeeded[1]));
		 * } else {
		 *  if (wallIsBlockingFront(blockingCarFront, needsSpaceFront)) {
		 *   blockingFront = new int[Integer.MAX_VALUE]
		 *  }
		 * }
		 * 
		 * if (blockingCarBack >= 0) {
		 * 	int spaceNeeded = getSpaceNeeded(car, next);
		 * 	blockingBack.add(getBlockingCars(blockingCarBack, spaceNeeded[0], spaceNeeded[1]));
		 * } else {
		 *  if (wallIsBlockingBack(blockingCarBack, needsSpaceBack))
		 *   blockingBack = new int[Integer.MAX_VALUE]
		 *  }
		 * }
		 * 
		 * }
		 * 
		 * return min(blockingFront.len, blockingBack.len);
		 * 
		 */
		
		return new int[0];
	}

}
