/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Cerba Mihail
 */
public class MailConfigFXBean {

    /**
     * The user’s name The user’s email address which is also the user name for
     * Gmail The user’s Gmail password The URL of the IMAP server The URL of the
     * SMTP server The IMAP port number (default 993) The SMTP port number
     * (default 465) The URL of the MySQL database The database name The port of
     * the MySQL database (default 3306) The user name for the MySQL database
     * The password for the MySQL database
     */
    private final StringProperty userName;
    private final StringProperty emailAddress;
    private final StringProperty password;
    private final StringProperty imapServerURL;
    private final StringProperty smtpServerURL;
    private final StringProperty imapPortNumber;
    private final StringProperty smtpPortNumber;
    private final StringProperty databaseURL;
    private final StringProperty databaseName;
    private final StringProperty databasePortNumber;
    private final StringProperty databaseUsername;
    private final StringProperty databasePassword;

    /**
     * Default Constructor
     */
    public MailConfigFXBean() {
        this("", "", "", "", "", "", "", "", "", "", "", "");
    }

    public MailConfigFXBean(String userName, String emailAddress, String password,
            String imapServerURL, String smtpServerURL, String imapPortNumber,
            String smtpPortNumber, String databaseURL, String databaseName,
            String databasePortNumber, String databaseUsername,
            String databasePassword) {
        super();
        this.userName = new SimpleStringProperty(userName);
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.password = new SimpleStringProperty(password);
        this.imapServerURL = new SimpleStringProperty(imapServerURL);
        this.smtpServerURL = new SimpleStringProperty(smtpServerURL);
        this.imapPortNumber = new SimpleStringProperty(imapPortNumber);
        this.smtpPortNumber = new SimpleStringProperty(smtpPortNumber);
        this.databaseURL = new SimpleStringProperty(databaseURL);
        this.databaseName = new SimpleStringProperty(databaseName);
        this.databasePortNumber = new SimpleStringProperty(databasePortNumber);
        this.databaseUsername = new SimpleStringProperty(databaseUsername);
        this.databasePassword = new SimpleStringProperty(databasePassword);
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setImapServerURL(String imapServerURL) {
        this.imapServerURL.set(imapServerURL);
    }

    public void setSmtpServerURL(String smtpServerURL) {
        this.smtpServerURL.set(smtpServerURL);
    }

    public void setImapPortNumber(String imapPortNumber) {
        this.imapPortNumber.set(imapPortNumber);
    }

    public void setSmtpPortNumber(String smtpPortNumber) {
        this.smtpPortNumber.set(smtpPortNumber);
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL.set(databaseURL);
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName.set(databaseName);
    }

    public void setDatabasePortNumber(String databasePortNumber) {
        this.databasePortNumber.set(databasePortNumber);
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername.set(databaseUsername);
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword.set(databasePassword);
    }

    public String getUserName() {
        return userName.get();
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getImapServerURL() {
        return imapServerURL.get();
    }

    public String getSmtpServerURL() {
        return smtpServerURL.get();
    }

    public String getImapPortNumber() {
        return imapPortNumber.get();
    }

    public String getSmtpPortNumber() {
        return smtpPortNumber.get();
    }

    public String getDatabaseURL() {
        return databaseURL.get();
    }

    public String getDatabaseName() {
        return databaseName.get();
    }

    public String getDatabasePortNumber() {
        return databasePortNumber.get();
    }

    public String getDatabaseUsername() {
        return databaseUsername.get();
    }

    public String getDatabasePassword() {
        return databasePassword.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty imapServerURLProperty() {
        return imapServerURL;
    }

    public StringProperty smtpServerURLProperty() {
        return smtpServerURL;
    }

    public StringProperty imapPortNumberProperty() {
        return imapPortNumber;
    }

    public StringProperty smtpPortNumberProperty() {
        return smtpPortNumber;
    }

    public StringProperty databaseURLProperty() {
        return databaseURL;
    }

    public StringProperty databaseNameProperty() {
        return databaseName;
    }

    public StringProperty databasePortNumberProperty() {
        return databasePortNumber;
    }

    public StringProperty databaseUsernameProperty() {
        return databaseUsername;
    }

    public StringProperty databasePasswordProperty() {
        return databasePassword;
    }

    @Override
    public String toString() {
        return "MailConfigBean{" + "userName=" + userName.get() + ", emailAddress=" + emailAddress.get() + ", password=" + password.get() + ", imapServerURL=" + imapServerURL.get() + ", smtpServerURL=" + smtpServerURL.get() + ", imapPortNumber=" + imapPortNumber.get() + ", smtpPortNumber=" + smtpPortNumber.get() + ", databaseURL=" + databaseURL.get() + ", databaseName=" + databaseName.get() + ", databasePortNumber=" + databasePortNumber.get() + ", databaseUsername=" + databaseUsername.get() + ", databasePassword=" + databasePassword.get() + '}';
    }

}
