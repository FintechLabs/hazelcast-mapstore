package com.fintechlabs;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class Person implements Serializable {

    private static final long serialversionUID = 129348938L;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private Date dateCreated;
    private Date lastUpdated;
    private String uniqueId = UUID.randomUUID().toString();

    Person() {
    }

    Person(ResultSet resultSet) throws SQLException {
        this.firstName = resultSet.getString("first_name");
        this.lastName = resultSet.getString("last_name");
        this.emailAddress = resultSet.getString("email_address");
        this.phoneNumber = resultSet.getString("phone_number");
        this.dateCreated = resultSet.getTimestamp("date_created");
        this.lastUpdated = resultSet.getTimestamp("last_updated");
        this.uniqueId = resultSet.getString("unique_id");
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
