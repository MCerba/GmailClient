/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces.dao;

import com.cerbam.data.MailAddressBean;
import java.sql.SQLException;

/**
 *
 * @author Cerba Mihail
 */
public interface EmailAddressDAO {

    /**
     * This method adds mailAddressBean to DB 
     * If EmailAddress already exists returns its id
     *
     * @param mailAddressBean mailAddressBean to add to DataBase
     * @return int - id of created mailAddressBean
     * @throws SQLException - if mailAddressBean cannnot be created
     */
    public int addEmailAddress(MailAddressBean mailAddressBean) throws SQLException;


        /**
     * This method gets  MailAddressBean with particular ID
     * @param id(int) id - mail address ID
     * @return (MailAddressBean) MailAddressBean - representing mail address in BD
     * @throws SQLException if MailAddress does not exist with this ID
     */
    public MailAddressBean getMailAddressBeanByID(int id) throws SQLException;

        /**
     * This method gets Array of MailAddressBeans from receivers field of particular email
     * @param mailId (int)  - ID of emailBean which contains MailAddresses as receivers 
     * @return (MailAddressBean[]) - Array of MailAddressBeans representing all 
     * Mail addresses in receivers field(returns Empty array if there are no such MailAddress in receiver table)
     * 
     * @throws SQLException in case of SQL error
     */
    public MailAddressBean[] getReceiverMailAddressBeansByMailID(int mailId) throws SQLException;

        /**
     * This method gets Array of MailAddressBeans from CC field of particular email
     * @param mailId (int)  - ID of emailBean which contains MailAddresses as CC 
     * @return (MailAddressBean[]) - Array of MailAddressBeans representing all 
     * Mail addresses in receivers field(returns Empty array if there are no such MailAddress in receiver table)
     * 
     * @throws SQLException in case of SQL error
     */
    public MailAddressBean[] getCCMailAddressBeansByMailID(int mailId) throws SQLException;

     /**
     * This method gets Array of MailAddressBeans from BCC field of particular email
     * @param mailId (int)  - ID of emailBean which contains MailAddresses as BCC 
     * @return (MailAddressBean[]) - Array of MailAddressBeans representing all 
     * Mail addresses in receivers field(returns Empty array if there are no such MailAddress in receiver table)
     * 
     * @throws SQLException in case of SQL error
     */
    public MailAddressBean[] getBCCMailAddressBeansByMailID(int mailId) throws SQLException;
    
    /**
     *This method gets Array of  All MailAddressBeans 
     * @return MailAddressBean[]) - Array of MailAddressBeans representing all 
     * Mail addresses 
     * 
     * @throws SQLException in case of SQL error
     */
    public MailAddressBean[] getAllEmailAddresses() throws SQLException;

}
