/**
 * Sample Skeleton for 'Settings.fxml' Controller Class
 */
package com.cerbam.javaFX.controllers;

import com.cerbam.business.BeanConverter;
import com.cerbam.business.GmailPropertiesManager;
import com.cerbam.data.MailConfigFXBean;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML SettingsController class
 *
 * @author Cerba Mihail
 */
public class SettingsController {

    private final static Logger LOG = LoggerFactory.getLogger(SettingsController.class);

    private MailConfigFXBean mailConfigFXBean;
    private GmailPropertiesManager gmailPropertiesManager;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userName"
    private TextField userName; // Value injected by FXMLLoader

    @FXML // fx:id="emailAddress"
    private TextField emailAddress; // Value injected by FXMLLoader

    @FXML // fx:id="password"
    private TextField password; // Value injected by FXMLLoader

    @FXML // fx:id="imapServerURL"
    private TextField imapServerURL; // Value injected by FXMLLoader

    @FXML // fx:id="smtpServerURL"
    private TextField smtpServerURL; // Value injected by FXMLLoader

    @FXML // fx:id="imapPortNumber"
    private TextField imapPortNumber; // Value injected by FXMLLoader

    @FXML // fx:id="smtpPortNumber"
    private TextField smtpPortNumber; // Value injected by FXMLLoader

    @FXML // fx:id="databaseURL"
    private TextField databaseURL; // Value injected by FXMLLoader

    @FXML // fx:id="databaseName"
    private TextField databaseName; // Value injected by FXMLLoader

    @FXML // fx:id="databasePortNumber"
    private TextField databasePortNumber; // Value injected by FXMLLoader

    @FXML // fx:id="databaseUsername"
    private TextField databaseUsername; // Value injected by FXMLLoader

    @FXML // fx:id="databasePassword"
    private TextField databasePassword; // Value injected by FXMLLoader

    /**
     * This method write Text Properties
     *
     * @param event - event on button
     */
    @FXML
    void okAndClose(ActionEvent event) {
        LOG.debug("Program exit");
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();

        try {
            gmailPropertiesManager.writeTextProperties("", "MailConfig", BeanConverter.toBeanConfigConvert(mailConfigFXBean));
            stage.close();
        } catch (IOException ex) {
            LOG.debug("WriteTextProperties Error!" + ex.getMessage());
            displayResult("Configuration file doesn't exosts!");
        } catch (Exception e) {
            LOG.debug("WriteTextProperties Error!" + e.getMessage());
            displayResult("Fill in all the fields please!");
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        gmailPropertiesManager = new GmailPropertiesManager();
        try {
            mailConfigFXBean = BeanConverter.toFXBeanConfigConvert(gmailPropertiesManager.loadTextProperties("", "MailConfig"));
        } catch (Exception e) {
            LOG.debug("MailConfig doesn't exists");
        }
        if (mailConfigFXBean == null) {
            mailConfigFXBean = new MailConfigFXBean();
        }
        performBindings();

    }

    /**
     * This method perform binding between FXML elements and MailConfigFXBean
     */
    private void performBindings() {
        Bindings.bindBidirectional(userName.textProperty(), mailConfigFXBean.userNameProperty());
        Bindings.bindBidirectional(emailAddress.textProperty(), mailConfigFXBean.emailAddressProperty());
        Bindings.bindBidirectional(password.textProperty(), mailConfigFXBean.passwordProperty());
        Bindings.bindBidirectional(imapServerURL.textProperty(), mailConfigFXBean.imapServerURLProperty());
        Bindings.bindBidirectional(smtpServerURL.textProperty(), mailConfigFXBean.smtpServerURLProperty());
        Bindings.bindBidirectional(imapPortNumber.textProperty(), mailConfigFXBean.imapPortNumberProperty());
        Bindings.bindBidirectional(smtpPortNumber.textProperty(), mailConfigFXBean.smtpPortNumberProperty());
        Bindings.bindBidirectional(databaseURL.textProperty(), mailConfigFXBean.databaseURLProperty());
        Bindings.bindBidirectional(databaseName.textProperty(), mailConfigFXBean.databaseNameProperty());
        Bindings.bindBidirectional(databasePortNumber.textProperty(), mailConfigFXBean.databasePortNumberProperty());
        Bindings.bindBidirectional(databaseUsername.textProperty(), mailConfigFXBean.databaseUsernameProperty());
        Bindings.bindBidirectional(databasePassword.textProperty(), mailConfigFXBean.databasePasswordProperty());

    }

    /**
     * This method is displaying message as an alert
     *
     * @param result(String) String to display
     */
    public void displayResult(String result) {
        Alert alert = new Alert(Alert.AlertType.ERROR, result, ButtonType.OK);
        alert.setTitle(result);
        alert.setHeaderText("Error");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();

        }
    }
}
