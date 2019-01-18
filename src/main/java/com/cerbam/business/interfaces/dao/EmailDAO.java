/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces.dao;

import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailBean;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * This class execute connection between mailBean and DataBase
 *
 * @author Cerba Mihail
 * @version 1.0
 */
public interface EmailDAO {

    /**
     * This method add MailBean to DB
     *
     * @param mailBean (MailBean) MailBean to add to DB
     * @return int - ID of inserted row
     * @throws SQLException in case of SQL Error
     */
    public int addEmail(MailBean mailBean) throws SQLException;

    /**
     * This method gets email from DB in form of MAilBean if EmailAddress
     * already exists returns its id
     *
     * @param id (int) ID of Email from DB
     * @return MailBean - email from DB in form of MAilBean
     * @throws SQLException if Email does not exist
     */
    public MailBean getEmailByID(int id) throws SQLException;

    /**
     * This method delete All receiver addresses of particular Email
     *
     * @param mailId (int) - Email ID of Email to delete from
     * @return (int) - number of deleted rows
     * @throws SQLException if case of SQL error
     */
    public int deleteReceiverAddressByMailID(int mailId) throws SQLException;

    /**
     * This method delete All CC addresses of particular Email
     *
     * @param mailId (int) - Email ID of Email to delete from
     * @return (int) - number of deleted rows
     * @throws SQLException if case of SQL error
     */
    public int deleteCCAddressByMailID(int mailId) throws SQLException;

    /**
     * This method delete All BCC addresses of particular Email
     *
     * @param mailId (int) - Email ID of Email to delete from
     * @return (int) - number of deleted rows
     * @throws SQLException if case of SQL error
     */
    public int deleteBCCAddressByMailID(int mailId) throws SQLException;

    /**
     * This method delete All Attachments of particular Email
     *
     * @param mailId (int) - Email ID of Attachments to delete from
     * @return (int) - number of deleted rows
     * @throws SQLException if case of SQL error
     */
    public int deleteAttachmentByMailID(int mailId) throws SQLException;

    /**
     * This method delete Email from DB
     *
     * @param mailId (int) - Email ID
     * @return (int) - number of deleted rows (0 if was not found)
     * @throws SQLException if case of SQL error
     */
    public int deleteMailByID(int mailId) throws SQLException;

    /**
     * This method delete All Embedded Attachments of particular Email
     *
     * @param mailId (int) - Email ID of Embedded Attachments to delete from
     * @return (int) - number of deleted rows
     * @throws SQLException if case of SQL error
     */
    public int deleteEmbeddedAttachmentByMailID(int mailId) throws SQLException;
    
        /**
     * This method gets Array of All MailBeans
     *
     * @return MailBean[]) - Array of MailBeans representing all
     * Mail addresses
     *
     * @throws SQLException in case of SQL error
     */
    public MailBean[] getAllEmails() throws SQLException;
    
         /**
     * This method gets Array of All MailBeans from particular folder
     *
     * @param folderBean (FolderBean) - folder Bean to search in 
     * @return MailBean[]) - Array of MailBean representing all
     * Mails in the folder
     *
     * @throws SQLException in case of SQL error
     */
    public ObservableList<MailBean> getAllEmailAddressesFromFolder(FolderBean folderBean) throws SQLException;
}
