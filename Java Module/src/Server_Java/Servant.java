package Server_Java;


import CORBA_IDL.*;
import CORBA_IDL.UserNotFoundException;
import CORBA_IDL.WrongCredentials;
import Server_Java.server_objects.Player;
import Server_Java.server_objects.Game;
import Server_Java.server_tools.GameHandler;
import Server_Java.server_tools.LeaderboardsManager;
import Server_Java.server_tools.LoginManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Servant extends GameActionsPOA {
    GameHandler gameHandler;
    public Servant() throws IOException {
        gameHandler = new GameHandler();
    }

    /**
     * The login method has the following functions:
     *      | It checks whether the entered username and password is empty
     *      | It checks if the password is invalid but the username exists
     *      | It checks whether the user is already online
     *
     * @param username entered by the user.
     * @param password entered by the user
     * @throws NullPointerException - if the value is trying to store a null value
     * @throws UserNotFoundException - if the user is not found in the database
     * @throws WrongCredentials - if the user exists but has entered the wrong credentials
     * @throws UserOnlineException - if the user is already online
     */
    @Override
    public void login(String username, String password) throws NullPointerException, UserNotFoundException, WrongCredentials, UserOnlineException{
        LoginManager loginManager = new LoginManager();
        Player player = null;
        if (username.equals("") || password.equals("")){
            throw new NullPointerException("The username/password cannot be empty");
        }
        if(loginManager.isUsernameExisting(username)){
            try {
                player = loginManager.authenticateAccount(username, password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();

            }
            if (player == null) {
                throw new WrongCredentials("Invalid Username/Password");
            }
        } else {
            throw new UserNotFoundException("User does not exists!");
        }

        if(gameHandler.getOnlineUsers().contains(username)){
            throw new UserOnlineException("User is already online");
        }
        gameHandler.addOnlineUsers(username);
    }

    /**
     * This method allows the user to join an existing game or create a new one by utilizing the GameHandler's getGame
     * @param username of a specfic player
     * @return Game ID of a specfic game
     */
    @Override
    public int joinGame(String username) {
        Game game = null;
        game = gameHandler.getGame(username);
        game.joinGame();
        return game.getGameID();
    }

    /**
     * This method will retrieve the current timer for waiting of other players to join a specfic game
     * @param gameID of a specific game
     * @return current timer when the players are waiting for other players to join
     */
    @Override
    public int getTimer(int gameID) {
        Game game = gameHandler.viewGame(gameID);
        return game.getTimer();
    }

    /**
     * This method will retrieve the name of all players in a single game
     * @param gameID of a specfic game
     * @return string array of players of a specific game
     */
    @Override
    public String[] getGamePlayers(int gameID) {
        Game game = gameHandler.viewGame(gameID);
        return game.getPlayers();
    }

    /**
     * This method allows all users of the a specfic game to be concurrent with each other by utilizing the Game objects' initializeGame
     * @param gameID of a specfic game
     */
    @Override
    public void initializeGame(int gameID) {
        Game game = gameHandler.viewGame(gameID);
        game.initializeGame();
    }

    /**
     * This method retrieves a boolean value to let the players know that the game round has already ended
     * @param gameID of a specific game
     * @return boolean value that identifies that the game round has ended
     */
    @Override
    public boolean runGame(int gameID) {
        Game currentGame = gameHandler.viewGame(gameID);
        try {
            return currentGame.runGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * This method allows the players to a word into a specific Game object by accessing it's sendWordToGame method
     * @param gameID of a specific game
     * @param username of a specfic player
     * @param word entered by a player during playing the game
     * @throws NoWordException if the word entered is not found in the Word.txt file
     * @throws ShortWordException if the word entered is has a length lesser than 5
     * @throws WordRepeatedException if the word was already entered by other users
     * @throws InvalidWordException if the word created does not match the given characters to be used by players
     */
    @Override
    public void sendWord(int gameID, String username, String word) throws NoWordException, ShortWordException,
            WordRepeatedException, InvalidWordException {
        gameHandler.sendWordToGame(gameID, username, word.toLowerCase());
    }

    /**
     * This method retrieves the generated array of characters of a specific Game object by accessing the getLetterList
     * @param gameID of a specific game
     * @return array of characters to be used in a specific game
     */
    @Override
    public char[] getCharacters(int gameID) {
        Game currentGame = gameHandler.viewGame(gameID);
        char[] characterList = new char[currentGame.getLetterList().size()];

        int i=0;
        for(char c : currentGame.getLetterList()){
            characterList[i]=c;
            i++;
        }
        return characterList;
    }

    /**
     * This method retrieves an array of entered words by players in a specific game by accesing the Game object's getPlayerInputs
     * @param gameID of a specific game
     * @return string array which stores all the entered words by all players in a specific game
     */
    @Override
    public String[] getEnteredWords(int gameID) {
        Game currentGame = gameHandler.viewGame(gameID);
        if(currentGame.getPlayerInputs()==null || currentGame.getPlayerInputs().size()==0){
            return new String[]{" "};
        }else{
            return currentGame.getPlayerInputs().toArray(new String[0]);
        }
    }

    /**
     * This method retrieves the round winner of a specific game by accessing the Game objects' getRoundWinner
     * @param gameID of a specific game
     * @return username of the winner
     */
    @Override
    public String getRoundWinner(int gameID) {
        Game currentGame = gameHandler.viewGame(gameID);
        return currentGame.getRoundWinner();
    }

    /**
     * This method retrieves the game winner of a specific game by accessing the Game objects' getGameWinner
     * @param gameID of a specific game
     * @return username of the winner
     */
    @Override
    public String getGameWinner(int gameID) {
        Game currentGame = gameHandler.viewGame(gameID);
        return currentGame.getGameWinner();
    }

    /**
     * This method will retrieve the number of games won by a user in a specific game by accessing the Game objects' getMyNoOfWins
     * @param gameID of a specfic game
     * @param username of the player
     * @return no of wins of a specific player
     */
    @Override
    public int getNoWinInGame(int gameID, String username) {
        Game currentGame = gameHandler.viewGame(gameID);
        return currentGame.getMyNoOfWins(username);
    }


    /**
     * This method calls the getLeaderboardsByLongestWord() method inside the LeaderboardsManager class
     * to get the Leaderboards for the Longest Words.
     * @return a 2D array containing the Leaderboards.
     */
    @Override
    public String[][] getLeaderBoardLongestWordsList() {
        LeaderboardsManager leaderboardsManager = new LeaderboardsManager();
        return leaderboardsManager.getLeaderboardsByLongestWord();
    }

    /**
     * This method calls the getLeaderboardsByWins() method inside the LeaderboardsManager class
     * to get the Leaderboards for the Number of Wins.
     * @return a 2D array containg the Leaderboards.
     */
    @Override
    public String[][] getLeaderboardWinList() {
        LeaderboardsManager leaderboardsManager = new LeaderboardsManager();
        return leaderboardsManager.getLeaderboardsByWins();
    }

    /**
     * This method removes the a specific player in a specfic game if the player chooses to quit the current game
     * @param gameID of a specfic game
     * @param username of the player
     */
    @Override
    public void quitGame(int gameID, String username) {
        gameHandler.quitCurrentGame(gameID,username);
    }

    /**
     * This method will logout a username by removing them in a List of online users which is found within the gameHandler
     * @param username of the player
     */
    @Override
    public void logout(String username) {
        gameHandler.removeOnlineUsers(username);
    }

}
