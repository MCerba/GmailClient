/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */
public class MailFXBean {

    private final static Logger LOG = LoggerFactory.getLogger(MailFXBean.class);

    private IntegerProperty ID;
    private StringProperty senderEmailAddress;
    private StringProperty receiverEmailAddresses;
    private StringProperty subject;
    private StringProperty textMessage;
    private int priority;
    private StringProperty ccs;
    private StringProperty bccs;
    private StringProperty htmlContent;
    private AttachmentBean[] embeddedAttachments;
    private AttachmentBean[] attachments;
    private StringProperty sentDate;
    private StringProperty receivedDate;
    private FolderBean folder;

    /**
     * Default Constructor
     */
    public MailFXBean() {
        super();
        this.ID = new SimpleIntegerProperty(0);
        this.senderEmailAddress = new SimpleStringProperty("");
        this.receiverEmailAddresses = new SimpleStringProperty("");
        this.subject = new SimpleStringProperty("");
        this.textMessage = new SimpleStringProperty("");
        this.ccs = new SimpleStringProperty("");
        this.bccs = new SimpleStringProperty("");
        this.htmlContent = new SimpleStringProperty("");
        this.sentDate = new SimpleStringProperty("");
        this.receivedDate = new SimpleStringProperty("");

    }

    public FolderBean getFolder() {
        return this.folder;
    }

    public void setFolder(FolderBean folderBean) {
        if (folderBean != null) {
            this.folder = new FolderBean(folderBean);
        } else {
            this.folder = null;
        }
    }

