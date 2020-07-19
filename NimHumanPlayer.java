/* 
 * Programming and Software Development COMP900041: Assignment 3 - Nim AI Play
 * This program is written by and the property of Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
 */


/* NimHumanPlayer is a derived class of NimPlayer, managing Human player specific information */
public class NimHumanPlayer extends NimPlayer {
    // constructor methods ************************************************************************
    /* no-argument provided constructor method */
    public NimHumanPlayer() {
        super();
    }

    /* copy constructor method */
    public NimHumanPlayer(NimPlayer nimplayer) {
        super(nimplayer);
    }
    
    /* main constructor method */
    public NimHumanPlayer(String username, String family_name, String given_name) {
        super(username, family_name, given_name);
    }

    // main methods *******************************************************************************
    /* Standard Game method :
     * returns how many stones the user would like to remove */
    public int removeStone(int stonecount, int upperbound) {
        System.out.println(getGivenName() + "'s turn - remove how many?");

        String input = Nimsys.keyboard.nextLine();

        System.out.print(Nimsys.NL);

        /* try convert to int, else refer to catch in NimGame */
        int n = Integer.parseInt(input);
        return n;
    }

    /* Advanced Game method :
     * returns the move the user would like to make */
    public String advancedMove(boolean[] available, String lastmove) {
        System.out.println(getGivenName() + "'s turn - which to remove?");

        String input = Nimsys.keyboard.nextLine();
        
        System.out.print(Nimsys.NL);
        return input;
    }
}
