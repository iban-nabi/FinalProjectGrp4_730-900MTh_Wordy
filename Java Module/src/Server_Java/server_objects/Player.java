package Server_Java.server_objects;

public class Player{
    private String playerID;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private int numOfWins;

    public Player(){

    }

    public Player(String playerID,String username, String firstName, String lastName, String password, int numOfWins){
        this.playerID = playerID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.numOfWins = numOfWins;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public void setNumOfWins(int numWins) {
        this.numOfWins = numWins;
    }
}
