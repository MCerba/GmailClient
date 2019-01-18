package com.cerbam.business.interfaces;

import com.cerbam.data.MailConfigBean;
import java.io.IOException;

/**
 * This class manage properties in Gmail Application
 *
 * @author Cerba Mihail
 *
 */
public interface PropertiesManager {
    /**
     * This method load TextProperties using path and  Name 
     * 
     * @param path - path to the properties file on disc
     * @param propertiesFileName (String)  -  properties File Name
     * @return MailConfigBean - instance of MailConfigBean
     * @throws IOException if properties can't be loaded 
     */
    public MailConfigBean loadTextProperties(final String path, final String propertiesFileName) throws IOException;
    
    /**
     * This method write TextProperties using path, Name and MailConfigBean 
     * 
     * @param path - path to the properties file on disc
     * @param propertiesFileName (String)  -  properties File Name
     * @param mailConfigBean(MailConfigBean) - instance of MailConfigBean
     * @throws IOException if properties can't be written
     */
    public void writeTextProperties(final String path, final String propFileName, final MailConfigBean mailConfig) throws IOException;
}
