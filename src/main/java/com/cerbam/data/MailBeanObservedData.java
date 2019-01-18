/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */
public class MailBeanObservedData{

    private final static Logger LOG = LoggerFactory.getLogger(MailBeanObservedData.class);

    private MailFXBean mailFXBean;

    private final PropertyChangeSupport mPcs = new PropertyChangeSupport(this);

    public MailBeanObservedData() {
        mailFXBean = new MailFXBean();
    }

    public void setObservedData(MailFXBean mailFXBean) {
        LOG.debug("VeryOLD DATa: " + this.mailFXBean);
        MailFXBean oldMailFXBeanObservedData = this.mailFXBean.makeCopy();
        this.mailFXBean = mailFXBean;
        this.mPcs.firePropertyChange("MailBeanObservedData", oldMailFXBeanObservedData, mailFXBean);
        LOG.debug("NEW DATA: " + mailFXBean);
        LOG.debug("OLD DATA: " + oldMailFXBeanObservedData);

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        LOG.debug("addPropertyChangeListener");
        this.mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.mPcs.removePropertyChangeListener(listener);
    }

    public MailFXBean getTheObservedData() {
        return this.mailFXBean;
    }
}
