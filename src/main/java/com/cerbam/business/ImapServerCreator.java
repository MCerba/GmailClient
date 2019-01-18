/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business;

import com.cerbam.business.interfaces.Server;
import jodd.mail.ImapServer;
import jodd.mail.MailServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class create ImapServer for GmailReceiver class
 * @author Cerba Mihail
 * @version 1.0
 */
public class ImapServerCreator implements Server {

    private ImapServer imapServer;
    private final static Logger LOG = LoggerFactory.getLogger(ImapServerCreator.class);

    /**
     *
     * @param imapServerName(String)  - ServerName
     * @param emailAddress (String) -  email address
     * @param password  (String) - password
     */ 
    public ImapServerCreator(String imapServerName, String emailAddress, String password) {
        try {
            // Create am SMTP server object
            imapServer = MailServer.create()
                    .ssl(true)
                    .host(imapServerName)
                    .auth(emailAddress, password)
                    .debugMode(true)
                    .buildImapMailServer();
        } catch (Exception e) {
            LOG.info(" Email Addresses is not valid");
            throw new IllegalArgumentException("SmtpServer: Email Addresses is not valid");
        }
    }
    
    /**
     * return contained server
     *
     * @return Server(ImapServer) - instance of created server
     */
    @Override
    public ImapServer getServer() {
        return imapServer;
    }
}