    public int getID() {
        if (this.ID != null) {
            return this.ID.get();
        }
        return 0;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public IntegerProperty idProperty() {
        return this.ID;
    }

    public String getSenderEmailAddress() {

            return this.senderEmailAddress.get();

    }

    public void setSenderEmailAddress(String mailAddressString) {
        if (mailAddressString == null) {
            this.senderEmailAddress.set(null);
        } else {
            this.senderEmailAddress.set(mailAddressString);
        }
    }

    public StringProperty senderEmailAddressProperety() {
        if (this.senderEmailAddress == null) {
            return null;
        } else {
            return this.senderEmailAddress;
        }
    }

    public String getReceiverEmailAddresses() {

            return this.receiverEmailAddresses.get();

    }

    public void setReceiverEmailAddresses(String receiverEmailAddresses) {
        if (receiverEmailAddresses != null) {
            this.receiverEmailAddresses.set(receiverEmailAddresses);
        } else {
            this.receiverEmailAddresses = null;
        }
    }

    public StringProperty receiverEmailAddressesProperety() {
        if (this.receiverEmailAddresses == null) {
            return null;
        } else {
            return this.receiverEmailAddresses;
        }
    }

    public String getSubject() {
        if (this.subject != null) {
            return this.subject.get();
        }
        return null;
    }

    public void setSubject(String subject) {

            this.subject.set(subject);

    }

    public StringProperty subjectProperety() {
        if (this.subject != null) {
            return this.subject;
        }
        return null;
    }

    public String getTextMessage() {
        if (this.textMessage != null) {
            return textMessage.get();
        }
        return null;
    }

    public void setTextMessage(String textMessage) {
        if (textMessage == null) {
            this.textMessage = null;
        } else {
            this.textMessage.set(textMessage);
        }
    }

    public StringProperty textMessageProperty() {
        if (this.textMessage != null) {
            return textMessage;
        }
        return null;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCcs() {
        if (this.ccs != null) {
            return this.ccs.get();
        }
        return null;
    }

    public void setCcs(String ccs) {
        if (ccs != null) {
            this.ccs.set(ccs);
        } else {
            this.ccs = null;
        }
    }

    public StringProperty ccsProperty() {
        if (this.ccs == null) {
            return null;
        }
        return this.ccs;
    }

    public String getBccs() {
        if (this.bccs != null) {
            return this.bccs.get();
        }
        return null;
    }

    public void setBccs(String bccs) {
        if (bccs != null) {
            this.bccs.set(bccs);
        } else {
            this.bccs = null;
        }
    }

    public StringProperty bccsProperty() {
        if (this.bccs == null) {
            return null;
        }
        return this.bccs;
    }

    public String getHtmlContent() {
        if (this.htmlContent != null) {
            return this.htmlContent.get();
        }
        return null;
    }

    public void setHtmlContent(String htmlContent) {
        if (htmlContent == null) {
            this.htmlContent = null;
        } else {
            this.htmlContent.set(htmlContent);
        }
    }

    public StringProperty htmlContentProprty() {
        if (this.htmlContent != null) {
            return this.htmlContent;
        }
        return null;
    }

    public AttachmentBean[] getEmbeddedAttachments() {
        if (this.embeddedAttachments == null) {
            return null;
        }

        return this.embeddedAttachments;

    }

    public void setEmbeddedAttachments(AttachmentBean[] embeddedAttachments) {
        if (embeddedAttachments != null) {
            this.embeddedAttachments = new AttachmentBean[embeddedAttachments.length];
            for (int i = 0; i < embeddedAttachments.length; i++) {
                if (embeddedAttachments[i] != null) {
                    this.embeddedAttachments[i] = embeddedAttachments[i].getCopy();
                }
            }
        } else {
            this.embeddedAttachments = null;
        }
    }

    public AttachmentBean[] getAttachments() {
        if (this.attachments == null) {
            return null;
        }
        return this.attachments;

    }

    public void setAttachments(AttachmentBean[] attachments) {
        if (attachments != null) {
            this.attachments = new AttachmentBean[attachments.length];
            for (int i = 0; i < attachments.length; i++) {
                if (attachments[i] != null) {
                    this.attachments[i] = attachments[i].getCopy();
                }
            }
        } else {
            this.attachments = null;
        }
    }

    public String getSentDate() {
        if (this.sentDate != null) {
            return this.sentDate.get();
        }
        return null;
    }

    public void setSentDate(String sentDate) {
        if (sentDate == null) {
            this.sentDate = null;
        } else {
            this.sentDate.set(sentDate);
        }
    }

    public StringProperty sentDateProperty() {
        if (this.sentDate == null) {
            return null;
        } else {
            return this.sentDate;
        }
    }

    public String getReceivedDate() {
        if (this.receivedDate != null) {
            return this.sentDate.get();
        }
        return null;
    }

    public void setReceivedDate(String receivedDate) {
        if (receivedDate == null) {
            this.receivedDate = null;
        } else {
            this.receivedDate.set(receivedDate);
        }
    }

    public StringProperty receivedDateProperty() {
        if (this.receivedDate == null) {
            return null;
        } else {
            return this.receivedDate;
        }
    }

    @Override
    public String toString() {
        return "MailBean{" + 
                "\nID=" + 
                this.getID() +   
                "\nsendEmailAddress=" + 
                getSenderEmailAddress() + 
                ",\n receiveEmailAddress=" + 
                getReceiverEmailAddresses() + 
                ",\n subject=" + 
                getSubject() + 
                ",\n textMessage=" + 
                getTextMessage() + 
                ",\n priority=" + 
                getPriority() + 
                ",\n ccs=" + 
                getCcs() + 
                ",\n bccs=" + 
                getBccs() + 
                ",\n htmlContent=" + 
                getHtmlContent() + 
                ",\n embeddedAttachment=" + 
                Arrays.toString(embeddedAttachments) + ",\n attachment=" + 
                Arrays.toString(attachments) + 
                ",\n Folder = " +
                this.getFolder() +
                 ",\n Send date = " +
                this.sentDate +
                ",\n Received date = " +
                this.receivedDate +
                '}';
        
    }

    public MailFXBean makeCopy() {
        MailFXBean newMailFXBean = new MailFXBean();
        newMailFXBean.setFolder(this.getFolder());
        newMailFXBean.setID(this.getID());
        newMailFXBean.setSenderEmailAddress(this.getSenderEmailAddress());
        newMailFXBean.setReceiverEmailAddresses(this.getReceiverEmailAddresses());
        newMailFXBean.setSubject(this.getSubject());
        newMailFXBean.setTextMessage(this.getTextMessage());
        newMailFXBean.setTextMessage(this.getTextMessage());
        newMailFXBean.setPriority(this.getPriority());
        newMailFXBean.setCcs(this.getCcs());
        newMailFXBean.setBccs(this.getBccs());
        newMailFXBean.setHtmlContent(this.getHtmlContent());
        newMailFXBean.setEmbeddedAttachments(this.getEmbeddedAttachments());
        newMailFXBean.setAttachments(this.getAttachments());
        newMailFXBean.setSentDate(this.getSentDate());
        newMailFXBean.setReceivedDate(this.getReceivedDate());

        return newMailFXBean;
    }

}
