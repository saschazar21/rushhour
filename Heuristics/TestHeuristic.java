package Heuristics;

import java.util.HashMap;
import java.util.HashSet;

import AStar.Puzzle;
import AStar.State;


/**
 * This is a template for the class corresponding to your original
 * advanced heuristic.  This class is an implementation of the
 * <tt>Heuristic</tt> interface.  After thinking of an original
 * heuristic, you should implement it here, filling in the constructor
 * and the <tt>getValue</tt> method.
 */
public class TestHeuristic implements Heuristic {

    /**
     * This is the required constructor, which must be of the given form.
     */
    public TestHeuristic(Puzzle puzzle) {

	// your code here

    }
	
    /**
     * This method returns the value of the heuristic function at the
     * given state.
     */
    public int getValue(State state) {    	
    	if (state.isGoal())  return 0;  // Heuristic function for the goal location is 0     		    	
    	HashMap <Position, Integer> positions = buildHashMap (state); // Building the hash set out of all the cars    	    	
    	// HashMap<Position, Integer> hm = positions;
    	
    	
    	HashSet <Integer> blockingCars = new HashSet <Integer>(); // To identify the set of cars that are blocking   
    	
    	
    	Puzzle puzzle = state.getPuzzle();
    	
    	
    	int fixedPositionGoalCar = puzzle.getFixedPosition(0),
    		variablePositionGoalCar = state.getVariablePosition(0),
    		goalCarSize = puzzle.getCarSize(0);
    	
    	int numberCars = puzzle.getNumCars();
    	int gridSize = puzzle.getGridSize();    
    	
    	for (int currPos = variablePositionGoalCar + goalCarSize; currPos <= gridSize-1; currPos++){
    		Position p = new Position(currPos, fixedPositionGoalCar);    		
    		if (positions.containsKey(p)){    	    			
    			blockingCars.add(positions.get(p));
    		}
    	}
    	
    	
    	
    	for (int count = 1; count < numberCars; count++){
    		if (blockingCars.contains(count) && isCarStuck(count, state, positions)){
    			if (puzzle.getCarOrient(count)){
    	    		int fixedPositionCar = puzzle.getFixedPosition(count);
    			    int variablePositionCar = state.getVariablePosition(count);
    			    int carSize = puzzle.getCarSize(count);    	    
    			    Position lowerLimit = new Position (fixedPositionCar, variablePositionCar + carSize);
    			    Position upperLimit = new Position (fixedPositionCar, fixedPositionGoalCar + carSize);
    				int blockingCarNumber = getBlockingCar (positions, lowerLimit, upperLimit, true);
    				if (blockingCarNumber > 0 && !(blockingCars.contains(blockingCarNumber))){
    					blockingCars.add(blockingCarNumber);
    				}    				
    			}
    		}
    	}
    	
      return blockingCars.size() + 1 ;
    }
    
   	class Position {
		public int x, y;
		public int getX (){
			return x;
		}
		public int getY (){
			return y;
		}
		public Position (int x_coord, int y_coord){
			this.x = x_coord;
			this.y = y_coord;
		}	
		@Override
		public boolean equals(Object other) {
	         return (this.getX() == ((Position) other).getX() && this.getY() == ((Position) other).getY());
	     }	
		
		public int hashCode() {
	         int hash = 1;
	         hash = hash * 31 + this.x;
	         hash = hash * 31 + this.y;
	         return hash;
	     }
		
	}

    
    public boolean isCarStuck (int carNumber, State state, HashMap <Position, Integer> positions){
    	Puzzle puzzle = state.getPuzzle();
    	int carVariablePosition = state.getVariablePosition(carNumber);
    	int carFixedPosition =  puzzle.getFixedPosition(carNumber);
    	int gridSize = puzzle.getGridSize();
    	int carSize = puzzle.getCarSize(carNumber);
    	int goalCarLocation = puzzle.getFixedPosition(0);
    	boolean isVertical = puzzle.getCarOrient(carNumber);
    	
    	if (isVertical && carStuckVertically (new Position(carFixedPosition, carVariablePosition), carSize, positions, goalCarLocation, gridSize))
    		return true;
    	return false;
    	
    }
   
