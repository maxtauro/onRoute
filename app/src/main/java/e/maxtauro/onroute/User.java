package e.maxtauro.onroute;

/**
 * Created by maxtauro on 2018-02-19.
 */

public class User {
    private String userEmail, userStatus,userID;

    public User(){
    }

    public User(String userEmail, String userID, String userStatus){
        this.userEmail = userEmail;
        this.userID = userID;
        this.userStatus = userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
