/**
 * Sample Skeleton for 'TableView.fxml' Controller Class
 */
package com.cerbam.javaFX.controllers;

import com.cerbam.business.BeanConverter;
import com.cerbam.business.dao.GmailDAO;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailFXBean;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableViewController {

    private final static Logger LOG = LoggerFactory.getLogger(TableViewController.class);
    GmailRootController gmailRootController;
    FolderBean folderToDisplay;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="gmailTable"
    private AnchorPane gmailTable; // Value injected by FXMLLoader

    private GmailDAO gmailDAO;
    private MailFXBean mailFXBean;

    @FXML // fx:id="tableView"
    private TableView<MailFXBean> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="id"
    private TableColumn<MailFXBean, Number> id; // Value injected by FXMLLoader

    @FXML // fx:id="from"
    private TableColumn<MailFXBean, String> from; // Value injected by FXMLLoader

    @FXML // fx:id="to"
    private TableColumn<MailFXBean, String> to; // Value injected by FXMLLoader

    @FXML // fx:id="subject"
    private TableColumn<MailFXBean, String> subject; // Value injected by FXMLLoader

    @FXML // fx:id="content"
    private TableColumn<MailFXBean, String> content; // Value injected by FXMLLoader

    @FXML // fx:id="sentDate"
    private TableColumn<MailFXBean, String> sentDate; // Value injected by FXMLLoader

    @FXML // fx:id="receiveDate"
    private TableColumn<MailFXBean, String> receiveDate; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // Connects the property in the Data object to the column in the table
        id.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        from.setCellValueFactory(cellData -> cellData.getValue()
                .senderEmailAddressProperety());
        to.setCellValueFactory(cellData -> cellData.getValue()
                .receiverEmailAddressesProperety());
        subject.setCellValueFactory(cellData -> cellData.getValue()
                .subjectProperety());
        content.setCellValueFactory(cellData -> cellData.getValue()
                .htmlContentProprty());
        sentDate.setCellValueFactory(cellData -> cellData.getValue()
                .sentDateProperty());
        receiveDate.setCellValueFactory(cellData -> cellData.getValue()
                .receivedDateProperty());

        adjustColumnWidths();

        // Listen for selection changes and show the fishData details when changed.
        tableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) -> showGmailContent(newValue));
    }

    public TableViewController() {
        super();
        gmailDAO = new GmailDAO();
        LOG.info(" NEW TableViewController");
    }

    public void setFolder(FolderBean folderBean) {
        this.folderToDisplay = folderBean;
    }

    /**
     * Sets the width of the columns based on a percentage of the overall width
     */
    private void adjustColumnWidths() {
        // Get the current width of the table
        double width = gmailTable.getPrefWidth();
        // Set width of each column
        id.setPrefWidth(width * 0.05);
        from.setPrefWidth(width * 0.1);
        to.setPrefWidth(width * 0.1);
        subject.setPrefWidth(width * 0.1);
        content.setPrefWidth(width * 0.55);
        sentDate.setPrefWidth(width * 0.1);
        receiveDate.setPrefWidth(width * 0.1);

    }

    private void showGmailContent(MailFXBean mailFXBean) {
        LOG.debug(mailFXBean.toString());
        this.gmailRootController.mailBeanObservedData.setObservedData(mailFXBean);
    }

    public void setRootController(GmailRootController gmailRootController) {
        this.gmailRootController = gmailRootController;
    }

    public void displayTheTable() {
        // Add observable list data to the table
        try {

            tableView.setItems(BeanConverter.toObservableListMailFXBean(this.gmailDAO.getAllEmailAddressesFromFolder(this.folderToDisplay)));
            LOG.info(this.folderToDisplay.toString());
            LOG.debug(tableView.getItems().toString());

        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
