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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */
public class MailBean {

    private final static Logger LOG = LoggerFactory.getLogger(MailBean.class);

    private int ID;
    private MailAddressBean senderEmailAddress;
    private MailAddressBean[] receiverEmailAddresses;
    private String subject;
    private String textMessage;
    private int priority;
    private MailAddressBean[] ccs;
    private MailAddressBean[] bccs;
    private String htmlContent;
    private AttachmentBean[] embeddedAttachments;
    private AttachmentBean[] attachments;
    private String sentDate;
    private String receivedDate;
    private FolderBean folder;

    /**
     * Default Constructor
     */
    public MailBean() {
    }

    public FolderBean getFolder() {
        return this.folder;
    }

    public void setFolder(FolderBean folderBean) {
        this.folder = folderBean;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public MailAddressBean getSenderEmailAddress() {
        if (this.senderEmailAddress != null) {
            return this.senderEmailAddress;
        }
        return null;
    }

    public void setSenderEmailAddress(MailAddressBean mailAddressBean) {
        if (mailAddressBean == null) {
            this.senderEmailAddress = null;
        } else {
            this.senderEmailAddress = new MailAddressBean(mailAddressBean);
        }
    }

    public MailAddressBean[] getReceiverEmailAddresses() {
        if (this.receiverEmailAddresses == null) {
            return null;
        } else {
            return this.receiverEmailAddresses;
        }
    }

    public void setReceiverEmailAddresses(MailAddressBean[] receiverEmailAddresses) {
        if (receiverEmailAddresses != null) {
            this.receiverEmailAddresses = new MailAddressBean[receiverEmailAddresses.length];
            System.arraycopy(receiverEmailAddresses, 0, this.receiverEmailAddresses, 0, receiverEmailAddresses.length);
        } else {
            this.receiverEmailAddresses = null;
        }
    }

    public String getSubject() {
        if (this.subject != null) {
            return this.subject;
        }
        return null;
    }

    public void setSubject(String subject) {
        if (subject == null) {
            this.subject = null;
        } else {
            this.subject = new String(subject);
        }
    }

    public String getTextMessage() {
        if (this.textMessage != null) {
            return textMessage;
        }
        return null;
    }

    public void setTextMessage(String textMessage) {
        if (textMessage == null) {
            this.textMessage = null;
        } else {
            this.textMessage = textMessage;
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public MailAddressBean[] getCcs() {
        if (this.ccs == null) {
            return null;
        }
        return this.ccs;
    }

    public void setCcs(MailAddressBean[] ccs) {
        if (ccs != null) {
            this.ccs = new MailAddressBean[ccs.length];
            for (int i = 0; i < this.ccs.length; i++) {
                this.ccs[i] = new MailAddressBean(ccs[i]);
            }
        } else {
            this.ccs = null;
        }
    }

    public MailAddressBean[] getBccs() {
        if (this.bccs == null) {
            return null;
        }

        return this.bccs;

    }

    public void setBccs(MailAddressBean[] bccs) {
        if (bccs != null) {
            this.bccs = new MailAddressBean[bccs.length];
            for (int i = 0; i < this.bccs.length; i++) {
                this.bccs[i] = new MailAddressBean(bccs[i]);
            }
        } else {
            this.bccs = null;
        }
    }

    public String getHtmlContent() {
        if (this.htmlContent != null) {
            return this.htmlContent;
        }
        return null;
    }

    public void setHtmlContent(String htmlContent) {
        if (htmlContent == null) {
            this.htmlContent = null;
        } else {
            this.htmlContent = htmlContent;
        }
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
               if (embeddedAttachments[i] != null)  this.embeddedAttachments[i] = embeddedAttachments[i].getCopy();
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
                if (attachments[i] != null) this.attachments[i] = attachments[i].getCopy();
            }
        } else {
            this.attachments = null;
        }
    }

    public String getSentDate() {
        if (this.sentDate != null) {
            return this.sentDate;
        }
        return null;
    }

    public void setSentDate(String sentDate) {
        if (sentDate == null) {
            this.sentDate = null;
        } else {
            this.sentDate = sentDate;
        }
    }

    public String getReceivedDate() {
        if (this.receivedDate != null) {
            return this.sentDate;
        }
        return null;
    }

    public void setReceivedDate(String receivedDate) {
        if (receivedDate == null) {
            this.receivedDate = null;
        } else {
            this.receivedDate = receivedDate;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.senderEmailAddress);
        hash = 67 * hash + Objects.hashCode(this.receiverEmailAddresses);
        hash = 67 * hash + Objects.hashCode(this.subject);
        hash = 67 * hash + Objects.hashCode(this.textMessage);
        hash = 67 * hash + Arrays.deepHashCode(this.ccs);
        hash = 67 * hash + Objects.hashCode(this.htmlContent);
        hash = 67 * hash + Arrays.deepHashCode(this.embeddedAttachments);
        hash = 67 * hash + Arrays.deepHashCode(this.attachments);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            LOG.info("obj+++++++++++++++++++++++++ NULL");
            return false;
        }
        if (getClass() != obj.getClass()) {
            LOG.info("getClass+++++++++++++++++++++++++ FALSE");
            return false;
        }
        final MailBean other = (MailBean) obj;
        if (this.senderEmailAddress == null ? other.senderEmailAddress != null : !this.senderEmailAddress.equals(other.senderEmailAddress)) {
            LOG.info("senderEmailAddress+++++++++++++++++++++++++ FALSE");
            LOG.info("sender1:" + this.senderEmailAddress);
            LOG.info("sender2:" + other.senderEmailAddress);
            return false;
        }
        if (!Arrays.equals(this.receiverEmailAddresses, other.receiverEmailAddresses)) {
            LOG.info("receiverEmailAddresses+++++++++++++++++++++++++ FALSE");
            return false;
        }
        if (this.sentDate == null ? other.sentDate != null : !this.sentDate.equals(other.sentDate)) {
            LOG.info("sentDate+++++++++++++++++++++++++ FALSE");
            LOG.info("1" + this.sentDate);
            LOG.info("2" + other.sentDate);
            return false;
        }
        if (this.subject == null ? other.subject != null : !this.subject.equals(other.subject)) {
            LOG.info("subject+++++++++++++++++++++++++ FALSE");
            return false;
        }
        if (this.textMessage == null ? other.textMessage != null : !this.textMessage.equals(other.textMessage)) {
            LOG.info("textMessage+++++++++++++++++++++++++ FALSE");
            return false;
        }
        if (this.htmlContent == null ? other.htmlContent != null : !this.htmlContent.equals(other.htmlContent)) {
            LOG.info("htmlContent+++++++++++++++++++++++++ FALSE");
            return false;
        }

        if (!Arrays.equals(this.embeddedAttachments, other.embeddedAttachments)) {
            LOG.info("embeddedAttachments+++++++++++++++++++++++++ FALSE");
            return false;
        }
        if (!Arrays.equals(this.attachments, other.attachments)) {
            LOG.info("attachments+++++++++++++++++++++++++ FALSE");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MailBean{" + 
                "\nID=" + 
                this.getID() +                 
                "\nsendEmailAddress=" + 
                getSenderEmailAddress() + 
                ",\n receiveEmailAddress=" + 
                Arrays.toString(getReceiverEmailAddresses()) + 
                ",\n subject=" + 
                getSubject() + 
                ",\n textMessage=" + 
                getTextMessage() + 
                ",\n priority=" + 
                getPriority() + 
                ",\n ccs=" + 
                Arrays.toString(getCcs()) + 
                ",\n bccs=" + 
                Arrays.toString(getBccs()) + 
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

}
