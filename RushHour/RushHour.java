package RushHour;

import java.io.*;
import java.text.*;

import AStar.AStar;
import AStar.BranchingFactor;
import AStar.Puzzle;
import Heuristics.AdvancedHeuristic;
import Heuristics.BlockingHeuristic;
import Heuristics.Heuristic;
import Heuristics.ZeroHeuristic;

/**
 * This class contains only a simple main for testing your algorithm on data
 * with one or more heuristics. The main begins by reading in all of the puzzles
 * described in a file named in <tt>argv[0]</tt>. It then proceeds to run A*
 * using each heuristic listed below on each of the puzzles (simply comment out
 * any heuristics you don't want to be testing on). In each case, it prints out
 * the solution path that was computed. Finally, it prints out a summary table
 * of the results. You may wish to modify or replace this <tt>main</tt> in any
 * way that you wish. (However, other classes that we have provided should not
 * be modified directly.)
 */
public class RushHour {

	/**
	 * The main for running all puzzles with all heuristics, and printing out
	 * all solution paths as well as a final summary table.
	 */
	public static void main(String argv[]) throws FileNotFoundException, IOException {

	// read all the puzzles in file named by first argument
    String filename;	
    
    try {
    	filename = argv[0];
    } catch(ArrayIndexOutOfBoundsException e) {
    	filename = "jams.txt";
    }
    
	Puzzle[] puzzles = Puzzle.readPuzzlesFromFile(filename);

		String[] heuristic_names = null;
		int num_puzzles = 5; // puzzles.length;
		int num_heuristics = 0;

		int[][] num_expanded = null;
		int[][] soln_depth = null;

		// run each heuristic on each puzzle
		for (int i = 0; i < num_puzzles; i++) {
			System.out.println("=================================================");
			System.out.println("puzzle = " + puzzles[i].getName());

			Heuristic[] heuristics = { // these are the heuristics to be used
					new ZeroHeuristic(puzzles[i]), new BlockingHeuristic(puzzles[i]),
					new AdvancedHeuristic(puzzles[i]), };

			if (i == 0) {
				num_heuristics = heuristics.length;
				num_expanded = new int[num_puzzles][num_heuristics];
				soln_depth = new int[num_puzzles][num_heuristics];

				heuristic_names = new String[num_heuristics];
				for (int h = 0; h < num_heuristics; h++)
					heuristic_names[h] = heuristics[h].getClass().getName();
			}

			for (int h = 0; h < num_heuristics; h++) {
				System.out.println();
				System.out.println("------------------------------------");
				System.out.println();
				System.out.println("heuristic = " + heuristic_names[h]);

				puzzles[i].resetSearchCount();
				AStar search = new AStar(puzzles[i], heuristics[h]);

				if (search.path == null) {
					System.out.println("NO SOLUTION FOUND.");
					soln_depth[i][h] = -1;
				} else {

					for (int j = 0; j < search.path.length; j++) {
						search.path[j].print();
						System.out.println();
					}

					num_expanded[i][h] = puzzles[i].getSearchCount();
					soln_depth[i][h] = search.path.length - 1;

					System.out.println("nodes expanded: " + num_expanded[i][h] + ", soln depth: " + soln_depth[i][h]);

				}
			}
		}

		// print the results in a table
		System.out.println();
		System.out.println();
		System.out.println();

		System.out.print("          ");
		for (int h = 0; h < num_heuristics; h++)
			System.out.print(" |    " + right_pad(heuristic_names[h], 18));
		System.out.println();

		System.out.print("name      ");
		for (int h = 0; h < num_heuristics; h++)
			System.out.print(" |    nodes dpth  br.fac");
		System.out.println();

		System.out.print("----------");
		for (int h = 0; h < num_heuristics; h++)
			System.out.print("-+----------------------");
		System.out.println();

		NumberFormat brfac_nf = new DecimalFormat("##0.000");

		for (int i = 0; i < num_puzzles; i++) {
			System.out.print(right_pad(puzzles[i].getName(), 10));

			for (int h = 0; h < num_heuristics; h++) {
				if (soln_depth[i][h] < 0) {
					System.out.print(" |  ** search failed ** ");
				} else {
					System.out.print(" | " + left_pad(Integer.toString(num_expanded[i][h]), 8) + " "
							+ left_pad(Integer.toString(soln_depth[i][h]), 4) + " " + left_pad(
									brfac_nf.format(BranchingFactor.compute(num_expanded[i][h], soln_depth[i][h])), 7));
				}
			}
			System.out.println();
		}
	}

	private static String left_pad(String s, int n) {
		for (int i = n - s.length(); i > 0; i--)
			s = " " + s;
		return s;
	}

	private static String right_pad(String s, int n) {
		for (int i = n - s.length(); i > 0; i--)
			s = s + " ";
		return s.substring(0, n);
	}

}
