/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.junit_mail_test;

import com.cerbam.business.GmailReceiver;
import com.cerbam.business.GmailSender;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailAddressBean;
import com.cerbam.data.MailBean;
import org.junit.Before;
import com.cerbam.data.MailConfigBean;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */
public class TestSenderReceiver {

    private final static Logger LOG = LoggerFactory.getLogger(TestSenderReceiver.class);

    MailConfigBean mailConfigSenderBean;
    MailConfigBean mailConfigReceiverBean;
    MailBean mailBean;
    MailBean mailBeanWithSubject;
    MailBean mailBeanWithCC;
    MailBean mailBeanWithBC;
    MailBean mailBeanWithTextMessage;
    MailBean mailBeanWithHTMLContent;
    MailBean mailBeanWithEmbeddedAttachments;
    MailBean mailBeanWithAttachments1;
    MailBean mailBeanWithAttachments2;
    MailAddressBean senderAddressBean;
    MailAddressBean receiverAddressBean;
    MailAddressBean otherAddressBean;
    FolderBean folderBean;

    @Before
    public void init() {
        mailConfigSenderBean = new MailConfigBean();
        mailConfigSenderBean.setEmailAddress("send.1642705@gmail.com");
        mailConfigSenderBean.setPassword("Dawson164@&05");
        mailConfigSenderBean.setSmtpServerURL("smtp.gmail.com");
        mailConfigSenderBean.setImapServerURL("imap.gmail.com");

        mailConfigReceiverBean = new MailConfigBean();
        mailConfigReceiverBean.setEmailAddress("receive.1642705@gmail.com");
        mailConfigReceiverBean.setPassword("Dawson164@&05");
        mailConfigReceiverBean.setSmtpServerURL("smtp.gmail.com");
        mailConfigReceiverBean.setImapServerURL("imap.gmail.com");

        mailBean = new MailBean();

        mailBeanWithTextMessage = new MailBean();

        mailBeanWithSubject = new MailBean();

        mailBeanWithCC = new MailBean();

        mailBeanWithBC = new MailBean();

        mailBeanWithEmbeddedAttachments = new MailBean();
        
        mailBeanWithHTMLContent = new MailBean();

        mailBeanWithAttachments1 = new MailBean();

        //Both, Embeded and simple attachments
        mailBeanWithAttachments2 = new MailBean();
            
        folderBean = new FolderBean();
        folderBean.setFolderName("Inbox");
        
        senderAddressBean = new MailAddressBean();
        senderAddressBean.setAddressName("send.1642705@gmail.com");
        receiverAddressBean = new MailAddressBean();
        receiverAddressBean.setAddressName("receive.1642705@gmail.com");
        otherAddressBean = new MailAddressBean();
        otherAddressBean.setAddressName("other.1642705@gmail.com");

    }

    @Test
    public void SenderReceiveWithAttachments2Test() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();

        AttachmentBean attachment = new AttachmentBean();
        attachment.setPath("WindsorKen180.jpg");
        attachment.setAttachmentArray("WindsorKen180.jpg");

        AttachmentBean embeddedAttachment = new AttachmentBean();
        embeddedAttachment.setPath("FreeFall.jpg");
        embeddedAttachment.setAttachmentArray("FreeFall.jpg");

