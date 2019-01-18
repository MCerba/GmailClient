/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business;

import com.cerbam.business.interfaces.EmailReceiver;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.MailAddressBean;
import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigBean;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.activation.DataSource;
import javax.mail.Flags;
import jodd.mail.EmailAddress;
import jodd.mail.EmailAttachment;
import jodd.mail.EmailFilter;
import jodd.mail.EmailMessage;
import jodd.mail.ImapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jodd.mail.ReceiveMailSession;
import jodd.mail.ReceivedEmail;

/**
 * This class receive email using Gmail server
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public class GmailReceiver implements EmailReceiver {

    private final static Logger LOG = LoggerFactory.getLogger(GmailReceiver.class);

    /**
     * This method receive all unleaded emails and mark flags to /seen
     * 
     * @param mailConfigBean - bean which contains all configurations
     * @return MailBean[] - array of MailBeans which contains received emails
     */
    @Override
    public ArrayList<MailBean> receiveEmail(MailConfigBean mailConfigBean) {

        ImapServer imapServer;
        ArrayList<MailBean> emailBeans = new ArrayList<>();
        ArrayList<MailAddressBean> ccs = new ArrayList<>();
        try {
            //creat Imap Server 
            imapServer = new ImapServerCreator(mailConfigBean.getImapServerURL(),
                    mailConfigBean.getEmailAddress(),
                    mailConfigBean.getPassword()).getServer();
        } catch (IllegalArgumentException e) {
            return null;
        }

        try (ReceiveMailSession session = imapServer.createSession()) {
            session.open();
            LOG.info("Message count: " + session.getMessageCount());
            ReceivedEmail[] emails = session.receiveEmailAndMarkSeen(EmailFilter
                    .filter().flag(Flags.Flag.SEEN, false));
            if (emails != null) {
                LOG.info("\n >>>> ReceivedEmail count = " + emails.length);
                for (ReceivedEmail email : emails) {
                    MailBean newEmailBean = new MailBean();
                    List<MailAddressBean> receiverEmailAddresses = new ArrayList<>();
                    LOG.info("\n\n===[" + email.messageNumber() + "]===");

                    // common info
                    MailAddressBean senderAddressBean = new MailAddressBean();
                    senderAddressBean.setAddressName(email.from().getEmail());
                    newEmailBean.setSenderEmailAddress(senderAddressBean);
                    // Handling array in email object
                    LOG.info("TO:" + Arrays.toString(email.to()));
                    for (EmailAddress emailAddress : email.to()) {
                        MailAddressBean mailAddressBean = new MailAddressBean();
                        mailAddressBean.setAddressName(emailAddress.getEmail());
                        receiverEmailAddresses.add(mailAddressBean);
                    }

                    newEmailBean.setReceiverEmailAddresses(receiverEmailAddresses.toArray(new MailAddressBean[0]));
                    LOG.info("CC:" + Arrays.toString(email.cc()));
                    for (EmailAddress address : email.cc()) {
                        MailAddressBean mailAddressBean = new MailAddressBean();
                        mailAddressBean.setAddressName(address.getEmail());
                        ccs.add(mailAddressBean);
                    }
                    newEmailBean.setCcs(ccs.toArray(new MailAddressBean[0]));
                    LOG.info("SUBJECT:" + email.subject());
                    newEmailBean.setSubject(email.subject());
                    LOG.info("PRIORITY:" + email.priority());
                    newEmailBean.setPriority(email.priority());

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    Date now = new Date();
                    Date sentDate = email.sentDate();
                    newEmailBean.setReceivedDate(dateFormat.format(now));
                    newEmailBean.setSentDate(dateFormat.format(sentDate));
                    LOG.info("SENT DATE:" + dateFormat.format(sentDate));
                    LOG.info("RECEIVED flag: " + email.flags());

                    // process messages
                    List<EmailMessage> messages = email.messages();
                    for (EmailMessage msg : messages) {
                        LOG.info("------EmailMessage");
                        LOG.info("EmailMessage encoding: " + msg.getEncoding());
                        LOG.info("EmailMessage mime type " + msg.getMimeType());
                        LOG.info("EmailMessage content: " + msg.getContent());
                        if ("TEXT/PLAIN".equals(msg.getMimeType())) {
                            newEmailBean.setTextMessage(msg.getContent());
                        } else if ("TEXT/HTML".equals(msg.getMimeType())) {
                            newEmailBean.setHtmlContent(msg.getContent());
                        }
                    }

//TODO add HTML attachments end embeded attachments  to bean
                    // process attachments
                    List<EmailAttachment<? extends DataSource>> attachments = email.attachments();
                    if (attachments != null) {
                        List<AttachmentBean> attachmentBeans = new ArrayList<>();
                        List<AttachmentBean> embededAttachmentBeans = new ArrayList<>();
                        for (EmailAttachment attachment : attachments) {
                            LOG.info("name: " + attachment.getName());
                            LOG.info("cid: " + attachment.getContentId());
                            LOG.info("size: " + attachment.getSize());
                            LOG.info("isEmbedded: " + attachment.isEmbedded());
                            // Write the file to disk
                            // Location hard coded in this example
                            AttachmentBean attachmentBean = new AttachmentBean();
                            if (attachment.isEmbedded()) {
                                attachmentBean.setAttachmentArray(attachment.toByteArray());
                                attachmentBean.setPath(attachment.getName());
                                embededAttachmentBeans.add(attachmentBean);
                            } else {
                                attachmentBean.setAttachmentArray(attachment.toByteArray());
                                attachmentBean.setPath(attachment.getName());
                                attachmentBeans.add(attachmentBean);
                            }
                            attachment.writeToFile(new File("C:\\Temp", attachment.getName()));

                        }
                        if (!attachmentBeans.isEmpty()) {
                            newEmailBean.setAttachments(attachmentBeans.toArray(new AttachmentBean[0]));
                        }
                        if (!embededAttachmentBeans.isEmpty()) {
                            newEmailBean.setEmbeddedAttachments(embededAttachmentBeans.toArray(new AttachmentBean[0]));
                        }
                    }
                    emailBeans.add(newEmailBean);
                }
            } else {
                LOG.info("Have no new emails");
            }
        }
        return emailBeans;
    }
}
