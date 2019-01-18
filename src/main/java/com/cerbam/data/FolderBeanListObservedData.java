/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail 
 */
public class FolderBeanListObservedData {
   

    private final static Logger LOG = LoggerFactory.getLogger(FolderBeanListObservedData.class);

    public ObservableList<FolderBean> folderBeanslist;

    private final PropertyChangeSupport mPcs = new PropertyChangeSupport(this);


    public void setObservedData(ObservableList<FolderBean> folderBeanslist) {
        LOG.debug("setObservedData: " + folderBeanslist);
        folderBeanslist = FXCollections.observableArrayList();
        ObservableList<FolderBean> oldTheData = FXCollections.observableArrayList(this.folderBeanslist);
        this.folderBeanslist = folderBeanslist;
        mPcs.firePropertyChange("observedData", oldTheData, folderBeanslist);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        LOG.debug("addPropertyChangeListener");
        mPcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        mPcs.removePropertyChangeListener(listener);
    }


    public ObservableList<FolderBean> getTheObservedData() {
        return this.folderBeanslist;
    }
}
