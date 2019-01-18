/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.app;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a starting point of application
 * 
 * @author Cerba Mihail
 * @version 1.0
 */
public class MainApp extends Application {
        private final static Logger LOG = LoggerFactory.getLogger(MainApp.class);
    
    private Stage primaryStage;
    private AnchorPane rootLayout;
        
    public MainApp() {
        super();
    }

   
    
        public void initRootLayout() {

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MainApp.class
                    .getResource("/fxml/GmailRoot.fxml"));
            rootLayout = (AnchorPane) loader.load();

        } catch (IOException ex) {
            LOG.error("Error to init root layout", ex);
            Platform.exit();
        }
    }
    
    
     /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }
    
        @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Set the application icon using getResourceAsStream.
        this.primaryStage.getIcons().add(
                new Image(MainApp.class
                        .getResourceAsStream("/images/main.png")));

        initRootLayout();

        this.primaryStage.setTitle("Gmail client");

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        LOG.info("Program started");
    }
}
