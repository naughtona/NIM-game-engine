/*
 * NimAIPlayer.java
 *
 * This class is provided as a skeleton code for the tasks of 
 * Sections 2.4, 2.5 and 2.6 in Project C. Add code (do NOT delete any) to it
 * to finish the tasks. 
 * 
 * This class has been added to by Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
*/


import java.lang.Math;


/* NimAIPlayer is a derived class of NimPlayer, managing AI player specific information */
public class NimAIPlayer extends NimPlayer {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.	

	// private (instance) constants ***************************************************************
	private final int MAX_REMOVAL = 2; // can remove at most two stones in a single move
	

	// constructor methods ************************************************************************
	/* no argument provided constructor method */
	public NimAIPlayer() {
		super();
	}
	
	/* copy constructor method */
	public NimAIPlayer(NimPlayer nimplayer) {
		super(nimplayer);
	}

	/* main constructor method */
	public NimAIPlayer(String username, String family_name, String given_name) {
		super(username, family_name, given_name);
	}

	// main methods *******************************************************************************
	/* Standard Game method : 
	 * returns how many stones the AI would like to remove according to its strategy */
	public int removeStone(int stonecount, int upperbound) {
		System.out.println(getGivenName() + "'s turn - remove how many?");
		System.out.print(Nimsys.NL);

		int M = Math.min(upperbound, stonecount); // "max removal at any time"
		int k_mod = (stonecount - 1) % (M + 1); // per formula in spec.

		if (k_mod != 0) { 
			/* winnable position -- sustain lead by keeping the opponent to k_mod == 0 */
			return k_mod;
		} 
		/* else -- currently unwinnable position -- remove random (bounded) number of stones */
		return (int) (Math.random() * M + 1); 
	}
	
	/* Advanced Game method : 
	 * return the move the AI would like to make according to its strategy */
	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		System.out.println(getGivenName() + "'s turn - which to remove?");
		System.out.print(Nimsys.NL);

		String move = "";
		int i = 0, j, initial_stones = available.length;

		/* expand the game state to see possible next moves */
		while (i < initial_stones) {
			j = 0;

			/* 1. can only move one or (at most) two stones
			 * 2. must stay within the index bounds of the 'available' array
			 * 3. can only remove if there is currently a stone (available[i+j] == true) */
			while (j < MAX_REMOVAL && (i+j) < initial_stones && available[i+j]) {
				move = String.format("%d %d", i+1, j+1);

				if (isWinnable(available.clone(), initial_stones, i, j+1)) {
					/* ok, we are in a winnable position -- return move! */
					return move;
				}
				j++;
			}
			i++;
		}
		/* dang, we cannot enforce a win given the current game state */
		return move;
	}

	// other (helper) methods *********************************************************************
	/* returns true if, by moving to_remove stones starting at index start_i, we can enforce a 
	 * losing condition on the rival in the next ply */
	private boolean isWinnable(boolean[] available, int initial, int start_i, int to_remove) {
		/* model what the game state ('available') would look like */
		for (int i = 0; i < to_remove; i++) {
			available[start_i + i] = false;
		}

		/* a cluster will be a set of consecutive trues in 'available' */
		int[] clusters = new int[initial]; // strictly speaking initial_stones/2 + 1
		/* curr_n is a buddy variable for the interesting part of clusters */
		int curr_n = 0, j = 0;

		/* only stop when we have reached the end of 'available' (game state) */
		while (j < initial) {
			int k = j, cluster_size;

			/* find consecutive trues and do not go off the edge of 'available' */
			while (k < initial && available[k]) {
				k++;
			}
			cluster_size = k - j; // index difference

			if (cluster_size > 0) {
				clusters[curr_n++] = cluster_size;
			}
			j = k + 1; // maintain efficient code!
		}

		int XOR_product = find_XOR_product(clusters, curr_n);

		/* if XOR product is 0 -- we can enforce a losing condition on our rival! */
		return XOR_product == 0;
	}

	/* calculates the result of XOR'ing each cluster's size cumulatively */
	private int find_XOR_product(int[] clusters, int curr_n) {
		int XOR_product = -1;

		/* number of clusters must be even (incl. 0)! */
		if (curr_n % 2 == 0) {
			/* perform bitwise operation XOR on each size of each cluster found */
			XOR_product = 0;
			for (int i = 0; i < curr_n; i++) {
				XOR_product ^= clusters[i];
			}
		}
		return XOR_product;
	}
}
