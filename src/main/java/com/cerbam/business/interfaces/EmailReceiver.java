/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces;

import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigBean;
import java.util.ArrayList;

/**
 * This class receive email using Email server
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public interface EmailReceiver {

    public ArrayList<MailBean> receiveEmail(MailConfigBean mailConfigBean);
}
