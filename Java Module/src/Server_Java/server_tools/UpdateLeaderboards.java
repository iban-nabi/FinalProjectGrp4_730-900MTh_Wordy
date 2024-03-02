package Server_Java.server_tools;

import java.sql.*;
import java.util.List;

public class UpdateLeaderboards {
    DataAccess dataAccess;
    Connection connection;

    /**
     * This constructor sets the Connection of the program to the Database.
     */
    public UpdateLeaderboards(){
        dataAccess = new DataAccess();
        dataAccess.setConnection();
        this.connection = dataAccess.getConnection();
    }

    /**
     * This method inserts a new record to the gamehistory table in the wordy database.
     * It specifies the GameDate, the LongestWord and the Username.
     * @param date when the game occured.
     * @param wordInputs the words entered during the game.
     */
    public void updateLongestWordLeaderboards(String date, List<String> wordInputs){
        String query = "INSERT INTO gamehistory(GameDate, LongestWord, Username) VALUES (?,?,?)";
        try{
            PreparedStatement ps = connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ps.setString(1,date);
            for(String s: wordInputs){
                String[] word = s.split(":");
                ps.setString(2,word[1]);
                ps.setString(3,word[0]);
                ps.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method updates the specified User's number of wins when the user wins the game.
     * @param username is the username of the winner.
     */
    public void updateUserWin(String username){
        String query = "SELECT NumOfWins FROM player WHERE Username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ps.setString(1,username);
            ResultSet resultSet = ps.executeQuery();

            int score=0;
            if(resultSet.next()) {
                score = resultSet.getInt(1);
                score++;
            }

            String query2 = "UPDATE player SET NumOfWins=? WHERE Username = ?";
            ps = connection.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1,score);
            ps.setString(2,username);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
