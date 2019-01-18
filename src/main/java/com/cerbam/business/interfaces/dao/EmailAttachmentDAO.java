/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces.dao;

import com.cerbam.data.AttachmentBean;
import java.sql.SQLException;

/**
 * This class execute connection between mailAttachmentBean and DataBase
 *
 * @author Cerba Mihail
 * @version 1.0
 */
public interface EmailAttachmentDAO {

    /**
     * This method adds attachmentBean to DB
     *
     * @param attachmentBean (attachmentBean) to add to DataBase
     * @return int - id of created AttachmentBean
     * @throws SQLException - if mailAddressBean cannot be created
     */
    public int addAttachment(AttachmentBean attachmentBean) throws SQLException;

    /**
     * This method gets attachment by its ID
     *
     * @param id (int) - Attachment ID
     * @return (AttachmentBean) AttachmentBean representing Attachmnet
     * @throws SQLException if Attachment does not exist
     */
    public AttachmentBean getAttachmentByID(int id) throws SQLException;

    /**
     * This method get AttachmentsBean Array from DB
     *
     * @param mailId (int) - ID of emailBean which contains Attachment
     * @return AttachmentBean[] - array of AttachmentBeans
     * @throws SQLException in case of SQL error
     */
    public AttachmentBean[] getAttachmentsByMailID(int mailId) throws SQLException;

    /**
     * This method get Embedded AttachmentsBean Array from DB
     *
     * @param mailId (int) - ID of emailBean which contains Embedded Attachment
     * @return AttachmentBean[] - array of Embedded AttachmentBeans
     * @throws SQLException in case of SQL error
     */
    public AttachmentBean[] getEmbeddedAttachmentsByMailID(int mailId) throws SQLException;

    /**
     * This method delete Embedded AttachmentsBean from DB by its ID
     *
     * @param id (int) - ID of AttachmentsBean in DB
     * @return (int) - number of deleted rows
     * @throws SQLException in case of SQL error
     */
    public int deleteAttachmentByID(int id) throws SQLException;

    /**
     * This method delete Embedded AttachmentsBean from DB
     *
     * @param mailId (int) - ID of emailBean which contains Embedded Attachment
     * @return (int) - number of deleted rows
     * @throws SQLException in case of SQL error
     */
    public int deleteAttachmentByMailID(int mailId) throws SQLException;

}
