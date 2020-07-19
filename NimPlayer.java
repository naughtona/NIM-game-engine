/* 
 * Programming and Software Development COMP900041: Assignment 3 - Nim AI Play
 * This program is written by and the property of Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
 */


import java.io.Serializable;
import java.lang.Math;


/* NimPlayer is an abstract and base class, facilitating <AI|Human> player record maintenance */
public abstract class NimPlayer implements Serializable {
    // private (instance) constants ***************************************************************
    private final String PCT_MARK = "%";
    private final String COMMA = ",";
    private final int SCALAR = 100;

    // private (instance) variables ***************************************************************
    private String username;
    private String family_name;
    private String given_name;
    private int games_played;
    private int games_won;

    // abstract method signatures *****************************************************************
    public abstract int removeStone(int stonecount, int upperbound); // standard game
    public abstract String advancedMove(boolean[] available, String lastmove); // advanced game
    
    
    // constructor methods ************************************************************************
    /* no argument provided constructor method */
    public NimPlayer() {
        this.username = "";
    }

    /* copy constructor method */
    public NimPlayer(NimPlayer nimplayer) {
        this.username = nimplayer.username;
        this.family_name = nimplayer.family_name;
        this.given_name = nimplayer.given_name;
        this.games_played = nimplayer.games_played;
        this.games_won = nimplayer.games_won;
    }

    /* main constructor method */
    public NimPlayer(String username, String family_name, String given_name) {
        this.username = username;
        this.family_name = family_name;
        this.given_name = given_name;
        this.games_played = 0;
        this.games_won = 0;
    }

    
    // other methods ******************************************************************************
    public void printPlayerInfo() {
        System.out.println( this.username       + COMMA     + 
                            this.given_name     + COMMA     + 
                            this.family_name    + COMMA     +
                            this.games_played   + " games," + 
                            this.games_won      + " wins"  );
    }

    public String playerRankInfo() {
        String pct = Math.round(getWinPct()) + PCT_MARK;
        return String.format("%-5s| %02d games | %s %s",pct,games_played,given_name,family_name);
    }

    /* comparison method for alphabetic comparison */
    public int compareUsername(NimPlayer other) {
        return username.compareTo(other.username);
    }

    /* comparison method for descending win percentage comparison */
    public int compareDscWinPct(NimPlayer other) {
        int winpct_comp = Float.compare(other.getWinPct(),getWinPct());

        if (winpct_comp != 0) {
            /* ok, their win percentages are not equal -- can stop here */
            return winpct_comp;
        }
        /* ok, we have a tie -- alphabetic comparison of their names is needed */
        return compareUsername(other);
    }

    /* comparison method for ascending win percentage comparison */
    public int compareAscWinPct(NimPlayer other) {
        int winpct_comp = Float.compare(getWinPct(),other.getWinPct());

        if (winpct_comp != 0) {
            /* ok, their win percentages are not equal -- can stop here */            
            return winpct_comp;
        }
        /* ok, we have a tie -- alphabetic comparison of their names is needed */
        return compareUsername(other);
    }

    /* returns player's win percentage */
    private float getWinPct() {
        if (games_played != 0) {
            return SCALAR * (float)games_won / games_played;
        } else {
            return 0.0f;
        }
    }


    // setter and getter methods ******************************************************************
    public String getFullName() {
        return this.given_name + " " + this.family_name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getFamilyName() {
        return this.family_name;
    }

    public String getGivenName() {
        return this.given_name;
    }

    public int getGamesPlayed() {
        return this.games_played;
    }

    public int getGamesWon() {
        return this.games_won;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setFamilyName(String family_name) {
        this.family_name = family_name;
    }

    public void setGivenName(String given_name) {
        this.given_name = given_name;
    }

    public void setGamesPlayed(int games_played) {
        this.games_played = games_played;
    }

    public void setGamesWon(int games_won) {
        this.games_won = games_won;
    }
}
