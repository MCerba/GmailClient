/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.dao;

import com.cerbam.business.interfaces.dao.EmailFolderDAO;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class execute connection between FolderBean and DataBase 
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public class GmailFolderDAO implements EmailFolderDAO {

    private final static Logger LOG = LoggerFactory.getLogger(GmailDAO.class);

    private final String url = "jdbc:mysql://localhost:3306/EMAIL_APP_DB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private final String user = "CerbaM";
    private final String password = "javaapp";

    /**
     * Default constructor
     */
    public GmailFolderDAO() {
        super();
    }


    /**
     * This method adds Folder To DB
     * Checks if Folder already exists returns its id 
     * 
     * @param folderBean folderBean to add
     * @return (int) - ID of inserted folder
     * @throws SQLException in case of SQL error
     */
    @Override
    public int addFolder(FolderBean folderBean) throws SQLException {
        int id = findFolderID(folderBean);
        if (id != 0) {
            folderBean.setID(id);
            return id;
        }

        String sql = "INSERT INTO email_app_db.FOLDERS(FOLDER_NAME) values (?) ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, folderBean.getFolderName());

            pStatement.executeUpdate();
            try (ResultSet rs = pStatement.getGeneratedKeys();) {
                id = -1;
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                folderBean.setID(id);
                LOG.debug("New record ID is " + id);
            }
        } catch (SQLException ex) {
            LOG.debug(" FOLDERS create error", ex.getMessage());
            throw ex;
        }
        return id;
    }
    
    /**
     * This method finds ID of folder in DB
     * @param folderBean (folderBean) FolderBean object  to find
     * @return int - folder ID
     * @throws SQLException if Folder was not found
     */
    @Override
    public int findFolderID(FolderBean folderBean) throws SQLException {

        // If there is no record with the desired id then this will be returned
        // as a null pointer
        int id = 0;

        String selectQuery = "SELECT ID from email_app_db.FOLDERS WHERE FOLDER_NAME = ? ;";

        try (Connection connection = DriverManager.getConnection(url, user,
                password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection
                        .prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setString(1, folderBean.getFolderName());
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("ID");
                }

            }
        }
        LOG.info("FindFolderID Found id : " + id);
        return id;
    }

    /**
     * This method gets folder Bean from DB
     * @param id (int) ID of Folder Bean im Db 
     * @return (FolderBean) FolderBean object 
     * @throws SQLException if Folder was not found
     */
    @Override
    public FolderBean getFolderBeanByID(int id) throws SQLException {
        FolderBean folderBean = new FolderBean();
        String selectQuery = "SELECT * FROM email_app_db.FOLDERS WHERE ID = ? ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    folderBean = makeFolderBean(resultSet);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("findAttachmentByID error", ex.getMessage());
            throw ex;
        }
        return folderBean;
    }
    
    /**
     * This method gets all folder Beans
     * @return ArrayList<FolderBean> FolderBean list 
     * @throws SQLException  if Folders was not found
     */
    public ObservableList<FolderBean> getAllFolderBeans() throws SQLException {
        FolderBean folderBean = new FolderBean();
        ObservableList<FolderBean> folders = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM email_app_db.FOLDERS ;";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    folderBean = makeFolderBean(resultSet);
                    folders.add(folderBean);
                }
            }
        } catch (SQLException ex) {
            LOG.debug("findAttachmentByID error", ex.getMessage());
            throw ex;
        }
        return folders;
    }

     /**
     * this method create FolderBean
     * @param rs ResultSet representing all columns in Folder table 
     * @return (FolderBean) FolderBean - representing Folder Bean in BD
     * @throws SQLException if SQL statement is not correct or else SQL error
     */
    private FolderBean makeFolderBean(ResultSet rs) throws SQLException {
        FolderBean folderBean = new FolderBean();
        folderBean.setFolderName(rs.getString("FOLDER_NAME"));
        folderBean.setID(rs.getInt("ID"));
        return folderBean;
    }
}
