/**
 * Sample Skeleton for 'Compose.fxml' Controller Class
 */
package com.cerbam.javaFX.controllers;

import com.cerbam.business.BeanConverter;
import com.cerbam.business.GmailPropertiesManager;
import com.cerbam.business.GmailSender;
import com.cerbam.business.dao.GmailDAO;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailAddressBean;
import com.cerbam.data.MailBean;
import com.cerbam.data.MailConfigBean;
import com.cerbam.data.MailConfigFXBean;
import com.cerbam.data.MailFXBean;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML ComposeController class
 *
 * @author Cerba Mihail
 */
public class ComposeController {
    
    private final static Logger LOG = LoggerFactory.getLogger(ComposeController.class);
    GmailSender gmailSender;
    private MailFXBean mailFXBean;
    private FileChooser fileChooser;
    private Stage stage;
    private GmailPropertiesManager gmailPropertiesManager;
    private MailConfigBean mailConfigBean;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="to"
    private TextField to; // Value injected by FXMLLoader

    @FXML // fx:id="cc"
    private TextField cc; // Value injected by FXMLLoader

    @FXML // fx:id="bcc"
    private TextField bcc; // Value injected by FXMLLoader

    @FXML // fx:id="subject"
    private TextField subject; // Value injected by FXMLLoader

    @FXML // fx:id="htmlEditor"
    private HTMLEditor htmlEditor; // Value injected by FXMLLoader

    @FXML
    void handleAttachments(ActionEvent event) {
        stage = (Stage) htmlEditor.getScene().getWindow();

        // Open the FileChooser dailog
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            AttachmentBean[] attachments = mailFXBean.getAttachments();
            if (attachments == null) {
                attachments = new AttachmentBean[50];
                attachments[0] = new AttachmentBean();
                attachments[0].setAttachmentArray(file.getAbsolutePath());
                attachments[0].setPath(file.getPath());
                
            } else {
                for (AttachmentBean attachment : attachments) {
                    if (attachment == null) {
                        attachment = new AttachmentBean();
                        attachment.setAttachmentArray(file.getAbsolutePath());
                        attachment.setPath(file.getPath());
                        break;
                    }
                }
                
            }
            mailFXBean.setAttachments(attachments);
        }
        
    }
    
    @FXML
    void handleEmbeddedAttachments(ActionEvent event) {
        stage = (Stage) htmlEditor.getScene().getWindow();

        // Open the FileChooser dailog
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            AttachmentBean[] embeddedAttachments = mailFXBean.getEmbeddedAttachments();
            if (embeddedAttachments == null) {
                embeddedAttachments = new AttachmentBean[50];
                embeddedAttachments[0] = new AttachmentBean();
                embeddedAttachments[0].setAttachmentArray(file.getAbsolutePath());
                embeddedAttachments[0].setPath(file.getPath());
                
            } else {
                for (AttachmentBean embeddedAttachment : embeddedAttachments) {
                    if (embeddedAttachment == null) {
                        embeddedAttachment = new AttachmentBean();
                        embeddedAttachment.setAttachmentArray(file.getAbsolutePath());
                        embeddedAttachment.setPath(file.getPath());
                        break;
                    }
                }
                
            }
            mailFXBean.setEmbeddedAttachments(embeddedAttachments);
        }
        
    }
    
    @FXML
    void handleSend(ActionEvent event) throws SQLException {
        MailAddressBean mailAddressBean = new MailAddressBean();
        mailAddressBean.setAddressName(mailConfigBean.getEmailAddress());
        mailFXBean.setSenderEmailAddress(mailAddressBean.getAddressName());
        mailFXBean.setHtmlContent(htmlEditor.getHtmlText());
        mailFXBean.setReceiverEmailAddresses(to.getText());
        mailFXBean.setCcs(cc.getText());
        mailFXBean.setBccs(bcc.getText());
        mailFXBean.setSubject(subject.getText());

        LOG.debug("9999999999999999999999999" + mailFXBean.toString());
        MailBean tosend = BeanConverter.toMailBeanConvert(mailFXBean);

        FolderBean folderBean = new FolderBean();
        folderBean.setID(2);
        folderBean.setFolderName("Sent");
        tosend.setFolder(folderBean);
        GmailDAO gmailDAO = new GmailDAO();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date now = new Date();
        String nowString = dateFormat.format(now);
        tosend.setSentDate(nowString);
        LOG.debug("5555555555555555" + tosend.toString());

        if (readyToSend()) {
            Thread newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        gmailSender.sendEmail(mailConfigBean, tosend);
                        gmailDAO.addEmail(tosend);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                displayResult("Your Email was send successfully!");
                            }
                        });
                    } catch (Exception e) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                displayResult("Your Email was  NOT send! \n" + e.getMessage());
                            }
                        });
                        
                    }
                    
                }
            });
            newThread.start();
            clearAll();
        } else {
            displayResult("Your should fill in next fields:\n To \nSubject\nContent ");
        }
        
    }
    
    public ComposeController() {
        gmailSender = new GmailSender();
        
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        gmailPropertiesManager = new GmailPropertiesManager();
        try {
            mailConfigBean = gmailPropertiesManager.loadTextProperties("", "MailConfig");
        } catch (Exception e) {
            LOG.debug("MailConfig doesn't exists");
        }
        if (mailConfigBean == null) {
            mailConfigBean = new MailConfigBean();
        }
        mailFXBean = new MailFXBean();
        performBindings();
        fileChooser = new FileChooser();
        
    }
    
    public void setMailFXBean(MailFXBean mailFXBean) {
        this.mailFXBean = mailFXBean;
        performBindings();
        htmlEditor.setHtmlText(mailFXBean.getHtmlContent());
        
    }

    /**
     * This method perform binding between FXML elements and MailConfigFXBean
     */
    private void performBindings() {
        Bindings.bindBidirectional(to.textProperty(), mailFXBean.receiverEmailAddressesProperety());
        Bindings.bindBidirectional(cc.textProperty(), mailFXBean.ccsProperty());
        Bindings.bindBidirectional(bcc.textProperty(), mailFXBean.bccsProperty());
        Bindings.bindBidirectional(subject.textProperty(), mailFXBean.subjectProperety());
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
    
    private boolean readyToSend() {
        if (this.mailFXBean.getReceiverEmailAddresses() != "" && this.mailFXBean.getSubject() != "" && !this.mailFXBean.getHtmlContent().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private void clearAll() {
        to.clear();
        cc.clear();
        bcc.clear();
        subject.clear();
        htmlEditor.setHtmlText("");
        mailFXBean.setAttachments(null);
        mailFXBean.setEmbeddedAttachments(null);
    }
}
