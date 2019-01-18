/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

/**
 * This class represent one Attachment
 *
 * @author Cerba Mihail
 */
public class FolderBean {

    private int ID;
    private String folderName;

    /**
     * Default Constructor
     */
    public FolderBean() {
    }

    /**
     * Copy Constructor
     *
     * @param folderBean - folderBean for copy
     */
    public FolderBean(FolderBean folderBean) {
        ID = folderBean.ID;
        folderName = folderBean.folderName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String toString() {
        return "FolderBean{" + "ID=" + ID + ", folderName=" + folderName + '}';
    }

}
