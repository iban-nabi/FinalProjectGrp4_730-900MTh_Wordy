package Server_Java.server_tools;

import Server_Java.server_objects.Player;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {


    private final DataAccess dataAccess = new DataAccess();

    /**
     * This constructor will set a connection to the wordy database.
     */
    public LoginManager(){
        dataAccess.setConnection();
    }

    /**
     * This method checks if the entered username and password matches a data in the player table.
     * The returned player will be used in the Servant class, under the login method.
     *
     * @param username is the username entered by the User.
     * @param passTemp is the password entered by the User.
     * @return the PLayer object to be used in the Login Method in the Servant Class.
     * @throws RuntimeException for exceptions thrown in during the execution.
     * @throws NoSuchAlgorithmException for algroithms not available in the environment
     */
    public Player authenticateAccount(String username, String passTemp) throws RuntimeException, NoSuchAlgorithmException {
        String password = md5(passTemp);
        String query = "SELECT * FROM player WHERE Username = ? AND Password = ? ";
        try {
            PreparedStatement ps = dataAccess.getConnection().prepareStatement(query);

            ps.setString(1,username);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()) {
                return new Player(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        (Integer.parseInt(resultSet.getString(6))));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method converts the user's password to a cryptographic hash algorithm called md5.
     *
     * @param password is the entered password of the user.
     * @return the md5 converted password of the user.
     * @throws RuntimeException for exceptions thrown in during the execution.
     * @throws NoSuchAlgorithmException for algroithms not available in the environment
     */
    private String md5(String password) throws RuntimeException, NoSuchAlgorithmException {
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.update(password.getBytes(),0,password.length());
        return new BigInteger(1,m.digest()).toString(16);
    }

    /**
     * This method checks if the username entered matches a username in the database.
     *
     * @param username is the entered username of the user.
     * @return true if username exists, otherwise false.
     */
    public boolean isUsernameExisting(String username){
        String query = "SELECT * FROM player WHERE Username = ?";
        try{
            PreparedStatement ps = dataAccess.getConnection().prepareStatement(query);
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
