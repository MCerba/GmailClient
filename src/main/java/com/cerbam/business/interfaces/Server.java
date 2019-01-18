/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces;

import jodd.mail.MailServer;

/**
 *
 * @author Cerba Mihail
 */
public interface Server {

    /**
     * return contained server
     *
     * @return Server - instance of created server
     */
    MailServer getServer();
}
