/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.dao;

import com.cerbam.business.interfaces.dao.EmailAddressDAO;
import com.cerbam.data.MailAddressBean;
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
 * This class execute connection between mailAddressBean and DataBase
 *
 * @author Cerba Mihail
 * @version 1.0
 */
public class GmailAddressDAO implements EmailAddressDAO {

    private final static Logger LOG = LoggerFactory.getLogger(GmailDAO.class);

    private final String url = "jdbc:mysql://localhost:3306/EMAIL_APP_DB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private final String user = "CerbaM";
    private final String password = "javaapp";

    /**
     * This method adds mailAddressBean to DB If EmailAddress already exists
     * returns its id
     *
     * @param mailAddressBean mailAddressBean to add to DataBase
     * @return int - id of created mailAddressBean
     * @throws SQLException - if mailAddressBean cannnot be created
     */
    @Override
    public int addEmailAddress(MailAddressBean mailAddressBean) throws SQLException {
        int id = findEmailAddressID(mailAddressBean);
        if (id != 0) {
            return id;
        }

        String sql = "INSERT INTO email_app_db.EMAILADRESSES (ADRESS_NAME, AUTHOR_NAME) values (?, ?);";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, mailAddressBean.getAddressName());
            pStatement.setString(2, mailAddressBean.getAuthorName());

            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                id = -1;
                if (rs.next()) {
                    id = rs.getInt(1);

                }
                mailAddressBean.setID(id);
                LOG.debug("New record EMAIL ID is " + id);
            }
        } catch (SQLException ex) {
            LOG.debug(" EMAILADRESSES create error", ex.getMessage());
            throw ex;
        }
        return id;
    }

    /**
     *
     * @param mailAddressBean mailAddressBean to find in DataBase
     * @return int - id of EmailAddress( 0 if doesn't exist)
     * @throws SQLException - if mailAddressBean cannot be fined
     */

    private int findEmailAddressID(MailAddressBean mailAddressBean) throws SQLException {

        // If there is no record with the desired id then this will be returned
        // as a null pointer
        int id = 0;

        String selectQuery = "SELECT ID from email_app_db.EMAILADRESSES WHERE ADRESS_NAME = ? AND AUTHOR_NAME = ? ;";

        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection
                        .prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setString(1, mailAddressBean.getAddressName());
            pStatement.setString(2, mailAddressBean.getAuthorName());
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("ID");
                }
            }
        }
        LOG.info("Found id : " + id);
        mailAddressBean.setID(id);
        return id;
    }

    /**
     * This method gets MailAddressBean with particular ID
     *
     * @param id (int) id - mail address ID
     * @return (MailAddressBean) MailAddressBean - representing mail address in
     * BD
     * @throws SQLException if MailAddress does not exist with this ID
     */
    @Override
    public MailAddressBean getMailAddressBeanByID(int id) throws SQLException {
        MailAddressBean mailAddressBean = new MailAddressBean();
        String selectQuery = "SELECT * FROM email_app_db.EMAILADRESSES WHERE ID = ?;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    mailAddressBean = makeMailAddressBean(resultSet);
                } else {
                    throw new SQLException("MailAddress was not found!");
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getMailAddressBeanByID error", ex.getMessage());
            throw ex;
        }
        return mailAddressBean;
    }

    /**
     *
     * @param rs ResultSet representing all columns in MailAddress table
     * @return (MailAddressBean) MailAddressBean - representing mail address in
     * BD
     * @throws SQLException if SQL statement is not correct or else SQL error
     */
    private MailAddressBean makeMailAddressBean(ResultSet rs) throws SQLException {
        MailAddressBean mailAddressBean = new MailAddressBean();
        mailAddressBean.setAddressName(rs.getString("ADRESS_NAME"));
        mailAddressBean.setAuthorName(rs.getString("AUTHOR_NAME"));
        mailAddressBean.setID(rs.getInt("ID"));

        return mailAddressBean;
    }

    /**
     * This method gets Array of MailAddressBeans from receivers field of
     * particular email
     *
     * @param mailId (int) - ID of emailBean which contains MailAddresses as
     * receivers
     * @return (MailAddressBean[]) - Array of MailAddressBeans representing all
     * Mail addresses in receivers field(returns Empty array if there are no
     * such MailAddress in receiver table)
     *
     * @throws SQLException in case of SQL error
     */
    @Override
    public MailAddressBean[] getReceiverMailAddressBeansByMailID(int mailId) throws SQLException {
        List<MailAddressBean> ReceiverMailAddressBean = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.EMAILADRESSES"
                + " JOIN email_app_db.EMAILS_TO_EMAILADRESSES ON (EMAILADRESSES_ID = EMAILADRESSES.ID) WHERE EMAIL_ID = ?;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, mailId);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReceiverMailAddressBean.add(makeMailAddressBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getReceiverMailAddressBeansByMailID error", ex.getMessage());
            throw ex;
        }
        return ReceiverMailAddressBean.toArray(new MailAddressBean[ReceiverMailAddressBean.size()]);
    }

    /**
     * This method gets Array of MailAddressBeans from CC field of particular
     * email
     *
     * @param mailId (int) - ID of emailBean which contains MailAddresses as CC
     * @return (MailAddressBean[]) - Array of MailAddressBeans representing all
     * Mail addresses in receivers field(returns Empty array if there are no
     * such MailAddress in receiver table)
     *
     * @throws SQLException in case of SQL error
     */
    @Override
    public MailAddressBean[] getCCMailAddressBeansByMailID(int mailId) throws SQLException {
        List<MailAddressBean> CCMailAddressBean = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.EMAILADRESSES"
                + " JOIN email_app_db.EMAILS_CC_EMAILADRESSES ON (EMAILADRESSES_ID = EMAILADRESSES.ID) WHERE EMAIL_ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, mailId);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    CCMailAddressBean.add(makeMailAddressBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getCCMailAddressBeansByMailID error", ex.getMessage());
            throw ex;
        }
        return CCMailAddressBean.toArray(new MailAddressBean[CCMailAddressBean.size()]);
    }

    /**
     * This method gets Array of MailAddressBeans from BCC field of particular
     * email
     *
     * @param mailId (int) - ID of emailBean which contains MailAddresses as BCC
     * @return (MailAddressBean[]) - Array of MailAddressBeans representing all
     * Mail addresses in receivers field(returns Empty array if there are no
     * such MailAddress in receiver table)
     *
     * @throws SQLException in case of SQL error
     */
    @Override
    public MailAddressBean[] getBCCMailAddressBeansByMailID(int mailId) throws SQLException {
        List<MailAddressBean> BCCMailAddressBean = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.EMAILADRESSES"
                + " JOIN email_app_db.EMAILS_BCC_EMAILADRESSES ON (EMAILADRESSES_ID = EMAILADRESSES.ID) WHERE EMAIL_ID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, mailId);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    BCCMailAddressBean.add(makeMailAddressBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getBCCMailAddressBeansByMailID error", ex.getMessage());
            throw ex;
        }
        return BCCMailAddressBean.toArray(new MailAddressBean[BCCMailAddressBean.size()]);
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
    public MailAddressBean[] getAllEmailAddresses() throws SQLException {
        List<MailAddressBean> AllEmailAddresses = new ArrayList<>();
        String selectQuery = "SELECT * FROM email_app_db.EMAILADRESSES";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    AllEmailAddresses.add(makeMailAddressBean(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("getBCCMailAddressBeansByMailID error", ex.getMessage());
            throw ex;
        }
        return AllEmailAddresses.toArray(new MailAddressBean[AllEmailAddresses.size()]);

    }
}
