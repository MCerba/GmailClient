/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail 
 */
public class MailBeanListObservedData {
   

    private final static Logger LOG = LoggerFactory.getLogger(MailBeanListObservedData.class);

    private ArrayList<MailFXBean> mailBeanslist;

    private final PropertyChangeSupport mPcs = new PropertyChangeSupport(this);


    public void setObservedData(ArrayList<MailFXBean> mailBeanslist) {
        LOG.debug("setObservedData: " + mailBeanslist);
        ArrayList<MailFXBean> oldTheData = new ArrayList<>(this.mailBeanslist);
        this.mailBeanslist = mailBeanslist;
        mPcs.firePropertyChange("observedData", oldTheData, mailBeanslist);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        LOG.debug("addPropertyChangeListener");
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }


    public ArrayList<MailFXBean> getTheObservedData() {
        return this.mailBeanslist;
    }
}
