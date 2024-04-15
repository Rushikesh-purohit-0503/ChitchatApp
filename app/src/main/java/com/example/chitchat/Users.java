package com.example.chitchat;

public class Users {
    String profilePic, mail, userName, password, userId, Lastmessage, status;
    public Users(){

    }
    public Users(String userId, String userName, String mail, String password, String profilePic, String status) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.mail = mail;
        this.profilePic = profilePic;
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastmessage() {
        return Lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        Lastmessage = lastmessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String id, String namee, String emaill, String password, String cpassword, String imageuri, String status) {

    }

    public Object getUid() {
        return userId;
    }
}
