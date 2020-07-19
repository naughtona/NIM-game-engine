/* 
 * Programming and Software Development COMP900041: Assignment 3 - Nim AI Play
 * This program is written by and the property of Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
 */

import java.lang.Math;

/* NimStandardGame is a derived class of NimGame, facilitating the playing aspect of the standard
 * game 
 */
public class NimStandardGame extends NimGame {
    // private (instance) variable ****************************************************************
    private int upper_bound;
    

    // constructor method *************************************************************************
    public NimStandardGame(int initial, int upper_bound, NimPlayer plyr1, NimPlayer plyr2) {
        super(initial, plyr1, plyr2);
        this.upper_bound = upper_bound;
    }

    // main methods *******************************************************************************
    /* print pre-game info */
    public void printGameParams() {
        System.out.println(Nimsys.NL + "Initial stone count: "   + this.stone_count             + 
                           Nimsys.NL + "Maximum stone removal: " + this.upper_bound             +
                           Nimsys.NL + "Player 1: "              + this.plyr1.getFullName()     +
                           Nimsys.NL + "Player 2: "              + this.plyr2.getFullName()     +
                           Nimsys.NL                                                            );
    }

    /* game is played within this method */
    public NimPlayer play() {
        int turns = 0, to_remove;

        /* only stop when int stone_count reaches zero */
        while (stone_count > 0) {
            System.out.println(stone_count + " stones left: " + stone_display());

            int max_removal = Math.min(stone_count, upper_bound);
            try {
                to_remove = turns % N_PLYRS == 0 ? plyr1.removeStone(stone_count, upper_bound)
                                                 : plyr2.removeStone(stone_count, upper_bound);
                /* ensure the validity of the move */
                if (to_remove >= 1 && to_remove <= max_removal) {
                    /* ok, we may proceed */
                    stone_count -= to_remove;
                    turns++;
                } else {
                    /* oops, removal invalid -- throw exception */
                    throw new Exception();
                }
            } catch (Exception e) {
                /* print invalid move message */
                System.out.println("Invalid move. You must remove between 1 and "    + 
                                                max_removal + " stones." +  Nimsys.NL);
            }
        }
        /* the player whose turn would be now wins! */
        return turns % N_PLYRS == 0 ? plyr1 : plyr2;
    }

    /* returns a well-formatted String representing the game state */
    private String stone_display() {
        String display = "";

        for (int i=0; i < stone_count; i++) {
            if (i==stone_count-1) {
                display += STAR; // do not add space if we are at end of loop
                break;
            }
            // else
            display += STAR + SPACE;
        }
        return display;
    }

    // setter and getter methods ******************************************************************
    public int getUpperbound() {
        return this.upper_bound;
    }

    public void setUpperbound(int upperbound) {
        this.upper_bound = upperbound;
    }
}
