package e.maxtauro.onroute;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by maxtauro on 2018-02-19.
 */

public class User {
    private String userEmail, userStatus, userID, lat, lng;
    public DatabaseReference friendList;


    public User(){
    }

    public User(String userEmail, String userID, String userStatus, String lat, String lng){
        this.userEmail = userEmail;
        this.userID = userID;
        this.userStatus = userStatus;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUserEmail() {
        return userEmail;
    }

    //public String

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }




}
