/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author MC
 */
public class MailAddressBean {

    private int ID;
    private String authorName;
    private String addressName;

    public MailAddressBean() {
    }

    /**
     * Copy Constructor
     *
     * @param mailAddressBean mailAddressBean for copy
     */
    public MailAddressBean(MailAddressBean mailAddressBean) {
        this.ID = mailAddressBean.ID;
        this.authorName = mailAddressBean.authorName;
        this.addressName = mailAddressBean.addressName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public String toString() {
        return "MailAddressBean{" + "ID=" + ID + ", authorName=" + authorName + ", addressName=" + addressName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.ID;
        hash = 47 * hash + Objects.hashCode(this.authorName);
        hash = 47 * hash + Objects.hashCode(this.addressName);
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
        final MailAddressBean other = (MailAddressBean) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (!Objects.equals(this.authorName, other.authorName)) {
            return false;
        }
        if (!Objects.equals(this.addressName, other.addressName)) {
            return false;
        }
        return true;
    }

}
