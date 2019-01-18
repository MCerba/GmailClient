/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business;

import com.cerbam.business.interfaces.Server;
import jodd.mail.MailServer;
import jodd.mail.SmtpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class create SmtpServe for GmailSender class
 * @author Cerba Mihail
 * @version 1.0
 */
public class SmtpServerCreator implements Server {

    private SmtpServer smtpServer;
    private final static Logger LOG = LoggerFactory.getLogger(SmtpServerCreator.class);


    /**
     *
     * @param smtpServerName(String)  - SMTP Server Name
     * @param emailAddress (String) -  email address
     * @param password  (String) - password
     */ 
    public SmtpServerCreator(String smtpServerName, String emailAddress, String password) {
        try {
            // Create am SMTP server object
            smtpServer = MailServer.create()
                    .ssl(true)
                    .host(smtpServerName)
                    .auth(emailAddress, password)
                    .debugMode(true)
                    .buildSmtpMailServer();
        } catch (Exception e) {
            LOG.info(" Email Addresses is not valid");
            throw new IllegalArgumentException("SmtpServer: Email Addresses is not valid");
        }
    }
    
    /**
     * return contained server
     *
     * @return Server(SmtpServer) - instance of created server
     */
    @Override
    public SmtpServer getServer() {
        return smtpServer;
    }
}
