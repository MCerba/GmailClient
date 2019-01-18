/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cerbam.business.interfaces.EmailSender;
import com.cerbam.data.AttachmentBean;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import com.cerbam.data.Gmail;
import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigBean;

/**
 * This class send email using Gmail server
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public class GmailSender implements EmailSender {

    // Real programmers use logging
    private final static Logger LOG = LoggerFactory.getLogger(GmailSender.class);


    /**
     * This method send email in form of mailBean
     * 
     * @param mailConfigBean (MailConfigBean) Mail Bean Configuration object
     * @param mailBean (mailBean) mailBean which represent email to send
     */
    @Override
    public void sendEmail(MailConfigBean mailConfigBean, MailBean mailBean) throws IllegalArgumentException {
        Gmail gmail;
        //creat SMTP server
        SmtpServer smtpServer = new SmtpServerCreator(mailConfigBean.getSmtpServerURL(),
                mailConfigBean.getEmailAddress(),
                mailConfigBean.getPassword()).getServer();

        //create limple email with no content
            String[] ReceiverEmailAddresses = new String[mailBean.getReceiverEmailAddresses().length];
            for (int i = 0; i < ReceiverEmailAddresses.length; i++) {
                ReceiverEmailAddresses[i] = mailBean.getReceiverEmailAddresses()[i].getAddressName();
            }
            gmail = new Gmail(mailBean.getSenderEmailAddress().getAddressName(),
                    ReceiverEmailAddresses);


        //check mailBean for existing fields
        if (mailBean.getTextMessage() != null) {
            gmail.addtextMessage(mailBean.getTextMessage());
        }
        if (mailBean.getSubject() != null) {
            gmail.addSubject(mailBean.getSubject());
        }
        if (mailBean.getHtmlContent() != null) {
            gmail.addHtmlContent(mailBean.getHtmlContent());
        }
        if (mailBean.getCcs() != null && mailBean.getCcs().length > 0 && !"".equals((mailBean.getCcs())[0].getAddressName())) {
            gmail.addCcs(mailBean.getCcs());
        } 
        if (mailBean.getSentDate() != null && !"".equals(mailBean.getSentDate())) {
            gmail.addSentDate(mailBean.getSentDate());
        }

        if (mailBean.getBccs() != null && mailBean.getBccs().length > 0 && !"".equals((mailBean.getBccs())[0].getAddressName())) {
            gmail.addBccs(mailBean.getBccs());
        }
        if (mailBean.getAttachments() != null) {
            for (AttachmentBean attachmentBean : mailBean.getAttachments()) {
                if (attachmentBean!= null ) gmail.addAttachment(attachmentBean.getPath());
            }
        }
        if (mailBean.getEmbeddedAttachments() != null) {
            for (AttachmentBean attachmentBean : mailBean.getEmbeddedAttachments()) {
                if (attachmentBean!= null ) gmail.addEmbeddedAttachment(attachmentBean.getPath());
            }
        }
        // Like a file we open the session, send the message and close the
        // session
        try ( // A session is the object responsible for communicating with the server
                SendMailSession session = smtpServer.createSession()) {
            // Like a file we open the session, send the message and close the
            // session
            session.open();
            session.sendMail(gmail.getEmail());
            LOG.info("Email sent");
        }
    }
}
