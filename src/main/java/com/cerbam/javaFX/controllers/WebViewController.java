/**
 * Sample Skeleton for 'WebView.fxml' Controller Class
 */
package com.cerbam.javaFX.controllers;

import com.cerbam.business.dao.GmailDAO;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailFXBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML WebViewController class
 *
 * @author Cerba Mihail
 */
public class WebViewController implements PropertyChangeListener {

    private final static Logger LOG = LoggerFactory.getLogger(WebViewController.class);
    private WebEngine webEngine;
    private MailFXBean mailFXBeanForShow;
    private GmailRootController gmailRootController;
    private GmailDAO gmailDAO;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML
    private HBox attachmentsBox;

    @FXML // fx:id="webView"
    private WebView webView; // Value injected by FXMLLoader

    @FXML // fx:id="from"
    private Label from; // Value injected by FXMLLoader

    @FXML // fx:id="to"
    private Label to; // Value injected by FXMLLoader

    @FXML // fx:id="cc"
    private Label cc; // Value injected by FXMLLoader

    @FXML // fx:id="bcc"
    private Label bcc; // Value injected by FXMLLoader

    @FXML // fx:id="subject"
    private Label subject; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        webEngine = webView.getEngine();
        gmailDAO = new GmailDAO();
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
        String propertyName = evt.getPropertyName();

        LOG.debug("propertyChange propertyName: " + propertyName);

        if ("MailBeanObservedData".equals(propertyName)) {
            showMail((MailFXBean) evt.getNewValue());
            System.out.println("propertyChange");
        }

    }

    private void showMail(MailFXBean mailFXBean) {
        mailFXBeanForShow = mailFXBean;
        System.out.println("showMail");
        if (mailFXBean != null) {
            System.out.println(mailFXBean);
        }
        if (mailFXBean.getSenderEmailAddress() != null) {
            from.setText(mailFXBean.getSenderEmailAddress());
        }
        to.setText(mailFXBean.getReceiverEmailAddresses());
        cc.setText(mailFXBean.getCcs());
        bcc.setText(mailFXBean.getBccs());
        subject.setText(mailFXBean.getSubject());
        this.webEngine.loadContent(mailFXBean.getHtmlContent());
        attachmentsBox.getChildren().clear();
        if (mailFXBean.getAttachments() != null){
            

        for (AttachmentBean attachmentBean : mailFXBean.getAttachments()) {
            FileOutputStream stream = null;
            try {
                Button button = new Button();
                button.setText(attachmentBean.getPath());
                stream = new FileOutputStream(attachmentBean.getPath());
                try {
                    stream.write(attachmentBean.getAttachmentArray());
                    stream.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(WebViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                            try (FileOutputStream stream = new FileOutputStream(((Button) e.getSource()).getText())) {
                                    stream.write(attachmentBean.getAttachmentArray());
                                } catch (IOException ex) {
                                    displayResult(((Button) e.getSource()).getText() + " was not downloaded!");
                                }
                        displayResult(((Button) e.getSource()).getText() + " was downloaded!");

                    }
                });
                attachmentsBox.getChildren().add(button);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(WebViewController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    stream.close();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(WebViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
    }

    @FXML
    void forward(ActionEvent event) {
        this.gmailRootController.composeEmail(null);
        mailFXBeanForShow.setReceiverEmailAddresses("");
        mailFXBeanForShow.setSubject("RE: " + mailFXBeanForShow.getSubject());
        mailFXBeanForShow.setHtmlContent("\n\nReplay to :\n" + mailFXBeanForShow.getHtmlContent());
        this.gmailRootController.composeController.setMailFXBean(mailFXBeanForShow);
        this.gmailRootController.tabViewController.showInbox();
    }

    @FXML
    void reply(ActionEvent event) {
        this.gmailRootController.composeEmail(null);
        mailFXBeanForShow.setReceiverEmailAddresses(mailFXBeanForShow.getSenderEmailAddress());
        mailFXBeanForShow.setSubject("RE: " + mailFXBeanForShow.getSubject());
        mailFXBeanForShow.setHtmlContent("\n\nReplay to :\n" + mailFXBeanForShow.getHtmlContent());
        this.gmailRootController.composeController.setMailFXBean(mailFXBeanForShow);
        this.gmailRootController.tabViewController.showInbox();
    }

    @FXML
    void replyAll(ActionEvent event) {
        this.gmailRootController.composeEmail(null);
        mailFXBeanForShow.setSubject("RE: " + mailFXBeanForShow.getSubject());
        mailFXBeanForShow.setHtmlContent("\n\nReplay to :\n" + mailFXBeanForShow.getHtmlContent());
        this.gmailRootController.composeController.setMailFXBean(mailFXBeanForShow);
        this.gmailRootController.tabViewController.showInbox();
    }

    public void setRootController(GmailRootController gmailRootController) {
        this.gmailRootController = gmailRootController;

    }
    
    @FXML
    void deleteEmail(ActionEvent event) {
        try {
            if (this.mailFXBeanForShow.getID()!= 0){
            this.gmailDAO.deleteMailByID(this.mailFXBeanForShow.getID());
            this.mailFXBeanForShow = new MailFXBean();
            showMail(this.mailFXBeanForShow);
            displayResult("Email was deleted!");
            gmailRootController.tabViewController.showInbox();
            }
                    } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(WebViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
