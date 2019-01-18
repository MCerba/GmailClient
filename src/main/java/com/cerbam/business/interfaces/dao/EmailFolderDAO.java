/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.business.interfaces.dao;

import com.cerbam.data.FolderBean;
import java.sql.SQLException;

/**
 * This class execute connection between FolderBean and DataBase
 *
 * @author Cerba Mihail
 * @version 1.0
 */
public interface EmailFolderDAO {

    /**
     * This method adds Folder To DB Checks if Folder already exists returns its
     * id
     *
     * @param folderBean folderBean to add
     * @return (int) - ID of inserted folder
     * @throws SQLException in case of SQL error
     */
    public int addFolder(FolderBean folderBean) throws SQLException;

    /**
     * This method finds ID of folder in DB
     *
     * @param folderBean (folderBean) FolderBean object to find
     * @return int - folder ID
     * @throws SQLException if Folder was not found
     */
    public int findFolderID(FolderBean folderBean) throws SQLException;

    /**
     * This method gets folder Bean from DB
     *
     * @param id (int) ID of Folder Bean im Db
     * @return (FolderBean) FolderBean object
     * @throws SQLException if Folder was not found
     */
    public FolderBean getFolderBeanByID(int id) throws SQLException;
}
