/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces;

import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigBean;

/**
 *
 * @author Cerba Mihail
 */
public interface EmailSender {

    public void sendEmail(MailConfigBean mailConfigBean, MailBean mailBean);

}
