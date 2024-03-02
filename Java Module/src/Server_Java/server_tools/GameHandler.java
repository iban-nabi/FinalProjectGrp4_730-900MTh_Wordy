package Server_Java.server_tools;

import CORBA_IDL.InvalidWordException;
import CORBA_IDL.NoWordException;
import CORBA_IDL.ShortWordException;
import CORBA_IDL.WordRepeatedException;
import Server_Java.server_objects.Game;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameHandler implements Serializable {
    private List<String> wordList;
    private List<Game> gameList = new ArrayList<>();
    private List<String>onlineUsers = new ArrayList<>();

    public GameHandler() throws IOException {
        WordReader wordReader = new WordReader();
        wordList = wordReader.populateWordList();
    }

    /**
     * This method adds the username of a player in a list of online users, this method will be used in the Servant
     * class, login method
     * @param username of a player
     */
    public void addOnlineUsers(String username){
        onlineUsers.add(username);
    }

    /**
     * This method removes the username of a player in the list of online users, this method will be used in the Servant
     * class, logout method
     * @param username of a specific player
     */
    public void removeOnlineUsers(String username){
        onlineUsers.remove(username);
    }

    /**
     * This method will return the list of online users to be used in the login method of the Servant class, to verify
     * if the user has already logged in
     * @return list of online users
     */
    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    /**
     * This method returns a specfic game object which will depend on the game id parameter, this will be used in
     * the Servant class to allow players to access the game they are currently in
     * @param gameID of a specific game
     * @return game of object depending on the game id
     */
    public Game viewGame(int gameID){
        return gameList.get(gameID);
    }

    /**
     * This method will allow the servant and the game object to communicate, which will allow players to send a word for
     * that specfic game
     * @param gameID of a specfic game
     * @param username of player
     * @param word entered by a player
     * @throws WordRepeatedException if word is already entered by other players
     * @throws ShortWordException if word length is less than 5
     * @throws NoWordException if word is not in the word.txt file
     * @throws InvalidWordException if the word created does not match the 17 characters to be used in a specfic game round
     */
    public void sendWordToGame(int gameID, String username, String word) throws WordRepeatedException, ShortWordException, NoWordException, InvalidWordException {
        gameList.get(gameID).enterWord(username+":"+word);
    }

    /**
     * This method will allow players to quit a game, this method will be used in the servant class, quit game method
     * @param gameID of a specific game
     * @param username of a player
     */
    public void quitCurrentGame(int gameID, String username){
        gameList.get(gameID).quitGame(username);
    }

    /**
     * This method will either create a new game or let a player join an existing game, this method will be used by the
     * servant class, join game method
     * @param player
     * @return Game object
     */
    public Game getGame(String player){
        if(gameList.size()!=0){
            for(Game g : gameList){
                if(!g.isGameClosed()){
                    g.addPlayer(player);
                    return g;
                }
            }
            gameList.add(new Game(player,gameList.size(),wordList));
            return gameList.get(gameList.size()-1);
        }else{
            gameList.add(new Game(player,gameList.size(),wordList));
            return gameList.get(0);
        }
    }
}