        mailBeanWithAttachments2.setSenderEmailAddress(senderAddressBean);
        mailBeanWithAttachments2.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithAttachments2.setHtmlContent("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:FreeFall.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        mailBeanWithAttachments2.setCcs(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithAttachments2.setSubject("Test of SenderReceiveWithAttachments2Test");
        mailBeanWithAttachments2.setBccs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithAttachments2.setTextMessage("The first Test");
        mailBeanWithAttachments2.setAttachments(new AttachmentBean[]{attachment});
        mailBeanWithAttachments2.setEmbeddedAttachments(new AttachmentBean[]{embeddedAttachment});
                 mailBeanWithAttachments2.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithAttachments2);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean> received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithAttachments2.toString());
        LOG.info("=================================================================================================");
        assertTrue(mailBeanWithAttachments2.equals(received.get(0)));

    }

    @Test
    public void SenderReceiveWithAttachments1Test() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();

        AttachmentBean attachment = new AttachmentBean();
        attachment.setPath("WindsorKen180.jpg");
        attachment.setAttachmentArray("WindsorKen180.jpg");

        mailBeanWithAttachments1.setSenderEmailAddress(senderAddressBean);
        mailBeanWithAttachments1.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithAttachments1.setCcs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithAttachments1.setHtmlContent("<h1>Send works!<h1>");
        mailBeanWithAttachments1.setSubject("SenderReceiveWithAttachments1Test");
        mailBeanWithAttachments1.setBccs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithAttachments1.setTextMessage("The first Test");
        mailBeanWithAttachments1.setAttachments(new AttachmentBean[]{attachment});
                 mailBeanWithAttachments1.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithAttachments1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean> received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithAttachments1.toString());
        assertTrue(mailBeanWithAttachments1.equals(received.get(0)));

    }

    @Test
    public void SenderReceiveEmbeddedAttachmentsTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();
        AttachmentBean embeddedAttachment = new AttachmentBean();
        embeddedAttachment.setPath("FreeFall.jpg");
        embeddedAttachment.setAttachmentArray("FreeFall.jpg");
        mailBeanWithEmbeddedAttachments.setSenderEmailAddress(senderAddressBean);
        mailBeanWithEmbeddedAttachments.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithEmbeddedAttachments.setCcs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithEmbeddedAttachments.setHtmlContent("<h1>Send works!<h1>");
        mailBeanWithEmbeddedAttachments.setSubject("SenderReceiveEmbeddedAttachmentsTest");
        mailBeanWithEmbeddedAttachments.setBccs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithEmbeddedAttachments.setTextMessage("The first Test");
        mailBeanWithEmbeddedAttachments.setAttachments(new AttachmentBean[]{embeddedAttachment});
         mailBeanWithEmbeddedAttachments.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithEmbeddedAttachments);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithEmbeddedAttachments.toString());
        assertTrue(mailBeanWithEmbeddedAttachments.equals(received.get(0)));

    }

    @Test
    public void SenderReceiveWithHTMLContentTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();

        mailBeanWithHTMLContent.setSenderEmailAddress(senderAddressBean);
        mailBeanWithHTMLContent.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithHTMLContent.setCcs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithHTMLContent.setSubject("SenderReceiveWithHTMLContentTest");
        mailBeanWithHTMLContent.setBccs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithHTMLContent.setTextMessage("The first Test");
        mailBeanWithHTMLContent.setHtmlContent("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:FreeFall.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
                                mailBeanWithHTMLContent.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithHTMLContent);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithHTMLContent.toString());
        assertTrue(mailBeanWithHTMLContent.equals(received.get(0)));

    }

    @Test
    public void SenderReceiveWithTextMessageTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();

        mailBeanWithTextMessage.setSenderEmailAddress(senderAddressBean);
        mailBeanWithTextMessage.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithTextMessage.setHtmlContent("<h1>Send works!<h1>");
        mailBeanWithTextMessage.setCcs(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithTextMessage.setSubject("SenderReceiveWithTextMessageTest");
        mailBeanWithTextMessage.setBccs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithTextMessage.setTextMessage("The first Test");
                        mailBeanWithTextMessage.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithTextMessage);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithTextMessage.toString());
        assertTrue(mailBeanWithTextMessage.equals(received.get(0)));

    }

    @Test
    public void SenderReceiveWithBCTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();
        mailBeanWithBC.setSenderEmailAddress(senderAddressBean);
        mailBeanWithBC.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithBC.setCcs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithBC.setSubject("SenderReceiveWithBCTest");
        mailBeanWithBC.setTextMessage("The of SenderReceiveWithBCTest");
        mailBeanWithBC.setBccs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithBC.setHtmlContent("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:FreeFall.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
                mailBeanWithBC.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithBC);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithBC.toString());
        assertTrue(mailBeanWithBC.equals(received.get(0)));

    }

    @Test
    public void SenderReceiverWithSubjectTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();
        mailBeanWithSubject.setSenderEmailAddress(senderAddressBean);
        mailBeanWithSubject.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithSubject.setCcs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithSubject.setTextMessage("The first Test");
        mailBeanWithSubject.setSubject("SenderReceiverWithSubjectTest");
        mailBeanWithSubject.setHtmlContent("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:FreeFall.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
                mailBeanWithSubject.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithSubject);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithSubject.toString());

        assertTrue(mailBeanWithSubject.equals(received.get(0)));

    }

    @Test
    public void SenderReceiverWithCCTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();
        mailBeanWithCC.setSenderEmailAddress(senderAddressBean);
        mailBeanWithCC.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBeanWithCC.setCcs(new MailAddressBean[]{otherAddressBean});
        mailBeanWithCC.setTextMessage("SenderReceiverWithCCTest");
        mailBeanWithCC.setHtmlContent("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:FreeFall.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        mailBeanWithCC.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBeanWithCC);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBeanWithCC.toString());
        assertTrue(mailBeanWithCC.equals(received.get(0)));

    }

    @Test
    public void SenderReceiverTest() {
        GmailSender GS = new GmailSender();
        GmailReceiver GR = new GmailReceiver();
        mailBean.setSenderEmailAddress(senderAddressBean);
        mailBean.setReceiverEmailAddresses(new MailAddressBean[]{receiverAddressBean});
        mailBean.setCcs(new MailAddressBean[]{receiverAddressBean});
        mailBean.setTextMessage("SenderReceiverTest");
        mailBean.setHtmlContent("<html><META http-equiv=Content-Type "
                + "content=\"text/html; charset=utf-8\">"
                + "<body><h1>Here is my photograph embedded in "
                + "this email.</h1><img src='cid:FreeFall.jpg'>"
                + "<h2>I'm flying!</h2></body></html>");
        mailBean.setSentDate("2018-09-21 19:01:59.0");
        GS.sendEmail(mailConfigSenderBean, mailBean);
        

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(1);
        }
        // Receive all email
        ArrayList<MailBean>  received = GR.receiveEmail(mailConfigReceiverBean);

        LOG.info(mailBean.toString());
        assertTrue(mailBean.equals(received.get(0)));

    }
}
