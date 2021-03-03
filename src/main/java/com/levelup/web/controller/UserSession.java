package com.levelup.web.controller;

public class UserSession {
    private String userLogin;
    private boolean isAdmin;

    public UserSession() {
    }

    public UserSession(String userLogin, boolean isAdmin) {
        this.userLogin = userLogin;
        this.isAdmin = isAdmin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
