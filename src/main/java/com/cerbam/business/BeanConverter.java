/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business;

import com.cerbam.data.MailAddressBean;
import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigFXBean;
import com.cerbam.data.MailConfigBean;
import com.cerbam.data.MailFXBean;
import com.cerbam.javaFX.controllers.GmailRootController;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */
public class BeanConverter {
    private final static Logger LOG = LoggerFactory.getLogger(BeanConverter.class);
    /**
     * This method converts mailConfigBean to MailConfigFXBean
     *
     * @param mailConfigBean (MailConfigBean) mailConfigBean to convert into
     * MailConfigFXBean
     * @return MailConfigFXBean - instance of MailConfigFXBean
     */
    public static MailConfigFXBean toFXBeanConfigConvert(MailConfigBean mailConfigBean) {
        MailConfigFXBean mailConfigFXBean = new MailConfigFXBean();
        mailConfigFXBean.setUserName(mailConfigBean.getUserName());
        mailConfigFXBean.setEmailAddress(mailConfigBean.getEmailAddress());
        mailConfigFXBean.setPassword(mailConfigBean.getPassword());
        mailConfigFXBean.setImapServerURL(mailConfigBean.getImapServerURL());
        mailConfigFXBean.setSmtpServerURL(mailConfigBean.getSmtpServerURL());
        mailConfigFXBean.setImapPortNumber(mailConfigBean.getImapPortNumber());
        mailConfigFXBean.setSmtpPortNumber(mailConfigBean.getSmtpPortNumber());
        mailConfigFXBean.setDatabaseURL(mailConfigBean.getDatabaseURL());
        mailConfigFXBean.setDatabaseName(mailConfigBean.getDatabaseName());
        mailConfigFXBean.setDatabasePortNumber(mailConfigBean.getDatabasePortNumber());
        mailConfigFXBean.setDatabaseUsername(mailConfigBean.getDatabaseUsername());
        mailConfigFXBean.setDatabasePassword(mailConfigBean.getPassword());
        return mailConfigFXBean;
    }

    /**
     * This method converts MailConfigFXBean to mailConfigBean
     *
     * @param mailConfigFXBean (MailConfigFXBean) MailConfigFXBean to convert
     * into mailConfigBean
     * @return mailConfigBean - instance of MailConfigFXBean
     */
    public static MailConfigBean toBeanConfigConvert(MailConfigFXBean mailConfigFXBean) {
        MailConfigBean mailConfigBean = new MailConfigBean();
        mailConfigBean.setUserName(mailConfigFXBean.getUserName());
        mailConfigBean.setEmailAddress(mailConfigFXBean.getEmailAddress());
        mailConfigBean.setPassword(mailConfigFXBean.getPassword());
        mailConfigBean.setImapServerURL(mailConfigFXBean.getImapServerURL());
        mailConfigBean.setSmtpServerURL(mailConfigFXBean.getSmtpServerURL());
        mailConfigBean.setImapPortNumber(mailConfigFXBean.getImapPortNumber());
        mailConfigBean.setSmtpPortNumber(mailConfigFXBean.getSmtpPortNumber());
        mailConfigBean.setDatabaseURL(mailConfigFXBean.getDatabaseURL());
        mailConfigBean.setDatabaseName(mailConfigFXBean.getDatabaseName());
        mailConfigBean.setDatabasePortNumber(mailConfigFXBean.getDatabasePortNumber());
        mailConfigBean.setDatabaseUsername(mailConfigFXBean.getDatabaseUsername());
        mailConfigBean.setDatabasePassword(mailConfigFXBean.getPassword());
        return mailConfigBean;
    }

