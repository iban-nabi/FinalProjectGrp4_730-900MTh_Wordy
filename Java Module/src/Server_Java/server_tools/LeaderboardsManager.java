package Server_Java.server_tools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class LeaderboardsManager {
    private final DataAccess dataAccess = new DataAccess();

    public LeaderboardsManager(){
        dataAccess.setConnection();
    }

    /**
     * This method retrieves the Top 5 Players with the longest words in the database.
     * @return a 2D array that contains the Username and the Word
     */
    public String[][] getLeaderboardsByLongestWord() {
        String playerLongestWord;
        String[][] longestWords = new String[5][2];

        for (String[] longestWord : longestWords) {
            Arrays.fill(longestWord, " ");
        }

        String query1 = "SELECT LongestWord, Username FROM gamehistory ORDER BY LENGTH(LongestWord) DESC, GameDate DESC LIMIT 5";
        try{
            Statement statement = dataAccess.getConnection().createStatement
                    (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet1 = statement.executeQuery(query1);

            int row = 0;
            int column = 0;
            while(resultSet1.next()){ //Populating the ArrayList with the top 5 Players with the longest words
                longestWords[row][column] = resultSet1.getString(1);
                column = 1;
                longestWords[row][column] = resultSet1.getString(2);
                row++;
                column = 0;
            }

            //The last data in the ArrayList would be the Current Player's data
            System.out.println(longestWords.length);
            return longestWords;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * This method retrieves the Top 5 players with the highest wins in the database.
     * @return a 2D array that contains the username and their number of wins.
     */
    public String[][] getLeaderboardsByWins() {
        String[][] highestWins = new String[5][2];

        for (String[] highestWin : highestWins) {
            Arrays.fill(highestWin, " ");
        }

        String query1 = "SELECT NumOfWins, Username FROM player WHERE NumOfWins > 0 ORDER BY NumOfWins DESC LIMIT 5"; //Getting the top 5
        try{
            Statement statement = dataAccess.getConnection().createStatement();
            ResultSet resultSet1 = statement.executeQuery(query1);

            int row = 0;
            int column = 0;
            while(resultSet1.next()){ //Populating the ArrayList with the top 5 Players with the longest words
                highestWins[row][column] = resultSet1.getString(1);
                column = 1;
                highestWins[row][column] = resultSet1.getString(2);
                row++;
                column = 0;
            }

            return highestWins;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
