/* 
 * Programming and Software Development COMP900041: Assignment 3 - Nim AI Play
 * This program is written by and the property of Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
 */


/* NimAdvancedGame is a derived class of NimGame, facilitating the playing aspect of the advanced
 * game (per spec.)
 */
public class NimAdvancedGame extends NimGame {
    // private (instance) constants ***************************************************************
    private final String LEFT_ARROW = "<";
    private final String RIGHT_ARROW = ">";
    private final String COMMA = ",";
    private final String X = "x";
    private final String INVALID = "Invalid move.";
    private final int MAX_REMOVAL = 2; // can remove at most two stones in a single move
    private final int MIN_REMOVAL = 1; // must remove at least one stone in a single move
    private final int N_INTEGERS = 2; // move must contain two space-separated integers

    // private (instance) variables ***************************************************************
    private int stones_left; // buddy variable for # stones left
    private boolean[] available; // game state variable
    

    // constructor method *************************************************************************
    public NimAdvancedGame(int initial, NimPlayer plyr1, NimPlayer plyr2) {
        super(initial, plyr1, plyr2);
        stones_left = initial; // initially equal to the starting stone count

        /* initialise 'available' to a row of trues */
        available = new boolean[initial];
        for (int i = 0; i < stone_count; i++) {
            available[i] = true;
        }
    }

    // main methods *******************************************************************************
    /* print pre-game info */
    public void printGameParams() {
        System.out.println(Nimsys.NL + "Initial stone count: "   + this.stone_count             + 
                           Nimsys.NL + "Stones display: "        + stone_display()              +
                           Nimsys.NL + "Player 1: "              + this.plyr1.getFullName()     +
                           Nimsys.NL + "Player 2: "              + this.plyr2.getFullName()     +
                           Nimsys.NL                                                            );
    }

    /* method for advancedgame game play */
    public NimPlayer play() {
        int turns = 0;
        String move, lastmove="";

        /* only stop when int stone_count reaches zero */
        while (stones_left > 0) {
            System.out.println(stones_left + " stones left: " + stone_display());
            
            try {
                move = turns % N_PLYRS == 0 ? plyr1.advancedMove(available, lastmove)
                                            : plyr2.advancedMove(available, lastmove);
                /* ensure the validity of the move */
                move = validateMove(move);

                if (move.equals(INVALID)) {
                    /* oops, invalid -- throw exception */
                    throw new Exception(INVALID);
                } else {
                    /* ok, we have a valid move -- update game state */
                    make_move(move.split(" "));
                    lastmove = move;
                    turns++;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print(Nimsys.NL);
            }
        }
        /* player who moves last wins! */
        return turns % N_PLYRS != 0 ? plyr1 : plyr2;
    }

    /* update game state with valid move */
    private void make_move(String[] move) {
        /* pull out the start index and the number to remove */
        int start_i = Integer.parseInt(move[0]);
        int to_remove = Integer.parseInt(move[1]);

        /* update game state accordingly */
        for (int i = 0; i < to_remove; i++) {
            available[start_i - 1 + i] = false;
        }
        /* decrement buddy variable stones left by to_remove */
        stones_left -= to_remove;
    }

    /* returns a well-formatted String representing the game state */
    private String stone_display() {
        String display = "";

        for (int i = 0; i < stone_count; i++) {
            if (available[i]) { // STAR denotes stone
                display += LEFT_ARROW + (i+1) + COMMA + STAR + RIGHT_ARROW;
            } else { // X denotes no stone
                display += LEFT_ARROW + (i+1) + COMMA + X + RIGHT_ARROW;
            }
            if (i != stone_count - 1) { // add a space, unless we are at end of loop
                display += SPACE;
            }
        }
        return display;
    }

    /* returns invalid move message if invalid, otherwise the move */
    public String validateMove(String move) {
        String[] move_split = move.split(" ");
        try {
            /* move must contain two space-separated integers */
            if (move_split.length == N_INTEGERS) {
                int start_i = Integer.parseInt(move_split[0]);
                int to_remove = Integer.parseInt(move_split[1]);
                
                /* removal check -- must be between 1 and 2 stones */
                if (to_remove < MIN_REMOVAL || to_remove > MAX_REMOVAL) {
                    throw new Exception();
                }
                /* index check -- there are stones to remove at those indices */
                for (int i = 0; i < to_remove; i++) {
                    if (!available[start_i - 1 + i]) {
                        throw new Exception();
                    }
                }
                /* ok, move is valid! */
                return move;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return INVALID;
        }
    }

    // setter and getter methods ******************************************************************
    public int getStonesLeft() {
        return this.stones_left;
    }

    public boolean[] getAvailable() {
        return available.clone();
    }

    public void setStonesLeft(int stones_left) {
        this.stones_left = stones_left;
    }

    public void setAvailable(boolean[] available) {
        this.available = available.clone();
    }
}
