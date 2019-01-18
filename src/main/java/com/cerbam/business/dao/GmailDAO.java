/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.dao;

import com.cerbam.business.interfaces.dao.EmailAddressDAO;
import com.cerbam.business.interfaces.dao.EmailAttachmentDAO;
import com.cerbam.business.interfaces.dao.EmailDAO;
import com.cerbam.business.interfaces.dao.EmailFolderDAO;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailAddressBean;
import com.cerbam.data.MailBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class execute connection between mailBean and DataBase 
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public class GmailDAO implements EmailDAO {

    private final static Logger LOG = LoggerFactory.getLogger(GmailDAO.class);

    private final String url = "jdbc:mysql://localhost:3306/EMAIL_APP_DB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private final String user = "CerbaM";
    private final String password = "javaapp";

    /**
     * Default constructor
     */
    public GmailDAO() {
        super();
    }

    /**
     * This method add MailBean to DB
     * 
     * @param mailBean (MailBean) MailBean to add to DB
     * @return int - ID of inserted row 
     * @throws SQLException in case of SQL Error 
     */
    @Override
    public int addEmail(MailBean mailBean) throws SQLException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        int id = 0;
        EmailAddressDAO gmailAddressDAO = new GmailAddressDAO();
        EmailAttachmentDAO gmailAttachmentDAO = new GmailAttachmentDAO();
        EmailFolderDAO GmailFolderDAO = new GmailFolderDAO();
        int senderID = 0;
        int folderID = 0;
        Timestamp sentDate = null;
        Timestamp receivedDate = null;
        int[] attachmentIds = new int[50];
        int[] embeddedAttachmentIds = new int[50];
        int[] receiverAddressIds = new int[50];
        int[] ccsIds = new int[50];
        int[] bccsIds = new int[50];

        //check ifsent date exist in mailBean
        // If YES convert it from String to Date
        if (mailBean.getSentDate() != null) {
            try {
                Date utilDate = dateFormat.parse(mailBean.getSentDate());
                long time = utilDate.getTime();
                sentDate = new Timestamp(time);
            } catch (ParseException e) {
                throw new IllegalArgumentException("addEmail: SentDate Format is Invalid." + e);
            }

        }

        //check if received Date date exist in mailBean
        // If YES convert it from String to Date
        if (mailBean.getReceivedDate() != null) {
            try {
                Date utilDate = dateFormat.parse(mailBean.getReceivedDate());
                long time = utilDate.getTime();
                sentDate = new Timestamp(time);
            } catch (ParseException e) {
                throw new IllegalArgumentException("addEmail: ReceivedDate Format is Invalid." + e);
            }
        }

        //add Sender EmailAddress to DB if it doesn't exist       
        try {
            senderID = gmailAddressDAO.addEmailAddress(mailBean.getSenderEmailAddress());
        } catch (SQLException ex) {
            LOG.debug(" addEmailAddress error", ex.getMessage());
            throw ex;
        }
        
        //SET Inbox folder by default
        if (mailBean.getFolder()== null ) {
            FolderBean folderBean = new FolderBean();
            folderBean.setFolderName("Inbox");
            mailBean.setFolder(folderBean);
         } 
        //add folder to DB if it doesn't exist    
        try {
            folderID = GmailFolderDAO.addFolder(mailBean.getFolder());
        } catch (SQLException ex) {
            LOG.debug(" addEmailAddress error", ex.getMessage());
            throw ex;
        }

        //add Attachments to DB
        if (mailBean.getAttachments() != null) {
            AttachmentBean[] attachments = mailBean.getAttachments();
            for (int i = 0; i < attachments.length; i++) {
                if (attachments[i] != null)
                attachmentIds[i] = gmailAttachmentDAO.addAttachment(attachments[i]);
            }
        }

        //add embedded Attachments to DB
        if (mailBean.getEmbeddedAttachments() != null) {
            AttachmentBean[] embeddedAttachments = mailBean.getEmbeddedAttachments();
            for (int i = 0; i < embeddedAttachments.length; i++) {
                if (embeddedAttachments[i] != null)
                embeddedAttachmentIds[i] = gmailAttachmentDAO.addAttachment(embeddedAttachments[i]);
            }
        }

        //add Receivers Email Addresses to DB 
        if (mailBean.getReceiverEmailAddresses() != null) {
            MailAddressBean[] emails = mailBean.getReceiverEmailAddresses();
            for (int i = 0; i < emails.length; i++) {
                try {
                    receiverAddressIds[i] = gmailAddressDAO.addEmailAddress(emails[i]);
                } catch (SQLException ex) {
                    LOG.debug("create error", ex.getMessage());
                    throw ex;
                }
            }
        }

        //add CC Email Addresses to DB 
        if (mailBean.getCcs() != null) {
            MailAddressBean[] emails = mailBean.getCcs();
            for (int i = 0; i < emails.length; i++) {
                try {
                    ccsIds[i] = gmailAddressDAO.addEmailAddress(emails[i]);
                } catch (SQLException ex) {
                    LOG.debug("create error", ex.getMessage());
                    throw ex;
                }
            }
        }

        //add BCC Email Addresses to DB 
        if (mailBean.getBccs() != null) {
            MailAddressBean[] emails = mailBean.getBccs();
            for (int i = 0; i < emails.length; i++) {
                try {
                    bccsIds[i] = gmailAddressDAO.addEmailAddress(emails[i]);
                } catch (SQLException ex) {
                    LOG.debug("create error", ex.getMessage());
                    throw ex;
                }
            }
        }

        String insertIntoEmails = "INSERT INTO EMAILS (SENDER_EMAILADRESS_ID, EMAIL_SUBJECT, TEXT_MESSAGE, PRIORITY, HTML_CONTENT, FOLDER_ID, SENT_DATE, RECEIVED_DATE) values (?, ?, ?, ?, ?, ?, ?, ?)";
        String insertIntoEmailsAttachments = "INSERT INTO EMAILS_ATTACHMENTS(EMAIL_ID, ATTACHMENTS_ID) VALUES (?, ?)";
        String insertIntoEmailsEmbeddedAttachments = "INSERT INTO EMAILS_EMBEDED_ATTACHMENTS(EMAIL_ID, ATTACHMENTS_ID) VALUES (?, ?)";
        String insertIntoEmailsAdresses = "INSERT INTO EMAILS_TO_EMAILADRESSES(EMAIL_ID, EMAILADRESSES_ID) VALUES (?, ?)";
        String insertIntoCCEmailsAdresses = "INSERT INTO EMAILS_CC_EMAILADRESSES(EMAIL_ID, EMAILADRESSES_ID) VALUES (?, ?)";
        String insertIntoBCCEmailsAdresses = "INSERT INTO EMAILS_BCC_EMAILADRESSES(EMAIL_ID, EMAILADRESSES_ID) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password); // You must use PreparedStatements to guard against SQL Injection
                ) {
            connection.setAutoCommit(false);
            try (PreparedStatement pStatement = connection.prepareStatement(insertIntoEmails, Statement.RETURN_GENERATED_KEYS);) {
                pStatement.setInt(1, senderID);
                pStatement.setString(2, mailBean.getSubject());
                pStatement.setString(3, mailBean.getTextMessage());
                pStatement.setInt(4, mailBean.getPriority());
                pStatement.setString(5, mailBean.getHtmlContent());
                pStatement.setInt(6, folderID);
                pStatement.setTimestamp(7, sentDate);
                pStatement.setTimestamp(8, receivedDate);
                pStatement.executeUpdate();
                ResultSet rs = pStatement.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                connection.commit();

            } catch (SQLException ex) {
                LOG.debug(" Email create error", ex.getMessage());
                connection.rollback();
                connection.setAutoCommit(true);
                throw ex;
            }

            //Add data to EMAILS_ATTACHMENTS bridging table
            try (PreparedStatement attachmentStatement = connection.prepareStatement(insertIntoEmailsAttachments, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement embeddedAttachmentStatement = connection.prepareStatement(insertIntoEmailsEmbeddedAttachments, Statement.RETURN_GENERATED_KEYS);) {
                for (int attachmentId : attachmentIds) {
                    if (attachmentId == 0) {
                        break;
                    }
                    attachmentStatement.setInt(1, id);
                    attachmentStatement.setInt(2, attachmentId);
                    attachmentStatement.addBatch();
                }

                for (int attachmentId : embeddedAttachmentIds) {
                    if (attachmentId == 0) {
                        break;
                    }
                    embeddedAttachmentStatement.setInt(1, id);
                    embeddedAttachmentStatement.setInt(2, attachmentId);
                    embeddedAttachmentStatement.addBatch();
                }
                attachmentStatement.executeBatch();
                embeddedAttachmentStatement.executeBatch();

                LOG.debug(" ALL EMAILS_ATTACHMENTS Was added !");
            } catch (SQLException ex) {
                LOG.debug(" EMAILS_ATTACHMENTS create error", ex.getMessage());
                connection.rollback();
                connection.setAutoCommit(true);
                throw ex;
            }

            try (PreparedStatement toStatement = connection.prepareStatement(insertIntoEmailsAdresses, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement ccStatement = connection.prepareStatement(insertIntoCCEmailsAdresses, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement bccStatement = connection.prepareStatement(insertIntoBCCEmailsAdresses, Statement.RETURN_GENERATED_KEYS);) {
                //Add data to EMAILS_TO_EMAILADRESSES bridging table
                for (int emailAddressId : receiverAddressIds) {
                    if (emailAddressId == 0) {
                        break;
                    }
                    toStatement.setInt(1, id);
                    toStatement.setInt(2, emailAddressId);
                    toStatement.addBatch();
                }

                //Add data to EMAILS_CC_EMAILADRESSES bridging table
                for (int emailAddressId : ccsIds) {
                    if (emailAddressId == 0) {
                        break;
                    }
                    ccStatement.setInt(1, id);
                    ccStatement.setInt(2, emailAddressId);
                    ccStatement.addBatch();
                }
                //Add data to EMAILS_BCC_EMAILADRESSES bridging table
                for (int emailAddressId : bccsIds) {
                    if (emailAddressId == 0) {
                        break;
                    }
                    bccStatement.setInt(1, id);
                    bccStatement.setInt(2, emailAddressId);
                    bccStatement.addBatch();
                }
                toStatement.executeBatch();
                ccStatement.executeBatch();
                bccStatement.executeBatch();
                LOG.debug("ALL EMAILS_XX_EMAILADRESSES Was added !");

            } catch (SQLException ex) {
                LOG.debug(" EMAILS_XX_EMAILADRESSES create error", ex.getMessage());
                connection.rollback();
                connection.setAutoCommit(true);
                throw ex;
            }
            connection.commit();
            connection.setAutoCommit(true);
        }
        
        mailBean.setID(id);
        return id;
    }

    /** This method gets email from DB in form of MAilBean
     * if EmailAddress already exists returns its id 
     * 
     * @param id (int) ID of Email from DB
     * @return MailBean - email from DB in form of MAilBean
     * @throws SQLException if Email does not exist
     */
    @Override
    public MailBean getEmailByID(int id) throws SQLException {
        MailBean mailBean = new MailBean();
        String selectQuery = "SELECT * "
                + " FROM email_app_db.EMAILS WHERE ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    mailBean = makeMailBean(resultSet);
                } else {
                    throw new SQLException("Mail was not found!");
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getEmailByID error", ex.getMessage());
            throw ex;
        }
        return mailBean;
    }

     /**
     * this method create MailBean
     * @param rs ResultSet representing all columns in Mail table 
     * @return (AttachmentBean) AttachmentBean - representing Mail Bean in BD
     * @throws SQLException if SQL statement is not correct or else SQL error
     */
    private MailBean makeMailBean(ResultSet rs) throws SQLException {
        MailBean mailBean = new MailBean();
        List<MailAddressBean> receiversAddressBeans = new ArrayList<>();

        EmailAddressDAO gmailAddressDAO = new GmailAddressDAO();
        EmailAttachmentDAO gmailAttachmentDAO = new GmailAttachmentDAO();
        EmailFolderDAO GmailFolderDAO = new GmailFolderDAO();
        //add sender address bean 
        MailAddressBean senderAddress;
        senderAddress = new MailAddressBean(gmailAddressDAO.getMailAddressBeanByID(rs.getInt("SENDER_EMAILADRESS_ID")));
        mailBean.setSenderEmailAddress(senderAddress);
        mailBean.setID(rs.getInt("ID"));
        mailBean.setSubject(rs.getString("EMAIL_SUBJECT"));
        mailBean.setTextMessage(rs.getString("TEXT_MESSAGE"));
        mailBean.setPriority(rs.getInt("PRIORITY"));
        mailBean.setHtmlContent(rs.getString("HTML_CONTENT"));

        //add folder address bean 
        FolderBean folderBean;
        int folderID = rs.getInt("FOLDER_ID");
        folderBean = new FolderBean(GmailFolderDAO.getFolderBeanByID(folderID));
        mailBean.setFolder(folderBean);

        //send dates
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        if (rs.getDate("SENT_DATE") != null) {
            mailBean.setSentDate(dateFormat.format(rs.getTimestamp("SENT_DATE")));
        }
        if (rs.getDate("RECEIVED_DATE") != null) {
            mailBean.setReceivedDate(dateFormat.format(rs.getTimestamp("RECEIVED_DATE")));
        }

        //set attachments
        AttachmentBean[] attachmentBeansCopy = gmailAttachmentDAO.getAttachmentsByMailID(rs.getInt("ID"));
        if (attachmentBeansCopy != null) {
            AttachmentBean[] attachmentBeans = Arrays.copyOf(attachmentBeansCopy, attachmentBeansCopy.length);
            mailBean.setAttachments(attachmentBeans);
        }

        //set  Embedded attachments
        AttachmentBean[] embeddedAttachmentBeansCopy = gmailAttachmentDAO.getEmbeddedAttachmentsByMailID(rs.getInt("ID"));
        if (embeddedAttachmentBeansCopy != null) {
            AttachmentBean[] embeddedAttachmentBeans = Arrays.copyOf(embeddedAttachmentBeansCopy, embeddedAttachmentBeansCopy.length);
            mailBean.setEmbeddedAttachments(embeddedAttachmentBeans);
        }
        //set  receiver eddresses
        MailAddressBean[] receiverMailAddressBeansCopy = gmailAddressDAO.getReceiverMailAddressBeansByMailID(rs.getInt("ID"));
        if (receiverMailAddressBeansCopy != null) {
            MailAddressBean[] receiverMailAddressBeans = Arrays.copyOf(receiverMailAddressBeansCopy, receiverMailAddressBeansCopy.length);
            mailBean.setReceiverEmailAddresses(receiverMailAddressBeans);
        }
        //set  CC eddresses
        MailAddressBean[] CCMailAddressBeansCopy = gmailAddressDAO.getCCMailAddressBeansByMailID(rs.getInt("ID"));
        if (CCMailAddressBeansCopy != null) {
            MailAddressBean[] CCMailAddressBeans = Arrays.copyOf(CCMailAddressBeansCopy, CCMailAddressBeansCopy.length);
            mailBean.setCcs(CCMailAddressBeans);
        }
        //set  BCC eddresses
        MailAddressBean[] BCCMailAddressBeansCopy = gmailAddressDAO.getBCCMailAddressBeansByMailID(rs.getInt("ID"));
        if (BCCMailAddressBeansCopy != null) {
            MailAddressBean[] BCCMailAddressBeans = Arrays.copyOf(BCCMailAddressBeansCopy, BCCMailAddressBeansCopy.length);
            mailBean.setBccs(BCCMailAddressBeans);
        }
        return mailBean;
    }

    /**
     * This method delete All receiver addresses of particular Email
     * @param mailId (int) - Email ID of Email to delete from
     * @return (int) - number of deleted rows 
     * @throws SQLException if case of SQL error
     */
    @Override
    public int deleteReceiverAddressByMailID(int mailId) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM EMAILS_TO_EMAILADRESSES WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, mailId);
            result = deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
            throw ex;
        }

        return result;
    }

    /**
     * This method delete All CC addresses of particular Email
     * 
     * @param mailId (int) - Email ID of Email to delete from
     * @return (int) - number of deleted rows 
     * @throws SQLException if case of SQL error
     */
    @Override
    public int deleteCCAddressByMailID(int mailId) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM EMAILS_CC_EMAILADRESSES WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, mailId);
            result = deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
            throw ex;
        }

        return result;
    }

    /**
     * This method delete All BCC addresses of particular Email
     * 
     * @param mailId (int) - Email ID of Email to delete from
     * @return (int) - number of deleted rows 
     * @throws SQLException if case of SQL error
     */
    @Override
    public int deleteBCCAddressByMailID(int mailId) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM EMAILS_BCC_EMAILADRESSES WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, mailId);
            result = deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
            throw ex;
        }

        return result;
    }

    /**
     * This method delete All Attachments of particular Email
     * 
     * @param mailId (int) - Email ID of Attachments to delete from
     * @return (int) - number of deleted rows 
     * @throws SQLException if case of SQL error
     */
    @Override
    public int deleteAttachmentByMailID(int mailId) throws SQLException {
        int result = 0;
        GmailAttachmentDAO gmailAttachmentDAO = new GmailAttachmentDAO();
        ArrayList<Integer> ids = new ArrayList<>();
        String deleteQuery = "DELETE FROM EMAILS_ATTACHMENTS WHERE EMAIL_ID = ? ;";
        String selectQuery = "SELECT ATTACHMENTS_ID FROM EMAILS_ATTACHMENTS WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            pStatement.setInt(1, mailId);
            deleteStatement.setInt(1, mailId);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    ids.add(resultSet.getInt("ATTACHMENTS_ID"));
                }
                result = deleteStatement.executeUpdate();
                for (Integer id : ids) {
                    gmailAttachmentDAO.deleteAttachmentByID(id);
                }
            } catch (SQLException ex) {
                LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
                throw ex;
            }
        } catch (SQLException ex) {
            LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
            throw ex;
        }

        return result;
    }

    /**
     * This method delete All Embedded Attachments of particular Email
     * 
     * @param mailId (int) - Email ID of Embedded Attachments to delete from
     * @return (int) - number of deleted rows 
     * @throws SQLException if case of SQL error
     */
    @Override
    public int deleteEmbeddedAttachmentByMailID(int mailId) throws SQLException {
        int result = 0;
        GmailAttachmentDAO gmailAttachmentDAO = new GmailAttachmentDAO();
        ArrayList<Integer> ids = new ArrayList<>();
        String deleteQuery = "DELETE FROM EMAILS_EMBEDED_ATTACHMENTS WHERE EMAIL_ID = ? ;";
        String selectQuery = "SELECT ATTACHMENTS_ID FROM EMAILS_EMBEDED_ATTACHMENTS WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            pStatement.setInt(1, mailId);
            deleteStatement.setInt(1, mailId);
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    ids.add(resultSet.getInt("ATTACHMENTS_ID"));
                }
                result = deleteStatement.executeUpdate();
                for (Integer id : ids) {
                    gmailAttachmentDAO.deleteAttachmentByID(id);
                }
            } catch (SQLException ex) {
                LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
                throw ex;
            }
        } catch (SQLException ex) {
            LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
            throw ex;
        }

        return result;
    }

    /**
     * This method delete Email from DB
     * 
     * @param mailId (int) - Email ID 
     * @return (int) - number of deleted rows (0 if was not found)
     * @throws SQLException if case of SQL error
     */
    @Override
    public int deleteMailByID(int mailId) throws SQLException {
        int result = 0;
        String deleteQuery = "DELETE FROM EMAILS WHERE ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, mailId);
            deleteReceiverAddressByMailID(mailId);
            deleteCCAddressByMailID(mailId);
            deleteBCCAddressByMailID(mailId);
            deleteAttachmentByMailID(mailId);
            deleteEmbeddedAttachmentByMailID(mailId);
            result = deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            LOG.debug("deleteAttachmentByMailID error", ex.getMessage());
            throw ex;
        }

        return result;
    }
    
        /**
     * This method gets Array of All MailAddressBeans
     *
     * @return MailAddressBean[]) - Array of MailAddressBeans representing all
     * Mail addresses
     *
     * @throws SQLException in case of SQL error
     */
    @Override
    public MailBean[] getAllEmails() throws SQLException {
        List<MailBean> AllEmails = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.EMAILS";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    AllEmails.add(makeMailBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getBCCMailAddressBeansByMailID error", ex.getMessage());
            throw ex;
        }
        return AllEmails.toArray(new MailBean[AllEmails.size()]);

    }
    
     /**
     * This method gets Array of All MailBeans from particular folder
     *
     * @param folderBean (FolderBean) - folder Bean to search in 
     * @return MailBean[]) - Array of MailBean representing all
     * Mails in the folder
     *
     * @throws SQLException in case of SQL error
     */
    @Override
    public ObservableList<MailBean> getAllEmailAddressesFromFolder(FolderBean folderBean) throws SQLException {
        ObservableList<MailBean> AllEmails = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM email_app_db.EMAILs WHERE FOLDER_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1,folderBean.getID());
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    AllEmails.add(makeMailBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getAllEmailAddressesFromFolder error", ex.getMessage());
            throw ex;
        }
        LOG.debug("EMAILS FORM FOLDER:" + folderBean.getFolderName() + "\nEAMILS " + AllEmails.toString()  );
        return AllEmails;

    }
}
