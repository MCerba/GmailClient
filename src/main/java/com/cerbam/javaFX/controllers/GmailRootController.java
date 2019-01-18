package com.cerbam.javaFX.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cerbam.app.MainApp;
import com.cerbam.business.dao.GmailFolderDAO;
import com.cerbam.data.FolderBean;
import com.cerbam.data.FolderBeanListObservedData;
import com.cerbam.data.MailBeanObservedData;
import com.cerbam.data.MailFXBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Cerba Mihail
 */
public class GmailRootController {
    
    private final static Logger LOG = LoggerFactory.getLogger(GmailRootController.class);
    private EventHandler<ActionEvent> removeCompose = (ActionEvent event) -> {
        removeCompose(event);
    };
    
    private EventHandler<ActionEvent> composeEmail = (ActionEvent event) -> {
        composeEmail(event);
    };
    @FXML // fx:id="rootAnchorPane"
    private AnchorPane rootAnchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="leftSplit"
    private AnchorPane leftSplit; // Value injected by FXMLLoader

    @FXML // fx:id="composePane"
    private AnchorPane composePane; // Value injected by FXMLLoader

    @FXML // fx:id="upperRihgtSplit"
    private AnchorPane upperRihgtSplit; // Value injected by FXMLLoader

    @FXML // fx:id="lowerRightSplit"
    private AnchorPane lowerRightSplit; // Value injected by FXMLLoader

    private AnchorPane ComposeView;
    
    @FXML // fx:id="composeBtn"
    private Button composeBtn; // Value injected by FXMLLoader
    
    public TabViewController tabViewController;
    public WebViewController webViewController;
    public MenuController menuViewController;
    public ComposeController composeController;
    
    public FolderBeanListObservedData folderBeanListObservedData;
    public MailBeanObservedData mailBeanObservedData;
    
    private GmailFolderDAO gmailFolderDAO;
    ObservableList<FolderBean> folders;
    
    @FXML
    void initialize() {
        try {
            
            initUpperRightLayout();
            initLowerRightLayout();
            initLeftLayout();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("/fxml/Compose.fxml"));
            ComposeView = (AnchorPane) loader.load();
            composeController = loader.getController();
        } catch (IOException ex) {
            LOG.error("initialize error: " + ex);
        }
        
        folderBeanListObservedData = new FolderBeanListObservedData();
        folderBeanListObservedData.addPropertyChangeListener(tabViewController);
        
        mailBeanObservedData = new MailBeanObservedData();
        mailBeanObservedData.addPropertyChangeListener(webViewController);
        
        gmailFolderDAO = new GmailFolderDAO();
        try {
            folders = gmailFolderDAO.getAllFolderBeans();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(GmailRootController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(folders);
        tabViewController.showTabs(folders);
        
    }
    
    private void initLeftLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class
                    .getResource("/fxml/Menu.fxml"));
            AnchorPane menuView = (AnchorPane) loader.load();
            menuViewController = loader.getController();
            menuViewController.setTabController(tabViewController);
            menuViewController.setRootController(this);
            // Give the controller the data object.
            //fishFXTreeController = loader.getController();
            //fishFXTreeController.setFishDAO(fishDAO);
            leftSplit.getChildren().add(menuView);
        } catch (Exception ex) {
            LOG.error("initLeftLayout error", ex);
            Platform.exit();
        }
    }
    
    private void initUpperRightLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class
                    .getResource("/fxml/TabView.fxml"));
            
            AnchorPane TabView = (AnchorPane) loader.load();
            tabViewController = loader.getController();
            tabViewController.setRootController(this);
            // Give the controller the data object.
            //fishFXTreeController = loader.getController();
            //fishFXTreeController.setFishDAO(fishDAO);
            upperRihgtSplit.getChildren().add(TabView);
            TabView.prefWidthProperty().bind(upperRihgtSplit.widthProperty());
            TabView.prefHeightProperty().bind(upperRihgtSplit.heightProperty());
        } catch (Exception ex) {
            LOG.error("initUpperLeftLayout error", ex);
            Platform.exit();
        }
    }
    
    private void initLowerRightLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainApp.class
                    .getResource("/fxml/WebView.fxml"));
            
            AnchorPane WebView = (AnchorPane) loader.load();
            webViewController = loader.getController();
            webViewController.setRootController(this);
            // Give the controller the data object.
            //fishFXTreeController = loader.getController();
            //fishFXTreeController.setFishDAO(fishDAO);
            lowerRightSplit.getChildren().add(WebView);
            WebView.prefWidthProperty().bind(lowerRightSplit.widthProperty());
            WebView.prefHeightProperty().bind(lowerRightSplit.heightProperty());
        } catch (Exception ex) {
            LOG.error("initLowerRightLayout error", ex);
            Platform.exit();
        }
    }

    /**
     * This method show compose Email window
     *
     * @param event - event on button
     */
    @FXML
    void composeEmail(ActionEvent event) {
        try {
            //upperRihgtSplit.getChildren().add(TabView);
            ComposeView.prefWidthProperty().bind(composePane.widthProperty());
            ComposeView.prefHeightProperty().bind(composePane.heightProperty());
            
            ComposeView.translateXProperty().set(rootAnchorPane.getWidth());
            composePane.getChildren().add(ComposeView);
            
            Timeline timeLine = new Timeline();
            KeyValue kv = new KeyValue(ComposeView.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
            timeLine.getKeyFrames().add(kf);
            timeLine.play();
            composeBtn.setOnAction(removeCompose);
            composeBtn.setText("Back");
        } catch (Exception ex) {
            LOG.error("initLeftLayout error" + ex.getMessage());
            Platform.exit();
        }
        
    }

    /**
     * This method hide compose Email window
     *
     * @param event - event on button
     */
    public void removeCompose(ActionEvent event) {
        composePane.getChildren().remove(ComposeView);
        composeBtn.setOnAction(composeEmail);
        composeBtn.setText("Compose");
    }
    
}
