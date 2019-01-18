/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.dao;

import com.cerbam.business.interfaces.dao.EmailAttachmentDAO;
import com.cerbam.data.AttachmentBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class execute connection between mailAttachmentBean and DataBase 
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public class GmailAttachmentDAO implements EmailAttachmentDAO {

    private final static Logger LOG = LoggerFactory.getLogger(GmailAttachmentDAO.class);

    private final String url = "jdbc:mysql://localhost:3306/EMAIL_APP_DB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "CerbaM";
    private final String password = "javaapp";

    /**
     * Default constructor
     */
    public GmailAttachmentDAO() {
        super();
    }

    /**
     * This method adds attachmentBean to DB 
     *
     * @param attachmentBean (attachmentBean) to add to DataBase
     * @return int - id of created AttachmentBean
     * @throws SQLException - if mailAddressBean cannot be created
     */
    @Override
    public int addAttachment(AttachmentBean attachmentBean) throws SQLException {
        int id;
        /*
        int id = findAttachmentID(attachmentBean.getPath(),attachmentBean.getAttachmentArray());
        if (id != 0) {
            attachmentBean.setID(id);
            return id;
        }
         */
        String sql = "INSERT INTO email_app_db.ATTACHMENTS (ATTACHMENT_NAME, ATTACHMENT_DATA) values (?, ?) ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            LOG.debug("New Attachmnet is " + attachmentBean.getPath());
            pStatement.setString(1, attachmentBean.getPath());
            LOG.debug("New Attachmnet Data is " + attachmentBean.getAttachmentArray());
            pStatement.setBytes(2, attachmentBean.getAttachmentArray());

            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                id = -1;
                if (rs.next()) {
                    id = rs.getInt(1);
                } else {
                    throw new SQLException("MailAddress was not found!");
                }
                attachmentBean.setID(id);
                LOG.debug("New record ID is " + id);
            }
        } catch (SQLException ex) {
            LOG.debug("create error", ex.getMessage());
            throw ex;
        }
        return id;
    }

    /**
     * This method finds Attachment in DAta Base
     * @param attachmentName (String) - Attachment Name
     * @param attachmentdata (byte[]) Array of bytes representing data
     * @return (int) ID of inserted Attachment
     * @throws SQLException if Attachment does not exists in DB
     */
    public int findAttachmentID(String attachmentName, byte[] attachmentdata) throws SQLException {

        // If there is no record with the desired id then this will be returned
        // as a null pointer
        int id = 0;

        String selectQuery = "SELECT ID from email_app_db.ATTACHMENTS WHERE ATTACHMENT_NAME = ? AND ATTACHMENT_DATA = ? ;";

        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection
                        .prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setString(1, attachmentName);
            pStatement.setBytes(2, attachmentdata);
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("ID");
                } else {
                    throw new SQLException("Attachment was not found!");
                }
            }
        }
        LOG.info("Found id : " + id);
        return id;
    }

    /**
     * This method gets attachment by its ID
     * @param id (int) - Attachment ID
     * @return (AttachmentBean) AttachmentBean representing Attachmnet
     * @throws SQLException if Attachment does not exist
     */
    @Override
    public AttachmentBean getAttachmentByID(int id) throws SQLException {
        AttachmentBean attachmentBean = new AttachmentBean();
        String selectQuery = "SELECT * FROM email_app_db.ATTACHMENTS WHERE ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    attachmentBean = makeAttachmentBean(resultSet);
                }else {
                    throw new SQLException("Attachment was not found!");
                }
               
            }
        } catch (SQLException ex) {
            LOG.debug("findAttachmentByID error", ex.getMessage());
            throw ex;
        }
        return attachmentBean;
    }

    /**
     * this method create AttachmentBean
     * @param rs ResultSet representing all columns in Attachment table 
     * @return (AttachmentBean) AttachmentBean - representing Attachment Bean in BD
     * @throws SQLException if SQL statement is not correct or else SQL error
     */
    private AttachmentBean makeAttachmentBean(ResultSet rs) throws SQLException {
        AttachmentBean attachmentBean = new AttachmentBean();
        attachmentBean.setPath(rs.getString("ATTACHMENT_NAME"));
        attachmentBean.setAttachmentArray(rs.getBytes("ATTACHMENT_DATA"));
        attachmentBean.setID(rs.getInt("ID"));

        return attachmentBean;
    }

    /**
     * This method get AttachmentsBean Array from DB
     * 
     * @param mailId (int)  - ID of emailBean which contains Attachment 
     * @return AttachmentBean[] - array of AttachmentBeans
     * @throws  SQLException in case of SQL error
     */
    @Override
    public AttachmentBean[] getAttachmentsByMailID(int mailId) throws SQLException {
        List<AttachmentBean> attachmentBeans = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.ATTACHMENTS"
                + " JOIN email_app_db.EMAILS_ATTACHMENTS ON (ATTACHMENTS_ID = ATTACHMENTS.ID) WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, mailId);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    attachmentBeans.add(makeAttachmentBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("findAttachmentByID error", ex.getMessage());
            throw ex;
        }
        return attachmentBeans.toArray(new AttachmentBean[attachmentBeans.size()]);
    }

    /**
     * This method get Embedded AttachmentsBean Array from DB
     * 
     * @param mailId (int)  - ID of emailBean which contains  Embedded Attachment 
     * @return AttachmentBean[] - array of  Embedded AttachmentBeans
     * @throws  SQLException in case of SQL error
     */
    @Override
    public AttachmentBean[] getEmbeddedAttachmentsByMailID(int mailId) throws SQLException {
        List<AttachmentBean> EmbeddedAttachments = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.ATTACHMENTS"
                + " JOIN email_app_db.EMAILS_EMBEDED_ATTACHMENTS ON (ATTACHMENTS_ID = ATTACHMENTS.ID) WHERE EMAIL_ID = ?;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, mailId);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    EmbeddedAttachments.add(makeAttachmentBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("findAttachmentByID error", ex.getMessage());
            throw ex;
        }
        return EmbeddedAttachments.toArray(new AttachmentBean[EmbeddedAttachments.size()]);
    }

    /**
     * This method delete  Embedded AttachmentsBean from DB
     * 
     * @param mailId (int)  - ID of emailBean which contains  Embedded Attachment 
     * @return (int) - number of deleted rows 
     * @throws SQLException in case of SQL error
     */
    @Override
    public int deleteAttachmentByMailID(int mailId) throws SQLException {
        int result = 0;
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
                    deleteAttachmentByID(id);
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
     * This method delete  Embedded AttachmentsBean from DB by its ID
     * 
     * @param id (int)  - ID of AttachmentsBean in DB
     * @return (int) - number of deleted rows 
     * @throws SQLException in case of SQL error
     */
    @Override
    public int deleteAttachmentByID(int id) throws SQLException {
        int results = 0;
        String deleteQuery = "DELETE FROM ATTACHMENTS WHERE ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(deleteQuery);) {
            pStatement.setInt(1, id);

            results = pStatement.executeUpdate();

        } catch (SQLException ex) {
            LOG.debug("findAttachmentByID error", ex.getMessage());
            throw ex;
        }
        return results;
    }
}