   public boolean carStuckVertically (Position carPosition, int carSize, HashMap <Position, Integer> positions, int goalCarLocation, int gridSize){
	   if (!canCarMoveUp (carPosition, carSize, positions, goalCarLocation) && 
			   !canCarMoveDown(carPosition, carSize, positions, goalCarLocation, gridSize)){
		   return true;
	   } else {
		   return false;
	   }
   }

   // OK!
   public HashMap <Position, Integer> buildHashMap (State state){
	   HashMap <Position, Integer> isOccupied = new HashMap <Position, Integer> ();
	   Puzzle puzzle = state.getPuzzle();
	   int numCars = puzzle.getNumCars();
	   for (int count = 0; count < numCars; count++){
		   if (puzzle.getCarOrient(count)){
			  int carLowerLimitY = state.getVariablePosition(count);
			  int carLowerLimitX = puzzle.getFixedPosition(count);
			  int carSize = puzzle.getCarSize(count);
			  // isOccupied.put((new Position(carLowerLimitX, carLowerLimitY)), count);
			  for (int carPosition = 0; carPosition < carSize; carPosition++){
				  isOccupied.put((new Position(carLowerLimitX, carLowerLimitY + carPosition)), count);				  
			  }
		   } else {
				  int carLowerLimitY = puzzle.getFixedPosition(count);
				  int carLowerLimitX = state.getVariablePosition(count);
				  int carSize = puzzle.getCarSize(count);
				  // isOccupied.put((new Position(carLowerLimitX, carLowerLimitY)), count);
				  for (int carPosition = 0; carPosition < carSize; carPosition++){
					  isOccupied.put((new Position(carLowerLimitX + carPosition, carLowerLimitY)), count);					 
				  }			   
		   }		   
	   }	
	return isOccupied;
   }
    
   public boolean canCarMoveUp (Position carPosition, int carSize, HashMap <Position, Integer> positions, int goalCarLocation){
	   if (
			(carPosition.getY() == 0) || (carSize > carPosition.getY() + 1) 
			|| (goalCarLocation >= carSize && checkRange(positions, new Position(carPosition.getX(), goalCarLocation - carSize),
					new Position(carPosition.getX(), carPosition.getY()-1), true) || (carSize > goalCarLocation))    
		  )
		return false;
	   return true ;
   } 
   
   public boolean canCarMoveDown (Position carPosition, int carSize, HashMap <Position, Integer> positions, int goalCarLocation, int gridSize){	   
	   if (			  
			(checkRange(positions, new Position(carPosition.getX(), goalCarLocation + 1),
					new Position(carPosition.getX(), goalCarLocation + carSize), true))    
		  )
		return false;
	   return true ;
   }  
    
  public Integer getBlockingCar (HashMap <Position, Integer> positions, Position lowerLimit, Position upperLimit, boolean isVertical){	  
	   if (!isVertical){	   
		   for (int count = lowerLimit.getX(); count <= upperLimit.getX(); count++){
			   if (positions.containsKey(new Position(count, upperLimit.getY())))
				   return (positions.get(new Position(count, upperLimit.getY())));
		   }		   
	   } else {
			   for (int count = lowerLimit.getY(); count <= upperLimit.getY(); count++){
				   if (positions.containsKey(new Position(upperLimit.getX(), count)))
					   return (positions.get(new Position(upperLimit.getX(), count)));
			   }			  
		   }
	   
	   return -1;
	   }
  
  public boolean checkRange (HashMap <Position, Integer> positions, Position lowerLimit, Position upperLimit, boolean isVertical){
	   if (!isVertical){	   
		   for (int count = lowerLimit.getX(); count <= upperLimit.getX(); count++){
			   if (positions.containsKey(new Position(count, upperLimit.getY())))
				   return true;
		   }		   
	   } else {
			   for (int count = lowerLimit.getY(); count <= upperLimit.getY(); count++){
				   if (positions.containsKey(new Position(upperLimit.getX(), count)))
					   return true;
			   }			  
		   }
	   return false;
	   }
   }


