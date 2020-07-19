/* 
 * Programming and Software Development COMP900041: Assignment 3 - Nim AI Play
 * This program is written by and the property of Andrew Naughton of University of Melbourne
 * ID: 910691, Username: naughtona, Last modified: 19/05/2020
 */


import java.util.Scanner;
import java.util.Arrays;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;


/* Nimsys controls the mechanics of Nim */
public class Nimsys {
    // publicly accessible constants **************************************************************
    public final static char NL = '\n'; // NL is an initialism for New Line

    // private (instance) constants ***************************************************************
    private final char YES = 'y';
    private final char CMD_PROMPT = '$';
    private final int MAX_SIZE = 100; // fixed upper bound on the size of 'players' array
    private final int TOP_TEN = 10;
    private final String DESC = "desc";
    private final String ASC = "asc";
    private final String[] SPECIFIED_COMMANDS = {"addplayer", "addaiplayer", "startadvancedgame", 
                                                "editplayer", "removeplayer", "displayplayer", 
                                                "resetstats", "rankings", "startgame", "exit"};

    // publicly accessible scanner ****************************************************************
    public static Scanner keyboard = new Scanner(System.in);

    // private (instance) variables ***************************************************************
    private NimPlayer[] players = new NimPlayer[MAX_SIZE];
    private int current_size = 0; // initially zero players


    // methods called via input pseudo command line ***********************************************
    private void addplayer(String username, String family_name, String given_name) {
        /* verify that the username provided would be unique in the current player list */
        if (playerExists(username) == -1) {
            /* username can be used -- create new NimHumanPlayer object in 'player' array */
            players[current_size++] = new NimHumanPlayer(username,family_name, given_name);
        } else {
            /* username cannot be used */
            System.out.println("The player already exists.");
        }
    }

    private void addaiplayer(String username, String family_name, String given_name) {
        /* verify that the username provided would be unique in the current player list */
        if (playerExists(username) == -1) {
            /* username can be used -- create new NimAIPlayer object in 'player' array */
            players[current_size++] = new NimAIPlayer(username, family_name, given_name);
        } else {
            /* username cannot be used */
            System.out.println("The player already exists.");
        }
    }

    private void displayplayer() {
        /* display all current players, sorted by descending alphabetical order */
        Arrays.sort(players, 0, current_size, NimPlayer::compareUsername);

        /* print player information -- one line per player */
        for (int i=0; i<current_size; i++) {
            players[i].printPlayerInfo();
        }
    }
    
    private void displayplayer(String username) {
        /* verify that username exists */
        int index = playerExists(username);

        if (index >= 0) {
            /* ok, we have found a match to the username provided */

            /* print player information for this player */
            players[index].printPlayerInfo();
        } else {
            /* oops, the username provided does not exist */
            System.out.println("The player does not exist.");
        }
    }

    private void removeplayer() {
        /* no username given -- check that this was the user's intention */
        System.out.println("Are you sure you want to remove all players? (y/n)");

        String response = keyboard.nextLine();

        /* if the response is not 'y' exactly, do not proceed */
        if ((response.length() == 1 && response.charAt(0) == YES)) {
            /* ok, we have the all clear -- remove all players */
            players = new NimPlayer[MAX_SIZE];
            current_size = 0;
        }
    }

    private void removeplayer(String username) {
        /* verify that username exists */
        int index = playerExists(username);

        if (index >= 0) {
            /* ok, we have found a match to the username provided */

            /* rearrange the 'players' array's objects to allow for the username's removal */
            for (int i=index; i < (current_size-1); i++) {
                players[i] = players[i+1];
            }
            /* the size of the 'players' array is now one object smaller */ 
            current_size--;
        } else {
            /* oops, the username provided does not exist */
            System.out.println("The player does not exist.");
        }
    }

    private void editplayer(String username, String family_name, String given_name) {
        /* verify that username exists */
        int index = playerExists(username);

        if (index >= 0) {
            /* ok, we have found a match to the username provided */

            /* check if the family name provided is different to their current family name */
            if (!(players[index].getFamilyName().equals(family_name))) {
                /* it is different -- let's set this to be their new family name */
                players[index].setFamilyName(family_name);
            }
            /* check if the given name provided is different to their current given name */
            if (!(players[index].getGivenName().equals(given_name))) {
                /* it is different -- let's set this to be their new given name */
                players[index].setGivenName(given_name);
            }
        } else {
            /* oops, the username provided does not exist */
            System.out.println("The player does not exist.");
        }
    }

