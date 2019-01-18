/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.util.Arrays;
import java.nio.file.Files;
import java.io.File;

/**
 * This class represent one Attachment
 *
 * @author Cerba Mihail
 */
public class AttachmentBean {

    private int ID;
    private byte[] attachmentArray;
    private String path;

    /**
     * Default Constructor
     */
    public AttachmentBean() {
    }

    /**
     * Copy Constructor
     *
     * @param attachmentBean attachmentBean for copy
     */
    AttachmentBean(AttachmentBean attachmentBean) {
        ID = attachmentBean.ID;
        path = attachmentBean.path;
        attachmentArray = Arrays.copyOf(attachmentBean.attachmentArray, attachmentBean.attachmentArray.length);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public byte[] getAttachmentArray() {
        return Arrays.copyOf(attachmentArray, attachmentArray.length);
    }

    public void setAttachmentArray(byte[] attachmentArray) {
        this.attachmentArray = Arrays.copyOf(attachmentArray, attachmentArray.length);
    }

    public void setAttachmentArray(String path) {
        File fi = new File(path);
        try {
            this.attachmentArray = Files.readAllBytes(fi.toPath());
        } catch (Exception e) {
            throw new IllegalArgumentException("AttachmentBean: File doest exist!");
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Arrays.hashCode(this.attachmentArray);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AttachmentBean other = (AttachmentBean) obj;
        return Arrays.equals(this.attachmentArray, other.attachmentArray);
    }

    @Override
    public String toString() {
        return "AttachmentBean{" + "attachmentArray=" + Arrays.toString(attachmentArray) + ", path=" + path + '}';
    }

    public AttachmentBean getCopy() {
        AttachmentBean bean = new AttachmentBean();
        bean.attachmentArray = this.attachmentArray;
        bean.path = this.path;
        bean.ID = this.ID;
        return bean;
    }

}
