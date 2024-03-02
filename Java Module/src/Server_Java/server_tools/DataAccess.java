package Server_Java.server_tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataAccess {
    private Connection connection;

    public DataAccess(){

    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * This method sets the database connection to be used by the applicatin
     */
    public void setConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wordy?user=root&password");
        } catch (SQLException e) {
            System.out.println("Database connection failed");
        }

    }
}