    private void resetstats() {
        /* no username given -- check that this was the user's intention */
        System.out.println("Are you sure you want to reset all player statistics? (y/n)");

        String response = keyboard.nextLine();

        /* if the response is not 'y' exactly, do not proceed */
        if ((response.length() == 1 && response.charAt(0) == YES)) {
            /* ok, we have the all clear -- reset all players' game stats to zero */
            for (int i=0; i<current_size; i++) {
                players[i].setGamesPlayed(0);
                players[i].setGamesWon(0);
            }
        }
    }

    private void resetstats(String username) {
        /* verify that username exists */
        int index = playerExists(username);

        /* ok, a username has indeed been provided */
        if (index >= 0) {
            /* ok, we have found a match to the username provided */

            /* reset games played and games won to both equal zero */
            players[index].setGamesPlayed(0);
            players[index].setGamesWon(0);
        } else {
            /* oops, the username provided does not exist */
            System.out.println("The player does not exist.");
        }
    }

    private void rankings() {
        /* default: sort 'players' array in descending win percentage order */
        Arrays.sort(players, 0, current_size, NimPlayer::compareDscWinPct);

        /* print a maximum of 10 players' information */
        for (int i=0; i<current_size && i<TOP_TEN; i++) {
            System.out.println(players[i].playerRankInfo());
        }
    }

    private void rankings(String order) {
        if (order.equals(ASC)) {
            /* sort 'players' array in ascending win percentage order */
            Arrays.sort(players, 0, current_size, NimPlayer::compareAscWinPct);
        } else {
            /* sort 'players' array in descending win percentage order */
            Arrays.sort(players, 0, current_size, NimPlayer::compareDscWinPct);
        }
        /* print a maximum of 10 players' information, ranked in <asc|desc> order */
        for (int i=0; i<current_size && i<TOP_TEN; i++) {
            System.out.println(players[i].playerRankInfo());
        }
    }

    private void startgame(String initial_n, String u_bound, String u_name1, String u_name2) {
        /* verify that both usernames exist */
        int i = playerExists(u_name1); // index i for player1
        int j = playerExists(u_name2); // index j for player2

        if (i >= 0 && j >= 0) {
            /* ok, we have found a match to both usernames provided */

            /* initial stones and upperbound should be of int type */
            int initial = Integer.parseInt(initial_n);
            int upperbound = Integer.parseInt(u_bound);

            /* NimStandardGame instance with user's game parameters per 'gameparams' */
            NimGame new_game = new NimStandardGame(initial,upperbound, players[i], players[j]);

            /* print pre-game information */
            new_game.printGameParams();

            /* play game and let the winner be assigned to 'victor' */
            NimPlayer victor = new_game.play();

            /* print winning declaration */
            new_game.printVictoryStatement(victor.getFullName());

            /* update both players' game statistics */
            updatePlayerStats(victor.getUsername(), players[i], players[j]);
        } else {
            /* oops, at least one of the usernames provided does not exist */
            System.out.println("One of the players does not exist.");
        }
    }

    private void startadvancedgame(String initial_n, String username1, String username2) {
        /* verify that both usernames exist */
        int i = playerExists(username1); // index i for player1
        int j = playerExists(username2); // index j for player2

        if (i >= 0 && j >= 0) {
            /* ok, we have found a match to both usernames provided */

            /* initial stones and upperbound should be of int type */
            int initial = Integer.parseInt(initial_n);

            /* NimAdvancedGame instance with user's game parameters per 'gameparams' */
            NimGame new_game = new NimAdvancedGame(initial,players[i],players[j]);

            /* print pre-game information */
            new_game.printGameParams();

            /* play game and let the winner be assigned to 'victor' */
            NimPlayer victor = new_game.play();

            /* print winning declaration */
            new_game.printVictoryStatement(victor.getFullName());

            /* update both players' game statistics */
            updatePlayerStats(victor.getUsername(), players[i], players[j]);
        } else {
            /* oops, at least one of the usernames provided does not exist */
            System.out.println("One of the players does not exist.");
        }
    }

    private void exit() {
        /* safely close scanner */
        keyboard.close();

        /* save current player records to disk (player_data.dat) */
        writePlayerStatsFile();

        System.out.print(NL);
        System.exit(0);
    }