    /**
     * This method converts mailFXBean to MailBean
     *
     * @param mailFXBean (MailFXBean) MailFXBean to convert into mailConfigBean
     * @return MailBean - instance of MailBean
     */
    public static MailBean toMailBeanConvert(MailFXBean mailFXBean) {
        MailBean mailBean = new MailBean();
        LOG.debug("0:" + mailFXBean);
        mailBean.setFolder(mailFXBean.getFolder());
        LOG.debug("1:" + mailBean.getFolder());
        mailBean.setID(mailFXBean.getID());
        LOG.debug("2:" + mailBean.getID());
        mailBean.setSenderEmailAddress(toMailAddressBean(mailFXBean.getSenderEmailAddress()));
        LOG.debug("3:" + mailBean.getSenderEmailAddress());
        mailBean.setReceiverEmailAddresses(toMailAddressBeanArray(mailFXBean.getReceiverEmailAddresses()));
        LOG.debug("4:" + mailFXBean.getReceiverEmailAddresses());
        mailBean.setSubject(mailFXBean.getSubject());
        LOG.debug("5:" + mailBean);
        mailBean.setTextMessage(mailFXBean.getTextMessage());
        LOG.debug("6:" + mailBean);
        mailBean.setTextMessage(mailFXBean.getTextMessage());
        LOG.debug("7:" + mailBean);
        mailBean.setPriority(mailFXBean.getPriority());
        LOG.debug("8:" + mailBean);
        mailBean.setCcs(toMailAddressBeanArray(mailFXBean.getCcs()));
        LOG.debug("9:" + mailBean);
        mailBean.setBccs(toMailAddressBeanArray(mailFXBean.getBccs()));
        LOG.debug("10:" + mailBean);
        mailBean.setHtmlContent(mailFXBean.getHtmlContent());
        LOG.debug("11:" + mailBean);
        mailBean.setEmbeddedAttachments(mailFXBean.getEmbeddedAttachments());
        LOG.debug("12:" + mailBean);
        mailBean.setAttachments(mailFXBean.getAttachments());
        LOG.debug("13:" + mailBean);
        mailBean.setSentDate(mailFXBean.getSentDate());
        LOG.debug("14:" + mailBean);
        mailBean.setReceivedDate(mailFXBean.getReceivedDate());
        LOG.debug("15:" + mailBean);

        return mailBean;
    }
    
    /**
     * This method converts mailFXBean to MailBean
     *
     * @param mailBean (MmailBean) MailBean to convert into mailConfigBean
     * @return MailFXBean - instance of MailFXBean
     */
    public static MailFXBean toMailFXBeanConvert(MailBean mailBean) {
        MailFXBean mailFXBean = new MailFXBean();
        mailFXBean.setFolder(mailBean.getFolder());
        mailFXBean.setID(mailBean.getID());
        mailFXBean.setSenderEmailAddress(mailBean.getSenderEmailAddress().getAddressName());
        mailFXBean.setReceiverEmailAddresses(toEmailAdressString(mailBean.getReceiverEmailAddresses()));
        mailFXBean.setSubject(mailBean.getSubject());
        mailFXBean.setTextMessage(mailBean.getTextMessage());
        mailFXBean.setPriority(mailBean.getPriority());
        mailFXBean.setCcs(toEmailAdressString(mailBean.getCcs()));
        mailFXBean.setBccs(toEmailAdressString(mailBean.getBccs()));
        mailFXBean.setHtmlContent(mailBean.getHtmlContent());
        mailFXBean.setEmbeddedAttachments(mailBean.getEmbeddedAttachments());
        mailFXBean.setAttachments(mailBean.getAttachments());
        mailFXBean.setSentDate(mailBean.getSentDate());
        mailFXBean.setReceivedDate(mailBean.getReceivedDate());
        LOG.debug("OLD BEAN:" + mailBean);
        LOG.debug("NEW FXBEAN:" + mailFXBean);
        return mailFXBean;
    }
    
    
    private static String toEmailAdressString(MailAddressBean[] mailAddressBeans){
    String result = ""; 
        for(MailAddressBean mail : mailAddressBeans){
        result = result + mail.getAddressName() + ",";
        LOG.debug(result);
    }
        LOG.debug("toEmailAdressString =======================================" + result);
        return result;
    }
    
    private static  MailAddressBean[] toMailAddressBeanArray(String mailAddressString){
        String[] addressStringArray = mailAddressString.split(",");
        LOG.debug("from " + mailAddressString);
        LOG.debug(Arrays.toString(addressStringArray));
        MailAddressBean[] mailAddressBeans = new MailAddressBean[addressStringArray.length];
        for (int i = 0; i < addressStringArray.length; i++) {
            mailAddressBeans[i] = new MailAddressBean();
            mailAddressBeans[i].setAddressName(addressStringArray[i]);
        }
    LOG.debug(Arrays.toString(mailAddressBeans));
    return mailAddressBeans;
    }
    
    private static MailAddressBean toMailAddressBean(String mailAddresString) {
        MailAddressBean mailAddressBean = new MailAddressBean();
        mailAddressBean.setAddressName(mailAddresString);
        return mailAddressBean;
    }

    public static ObservableList<MailFXBean> toObservableListMailFXBean(ObservableList<MailBean> MailBeanList){
    ObservableList<MailFXBean> listMailFXBean = FXCollections.observableArrayList();
    for (MailBean mailBean : MailBeanList){
        listMailFXBean.add(toMailFXBeanConvert(mailBean));
    }
    return listMailFXBean;
    }
}
