/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.RFC2822AddressParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author MC
 */
public class Gmail {

    Email email;
    String invalidEmailAddress;
    // Real programmers use logging
    private final static Logger LOG = LoggerFactory.getLogger(Gmail.class);

    /**
     * Gmail constructor without cc and HTML content
     *
     * @param sendEmailAddress (String) - sender Email Address
     * @param receiveEmailAddress (String[]) -array of receiver Email Address
     *
     */
    public Gmail(String sendEmailAddress,
            String[] receiveEmailAddress) {
        Boolean dataIsValid = true;
        if (!emaiIsValidl(sendEmailAddress)) {
            dataIsValid = false;
        }
        for (String emailAddress : receiveEmailAddress) {
            if (!emaiIsValidl(emailAddress)) {
                invalidEmailAddress = emailAddress;
                dataIsValid = false;
            }
        }
        if (dataIsValid) {
            this.email = Email.create().from(sendEmailAddress)
                    .to(receiveEmailAddress);
        } else {
            LOG.info("Receivers Email Addresses is not valid "+ invalidEmailAddress);
            throw new IllegalArgumentException("Receivers Email Addresses is not valid " + invalidEmailAddress);
        }

    }

    /**
     *
     * @param textMessage (String) - text Message of letter
     */
    public void addtextMessage(String textMessage) {
        this.email.textMessage(textMessage);
    }

    /**
     *
     * @param subject (String) - subject of letter
     */
    public void addSubject(String subject) {
        this.email.subject(subject);
    }

    /**
     * Add ccs to email
     *
     * @param ccs (MailAddressBean[]) - cc addresses
     */
    public void addCcs(MailAddressBean[] ccs) {
        if (ccs.length == 0)return;
        boolean dataIsValid = true;
        List<String> ccsString = new ArrayList<>();
        //validate all emails in cc array
        for (MailAddressBean cc : ccs) {
            ccsString.add(cc.getAddressName());
            if (emaiIsValidl(cc.getAddressName()) == false) {
                dataIsValid = false;
                 invalidEmailAddress = cc.getAddressName();

            }
            if (dataIsValid) {
                this.email.cc(ccsString.toArray(new String[ccsString.size()]));
            } else {
                LOG.info("CC Email Addresses is not valid " + invalidEmailAddress);
                throw new IllegalArgumentException("CC Email Addresses is not valid " + invalidEmailAddress);
            }
        }
    }

    /**
     * Add htmlContent to email
     *
     * @param sentDate
     */
    public void addSentDate(String sentDate) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(sentDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Gmail: SentDate Format is Invalid." + e);
        }

        this.email.sentDate(date);
    }

    /**
     * Add bccs to email
     *
     * @param bccs (MailAddressBean[]) - cc addresses
     */
    public void addBccs(MailAddressBean[] bccs) {
        boolean dataIsValid = true;
        List<String> bccsString = new ArrayList<>();
        //validate all emails in cc array
        for (MailAddressBean bcc : bccs) {
            bccsString.add(bcc.getAddressName());
            if (emaiIsValidl(bcc.getAddressName()) == false) {
                dataIsValid = false;
                invalidEmailAddress = bcc.getAddressName();

            }
            if (dataIsValid) {
                this.email.bcc(bccsString.toArray(new String[bccsString.size()]));
            } else {
                LOG.info("BCC Email Addresses is not valid " + invalidEmailAddress);
                throw new IllegalArgumentException("BCC Email Addresses is not valid " + invalidEmailAddress);
            }
        }
    }

    /**
     * Add htmlContent to email
     *
     * @param htmlContent (String[]) - cc addresses
     */
    public void addHtmlContent(String htmlContent) {
        this.email.htmlMessage(htmlContent);
    }

    /**
     * Add htmlContent to email
     *
     * @param path (String) - path to attachment
     */
    public void addAttachment(String path) {
        this.email.attachment(EmailAttachment.with().content(path));
    }

    /**
     * Add htmlContent to email
     *
     * @param path (String) - path to attachment
     */
    public void addEmbeddedAttachment(String path) {
        this.email.embeddedAttachment(EmailAttachment.with().content(new File(path)));
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Use the RFC2822AddressParser to validate that the email string could be a
     * valid address
     *
     * @param address
     * @return true is OK, false if not
     */
    private boolean emaiIsValidl(String address) {
        return RFC2822AddressParser.STRICT.parseToEmailAddress(address) != null;
    }
}