    // non-specified commands methods (helpers) ***************************************************
    private void updatePlayerStats(String victor, NimPlayer player1, NimPlayer player2) {
        /* update the games won statistic for the victor */
        if (player1.getUsername().equals(victor)) {
            player1.setGamesWon(player1.getGamesWon() + 1);
        } else { // no draws permitted
            player2.setGamesWon(player2.getGamesWon() + 1);
        }
        /* the games played statistic is adjusted for both the victor and the vanquished */ 
        player1.setGamesPlayed(player1.getGamesPlayed() + 1);
        player2.setGamesPlayed(player2.getGamesPlayed() + 1);
    }

    private int playerExists(String username) {
        /* try to match the username provided to each of the current players */
        for (int i=0; i<current_size; i++) {
            if (players[i].getUsername().equals(username)) {
                /* cool! we have found a match -- return its index in the 'players' array */
                return i;
            }
        }
        /* oops, the username provided does not exist */
        return -1;
    }

    private void readCommand(String[] input) {
        /* user input is either 0, 1 or 2 space-separated strings */

        /* if provided, the first space-delimited token will be a method name to be called */
        String user_command = input[0]; // e.g. displayplayer

        /* if provided, the second space-delimited token will be the user's method's arguments */
        Object[] user_args = null;
        if (input.length > 1) {
            /* if there is more than one argument, they will be comma-separated */
            user_args = input[1].split(",");
        }
        /* ensure the input, including the command and its arguments, is valid */
        validateInput(user_command, user_args);
    }

    private void validateInput(String user_command, Object[] user_args) {
        try {
            /* initially assume the command and, hence the input, is invalid */
            boolean invalid_command = true, valid_input = false;

            /* pull out each method in Nimsys */
            for (Method m : this.getClass().getDeclaredMethods()) {
                /* we are only interested in matching with the specified commands (per spec.) */
                if (Arrays.asList(SPECIFIED_COMMANDS).contains(m.getName()) &&
                                            m.getName().equals(user_command)) {
                    /* ok, the command is not invalid */
                    invalid_command = false;

                    /* method parameter count must match argument count from the input */
                    if (user_args == null) { // null != 0 -- special case
                        if (m.getParameterCount() == 0) {
                            /* ok, the input is valid -- call the method! */
                            valid_input = true;
                            m.invoke(this,user_args);
                        }
                    } else if (m.getParameterCount() == user_args.length) {
                        /* ok, the input is valid -- call the method! */
                        valid_input = true;
                        m.invoke(this,user_args);
                    }
                }
            }
            if (!valid_input) {
                if (invalid_command) { // invalid input caused by invalid command
                    throw new Exception( "'" + user_command + "'" + " is not a valid command.");
                } else { // invalid input caused by invalid argument count!
                    throw new Exception("Incorrect number of arguments supplied to command.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void writePlayerStatsFile() {
        try {
            /* code inspired by Java Absolute 6th Edition */
            ObjectOutputStream outputStream =   new ObjectOutputStream(
                                                new FileOutputStream("player_data.dat"));
            /* write each current player's information to binary file */
            for (int i = 0; i < current_size; i++) {
                outputStream.writeObject(players[i]);
            }
            outputStream.close();
        } catch (IOException e) {/*do nothing*/}
    }

    private void readPlayerStatsFile() {
        try {
            /* code inspired by Java Absolute 6th Edition */
            ObjectInputStream inputStream = new ObjectInputStream(
                                            new FileInputStream("player_data.dat"));
            /* read in the player information saved in player_data.dat binary file (if any) */
            try {
                while (true) {
                    NimPlayer next = (NimPlayer)inputStream.readObject();
                    if (next != null) {
                        players[current_size++] = next;
                    }
                }
            } catch (EOFException e) {/*do nothing*/}
        } catch (Exception e) {/*do nothing*/}
    }


    // main method for program ********************************************************************
    public static void main(String[] args) {
        /* create a new instance of Nimsys */
        Nimsys control = new Nimsys();

        /* read and transfer player records in player_data.dat to 'players' array */
        control.readPlayerStatsFile();

        /* print out greeting */
        System.out.println("Welcome to Nim" + NL);

        /* enable a continuous cycle of receiving and acting on user commands */
        while (true) {
            System.out.print(control.CMD_PROMPT);

            /* read and interpret the user's command */
            control.readCommand(keyboard.nextLine().split(" "));
            
            System.out.print(NL);
        }
    }
}
