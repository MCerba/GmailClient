/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

/**
 *
 * @author Cerba Mihail
 */
public class MailConfigBean {

    /**
     * The user’s name The user’s email address which is also the user name for
     * Gmail The user’s Gmail password The URL of the IMAP server The URL of the
     * SMTP server The IMAP port number (default 993) The SMTP port number
     * (default 465) The URL of the MySQL database The database name The port of
     * the MySQL database (default 3306) The user name for the MySQL database
     * The password for the MySQL database
     */
    private String userName;
    private String emailAddress;
    private String password;
    private String imapServerURL;
    private String smtpServerURL;
    private String imapPortNumber;
    private String smtpPortNumber;
    private String databaseURL;
    private String databaseName;
    private String databasePortNumber;
    private String databaseUsername;
    private String databasePassword;

    /**
     * Default Constructor
     */
    public MailConfigBean() {
    }

    public MailConfigBean(String userName, String emailAddress, String password,
            String imapServerURL, String smtpServerURL, String imapPortNumber,
            String smtpPortNumber, String databaseURL, String databaseName,
            String databasePortNumber, String databaseUsername,
            String databasePassword) {
        super();
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.imapServerURL = imapServerURL;
        this.smtpServerURL = smtpServerURL;
        this.imapPortNumber = imapPortNumber;
        this.smtpPortNumber = smtpPortNumber;
        this.databaseURL = databaseURL;
        this.databaseName = databaseName;
        this.databasePortNumber = databasePortNumber;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImapServerURL(String imapServerURL) {
        this.imapServerURL = imapServerURL;
    }

    public void setSmtpServerURL(String smtpServerURL) {
        this.smtpServerURL = smtpServerURL;
    }

    public void setImapPortNumber(String imapPortNumber) {
        this.imapPortNumber = imapPortNumber;
    }

    public void setSmtpPortNumber(String smtpPortNumber) {
        this.smtpPortNumber = smtpPortNumber;
    }

    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setDatabasePortNumber(String databasePortNumber) {
        this.databasePortNumber = databasePortNumber;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getImapServerURL() {
        return imapServerURL;
    }

    public String getSmtpServerURL() {
        return smtpServerURL;
    }

    public String getImapPortNumber() {
        return imapPortNumber;
    }

    public String getSmtpPortNumber() {
        return smtpPortNumber;
    }

    public String getDatabaseURL() {
        return databaseURL;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabasePortNumber() {
        return databasePortNumber;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.userName != null ? this.userName.hashCode() : 0);
        hash = 59 * hash + (this.emailAddress != null ? this.emailAddress.hashCode() : 0);
        hash = 59 * hash + (this.password != null ? this.password.hashCode() : 0);
        hash = 59 * hash + (this.imapServerURL != null ? this.imapServerURL.hashCode() : 0);
        hash = 59 * hash + (this.smtpServerURL != null ? this.smtpServerURL.hashCode() : 0);
        hash = 59 * hash + (this.imapPortNumber != null ? this.imapPortNumber.hashCode() : 0);
        hash = 59 * hash + (this.smtpPortNumber != null ? this.smtpPortNumber.hashCode() : 0);
        hash = 59 * hash + (this.databaseURL != null ? this.databaseURL.hashCode() : 0);
        hash = 59 * hash + (this.databaseName != null ? this.databaseName.hashCode() : 0);
        hash = 59 * hash + (this.databasePortNumber != null ? this.databasePortNumber.hashCode() : 0);
        hash = 59 * hash + (this.databaseUsername != null ? this.databaseUsername.hashCode() : 0);
        hash = 59 * hash + (this.databasePassword != null ? this.databasePassword.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MailConfigBean other = (MailConfigBean) obj;
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        if ((this.emailAddress == null) ? (other.emailAddress != null) : !this.emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        if ((this.imapServerURL == null) ? (other.imapServerURL != null) : !this.imapServerURL.equals(other.imapServerURL)) {
            return false;
        }
        if ((this.smtpServerURL == null) ? (other.smtpServerURL != null) : !this.smtpServerURL.equals(other.smtpServerURL)) {
            return false;
        }
        if ((this.imapPortNumber == null) ? (other.imapPortNumber != null) : !this.imapPortNumber.equals(other.imapPortNumber)) {
            return false;
        }
        if ((this.smtpPortNumber == null) ? (other.smtpPortNumber != null) : !this.smtpPortNumber.equals(other.smtpPortNumber)) {
            return false;
        }
        if ((this.databaseURL == null) ? (other.databaseURL != null) : !this.databaseURL.equals(other.databaseURL)) {
            return false;
        }
        if ((this.databaseName == null) ? (other.databaseName != null) : !this.databaseName.equals(other.databaseName)) {
            return false;
        }
        if ((this.databasePortNumber == null) ? (other.databasePortNumber != null) : !this.databasePortNumber.equals(other.databasePortNumber)) {
            return false;
        }
        if ((this.databaseUsername == null) ? (other.databaseUsername != null) : !this.databaseUsername.equals(other.databaseUsername)) {
            return false;
        }
        if ((this.databasePassword == null) ? (other.databasePassword != null) : !this.databasePassword.equals(other.databasePassword)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MailConfigBean{" + "userName=" + userName + ", emailAddress=" + emailAddress + ", password=" + password + ", imapServerURL=" + imapServerURL + ", smtpServerURL=" + smtpServerURL + ", imapPortNumber=" + imapPortNumber + ", smtpPortNumber=" + smtpPortNumber + ", databaseURL=" + databaseURL + ", databaseName=" + databaseName + ", databasePortNumber=" + databasePortNumber + ", databaseUsername=" + databaseUsername + ", databasePassword=" + databasePassword + '}';
    }

}
