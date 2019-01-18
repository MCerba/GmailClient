/**
 * Sample Skeleton for 'Menu.fxml' Controller Class
 */
package com.cerbam.javaFX.controllers;

import com.cerbam.business.BeanConverter;
import com.cerbam.business.GmailPropertiesManager;
import com.cerbam.business.GmailReceiver;
import com.cerbam.business.dao.GmailDAO;
import com.cerbam.business.dao.GmailFolderDAO;
import com.cerbam.business.interfaces.EmailReceiver;
import com.cerbam.data.FolderBean;
import com.cerbam.data.FolderBeanListObservedData;
import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigFXBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML MenuController class
 *
 * @author Cerba Mihail
 */
public class MenuController implements PropertyChangeListener {

    @FXML
    private TextField newFolderText;
    private final static Logger LOG = LoggerFactory.getLogger(MenuController.class);
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    private GmailRootController gmailRootController;
    private TabViewController tabViewController;
    private EmailReceiver gmailReceiver;
    private MailConfigFXBean mailConfigFXBean;
    private GmailPropertiesManager gmailPropertiesManager;
    private GmailDAO gmailDAO;
    private GmailFolderDAO gmailFolderDAO;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    public MenuController() {
        gmailRootController = new GmailRootController();
        gmailReceiver = new GmailReceiver();
        gmailDAO = new GmailDAO();
        tabViewController = new TabViewController();
    }

    @FXML
    void AddFolder(ActionEvent event) {
        String newFolderName = newFolderText.getText();
        if (newFolderName == null || newFolderName == "") {
            displayResult("Folder Name IS Invalid!");
        } else {
            FolderBean folderBean = new FolderBean();
            folderBean.setFolderName(newFolderName);
            try {
                folderBean.setID(gmailFolderDAO.addFolder(folderBean));
                ObservableList<FolderBean> newFolders = FXCollections.observableArrayList();
                newFolders.add(folderBean);
                tabViewController.showTabs(newFolders);

            } catch (SQLException e) {
                displayResult("Folder Name " + newFolderName + " can't be added!");
            }
        }
    }

    @FXML
    void ReceiveEmail(ActionEvent event) {
        gmailPropertiesManager = new GmailPropertiesManager();

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mailConfigFXBean = BeanConverter.toFXBeanConfigConvert(gmailPropertiesManager.loadTextProperties("", "MailConfig"));
                    LOG.debug("MailConfig: " + mailConfigFXBean);
                    ArrayList<MailBean> receivedEmails = gmailReceiver.receiveEmail(BeanConverter.toBeanConfigConvert(mailConfigFXBean));
                    LOG.debug("I got NEW MEssages: " + receivedEmails.size());
                    for (MailBean mailBean : receivedEmails) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        Date now = new Date();
                        String nowString = dateFormat.format(now);
                        mailBean.setReceivedDate(nowString);
                        gmailDAO.addEmail(mailBean);
                        LOG.debug("New mailBean to DB:\n" + mailBean);

                    }
                    if (receivedEmails.size() >= 0) {
                        tabViewController.showInbox();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                displayResult("You have " + receivedEmails.size() + " new emails! \n");
                            }
                        });

                    }
                } catch (Exception e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            displayResult("Emails CANNOT be received!\n" + e.getMessage());
                        }
                    });

                }

            }
        });
        newThread.start();

    }

    @FXML
    void sendEmailFromMenu(ActionEvent event) {
        gmailRootController.composeEmail(null);
    }

    @FXML
    void showAbout(ActionEvent event) {

    }

    /**
     * This method shows settings window
     *
     * @param event - event on button
     */
    @FXML
    void showSettings(ActionEvent event) {
        try {
            AnchorPane settingsLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/Settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(settingsLayout, 330, 490));
            stage.setResizable(false);

            stage.show();
            LOG.debug("Show");
        } catch (IOException e) {
            LOG.debug("Oppening SettingsError:" + e.getMessage());
        }

    }

    /**
     * This method checking if MailConfig.properties exists if not - show
     * settings window to fill it in
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        gmailFolderDAO = new GmailFolderDAO();
        //checking if MailConfig.properties exists
        File f = new File("MailConfig.properties");
        //if not exists show settings window to fill it in
        if (!f.exists()) {
            Thread newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showSettings(null);
                        }
                    });
                }
            });
            newThread.start();

        }

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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setTabController(TabViewController tabViewController) {
        this.tabViewController = tabViewController;
    }

    public void setRootController(GmailRootController gmailRootController) {
        this.gmailRootController = gmailRootController;

    }
}
