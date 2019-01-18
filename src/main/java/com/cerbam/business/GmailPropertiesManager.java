package com.cerbam.business;

import com.cerbam.business.interfaces.PropertiesManager;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.Paths.get;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.cerbam.data.MailConfigBean;

/**
 * This class manage properties in Gmail Application
 *
 * @author Cerba Mihail
 *
 */
public class GmailPropertiesManager implements PropertiesManager {

    /**
     * This method load TextProperties using path and  Name 
     * 
     * @param path - path to the properties file on disc
     * @param propertiesFileName (String)  -  properties File Name
     * @return MailConfigBean - instance of MailConfigBean
     * @throws IOException if properties can't be loaded 
     */
    @Override
    public final MailConfigBean loadTextProperties(final String path, final String propertiesFileName) throws IOException {

        Properties properties = new Properties();

        Path filePath = get(path, propertiesFileName + ".properties");

        MailConfigBean mailConfigBean = new MailConfigBean();

        // File must exist
        if (Files.exists(filePath)) {
            try (InputStream propertiesFileStream = newInputStream(filePath);) {
                properties.load(propertiesFileStream);
            }
            mailConfigBean.setUserName(properties.getProperty("userName"));
            mailConfigBean.setEmailAddress(properties.getProperty("emailAddress"));
            mailConfigBean.setPassword(properties.getProperty("password"));
            mailConfigBean.setImapServerURL(properties.getProperty("imapServerURL"));
            mailConfigBean.setSmtpServerURL(properties.getProperty("smtpServerURL"));
            mailConfigBean.setImapPortNumber(properties.getProperty("imapPortNumber"));
            mailConfigBean.setSmtpPortNumber(properties.getProperty("smtpPortNumber"));
            mailConfigBean.setDatabaseURL(properties.getProperty("databaseURL"));
            mailConfigBean.setDatabaseName(properties.getProperty("databaseName"));
            mailConfigBean.setDatabasePortNumber(properties.getProperty("databasePortNumber"));
            mailConfigBean.setDatabaseUsername(properties.getProperty("databaseUsername"));
            mailConfigBean.setDatabasePassword(properties.getProperty("databasePassword"));

            
        }
        return mailConfigBean;
    }

    /**
     * This method write TextProperties using path, Name and MailConfigBean 
     * 
     * @param path - path to the properties file on disc
     * @param propertiesFileName (String)  -  properties File Name
     * @param mailConfigBean(MailConfigBean) - instance of MailConfigBean
     * @throws IOException if properties can't be written
     */
    @Override
    public final void writeTextProperties(final String path, final String propertiesFileName, final MailConfigBean mailConfigBean) throws IOException {

        Properties properties = new Properties();

        properties.setProperty("userName", mailConfigBean.getUserName());
        properties.setProperty("emailAddress", mailConfigBean.getEmailAddress());     
        properties.setProperty("password", mailConfigBean.getPassword());
        properties.setProperty("imapServerURL", mailConfigBean.getImapServerURL());
        properties.setProperty("smtpServerURL", mailConfigBean.getSmtpServerURL());
        properties.setProperty("imapPortNumber", mailConfigBean.getImapPortNumber());
        properties.setProperty("smtpPortNumber", mailConfigBean.getSmtpPortNumber());
        properties.setProperty("databaseURL", mailConfigBean.getDatabaseURL());
        properties.setProperty("databaseName", mailConfigBean.getDatabaseName());
        properties.setProperty("databasePortNumber", mailConfigBean.getDatabasePortNumber());
        properties.setProperty("databaseUsername", mailConfigBean.getDatabaseUsername());
        properties.setProperty("databasePassword", mailConfigBean.getDatabasePassword());


        Path filePath = get(path, propertiesFileName + ".properties");

        // Creates the file or if file exists it is truncated to length of zero
        // before writing
        try (OutputStream propFileStream = newOutputStream(filePath)) {
            properties.store(propFileStream, "GMAIL Properties");
        }
    }


}
