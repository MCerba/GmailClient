/**
 * Sample Skeleton for 'TabView.fxml' Controller Class
 */
package com.cerbam.javaFX.controllers;

import com.cerbam.app.MainApp;
import com.cerbam.business.dao.GmailFolderDAO;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailFXBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML TabViewController class
 *
 * @author Cerba Mihail
 */
public class TabViewController implements PropertyChangeListener {

    private final static Logger LOG = LoggerFactory.getLogger(TabViewController.class);
    GmailRootController gmailRootController;
    TableViewController tableViewController;
    GmailFolderDAO gmailFolderDAO;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tabView"
    private TabPane tabView; // Value injected by FXMLLoader

    @FXML
    void deleteTab(ActionEvent event) {
        displayResult("Delete Tab method will be implemented in phase 4!");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

    public TabViewController() {
        super();
        gmailFolderDAO = new GmailFolderDAO();
    }

    /**
     * This method is displaying message as an alert
     *
     * @param result(String) String to display
     */
    public void displayResult(String result) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, result, ButtonType.OK);
        alert.setTitle(result);
        alert.setHeaderText("Email");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();

        }
    }

    public void showInbox() {
        tabView.getSelectionModel().clearSelection();
        tabView.getSelectionModel().clearAndSelect(1);
        tabView.getSelectionModel().clearAndSelect(0);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("TAB PROPRTY CHANGE");
        String propertyName = evt.getPropertyName();

        LOG.debug("propertyChange propertyName: " + propertyName);

        if ("MailBeanObservedData".equals(propertyName)) {
            showTabs((ObservableList) evt.getNewValue());
            System.out.println("propertyChange");
        }
    }

    public void showTabs(ObservableList<FolderBean> folders) {
        folders.forEach((folder) -> {
            tabView.getTabs().add(new Tab(folder.getFolderName()));
        });

        tabView.getSelectionModel().clearSelection();

        // Add Tab ChangeListener
        tabView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                System.out.println("Tab selected: " + newValue.getText());
                if (newValue.getContent() == null || true) {
                    try {
                        // Loading content on demand
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(MainApp.class
                                .getResource("/fxml/TableView.fxml"));
                        Parent root = (Parent) loader.load();
                        tableViewController = loader.getController();
                        tableViewController.setRootController(gmailRootController);
                        newValue.setContent(root);

                        FolderBean folderBean = new FolderBean();
                        folderBean.setFolderName(newValue.getText());
                        folderBean.setID(gmailFolderDAO.findFolderID(folderBean));
                        tableViewController.setFolder(folderBean);
                        tableViewController.displayTheTable();

                        // OPTIONAL : Store the controller if needed
                        //                    cityControllerMap.put(newValue.getText(), fXMLLoader.getController());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    // Content is already loaded. Update it if necessary.
                    Parent root = (Parent) newValue.getContent();
                    // Optionally get the controller from Map and manipulate the content
                    // via its controller.
                }
            }

            //        @Override
            //        public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
            //            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            //        }
        });
// By default, select 1st tab and load its content.
        tabView.getSelectionModel().selectFirst();

    }

    public void setRootController(GmailRootController gmailRootController) {
        this.gmailRootController = gmailRootController;

    }

}
