/* 
 * Programming and Software Development COMP900041: Assignment 3 - Nim AI Play
 * This program is written by and the property of Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
 */


/* NimGame is an abstract and base class, facilitating the playing aspect of <Standard|Advanced> 
 * version of NimGame
 */
public abstract class NimGame {
    // protected constants ************************************************************************
    protected final String STAR = "*";
    protected final String SPACE = " ";
    protected final int N_PLYRS = 2; // PLAYERS abbreviated in the interest of 100 char limit

    // protected variables ************************************************************************
    protected int stone_count;
    protected NimPlayer plyr1; // players abbreviated in the interest of 100 char limit
    protected NimPlayer plyr2; // as above

    // abstract method signatures *****************************************************************
    public abstract void printGameParams(); // print pre-game info
    public abstract NimPlayer play(); // game play method


    // constructor method *************************************************************************
    public NimGame(int initial_stones, NimPlayer plyr1, NimPlayer plyr2) {
        this.stone_count = initial_stones;
        setplyr1(plyr1);
        setplyr2(plyr2);
    }

    // other methods ******************************************************************************
    /* game over and winner declarations are done here */
    public void printVictoryStatement(String victor) {
        System.out.println("Game Over" + Nimsys.NL + victor + " wins!");
    }

    // setter and getter methods ******************************************************************
    public int getStonecount() {
        return this.stone_count;
    }

    public NimPlayer getplyr1() {
        return this.plyr1.getClass() == NimHumanPlayer.class ? new NimHumanPlayer(this.plyr1)
                                                             : new NimAIPlayer(this.plyr1);
    }

    public NimPlayer getplyr2() {
        return this.plyr2.getClass() == NimHumanPlayer.class ? new NimHumanPlayer(this.plyr2)
                                                             : new NimAIPlayer(this.plyr2);
    }

    public void setStonecount(int stonecount) {
        this.stone_count = stonecount;
    }

    public void setplyr1(NimPlayer plyr1) {
        this.plyr1 = plyr1.getClass() == NimHumanPlayer.class ? new NimHumanPlayer(plyr1)
                                                              : new NimAIPlayer(plyr1);
    }

    public void setplyr2(NimPlayer plyr2) {
        this.plyr2 = plyr2.getClass() == NimHumanPlayer.class ? new NimHumanPlayer(plyr2)
                                                              : new NimAIPlayer(plyr2);
    }
}
