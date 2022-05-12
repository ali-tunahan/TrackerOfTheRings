package com.example.trackeroftherings;

import java.io.Serializable;

public abstract class Account implements Serializable {
    private String username;
    private String password;
    private String companyID;

    /**
     * Empty constructor for database
     */
    public Account(){}

    /**
     * Accessor for username
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Accessor for companyID
     * @return companyID
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * Mutator for companyID
     * @param companyID
     */
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
    /**
     * Accessor for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Mutator for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Mutator for username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Authenticates company ID
     * @param ID
     * @return
     */
    public Company authenticateCompanyID(String ID){
        return new Company();
    }
    
}
