/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.DBTest;

import com.cerbam.business.dao.GmailAddressDAO;
import com.cerbam.business.dao.GmailAttachmentDAO;
import com.cerbam.business.dao.GmailDAO;
import com.cerbam.business.dao.GmailFolderDAO;
import com.cerbam.business.interfaces.dao.EmailDAO;
import com.cerbam.business.interfaces.dao.EmailFolderDAO;
import com.cerbam.data.AttachmentBean;
import com.cerbam.data.FolderBean;
import com.cerbam.data.MailAddressBean;
import com.cerbam.data.MailBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javafx.collections.ObservableList;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */

public class EmailDAOTest {

    AttachmentBean attachment1;
    AttachmentBean attachment2;
    MailBean mailBean;
    MailAddressBean senderAddressBean;
    MailAddressBean receiverAddressBean;
    MailAddressBean otherAddressBean;
    GmailDAO gmailDAO;
    GmailAttachmentDAO gmailAttachmentDAO;
    GmailAddressDAO gmailAddressDAO;


            

    // This is my local MySQL server
    private final String url = "jdbc:mysql://localhost:3306/EMAIL_APP_DB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true";
    private final String user = "CerbaM";
    private final String password = "javaapp";
    

    
    private final static Logger LOG = LoggerFactory.getLogger(AttachmentDAOTest.class);

    
    @Before
    public void init(){
        senderAddressBean = new MailAddressBean();
        senderAddressBean.setAddressName("send.1642705@gmail.com");
        receiverAddressBean = new MailAddressBean();
        receiverAddressBean.setAddressName("receive.1642705@gmail.com");
        otherAddressBean = new MailAddressBean();
        otherAddressBean.setAddressName("other.1642705@gmail.com");
        gmailDAO = new GmailDAO();
        gmailAttachmentDAO = new  GmailAttachmentDAO();
        gmailAddressDAO = new GmailAddressDAO();

        mailBean = new MailBean();
            attachment1 = new AttachmentBean();
            attachment2 = new AttachmentBean();
            mailBean.setSenderEmailAddress(senderAddressBean);
            mailBean.setReceiverEmailAddresses(new MailAddressBean[] {receiverAddressBean, otherAddressBean});
            mailBean.setSubject("Test");
            mailBean.setCcs(new MailAddressBean[]{receiverAddressBean,senderAddressBean});
            mailBean.setBccs(new MailAddressBean[]{otherAddressBean});
            mailBean.setTextMessage("The first Test");
            mailBean.setHtmlContent("Testing");
            attachment1.setPath("FreeFall.jpg");
            attachment1.setAttachmentArray("FreeFall.jpg");
            attachment2.setPath("WindsorKen180.jpg");
            attachment2.setAttachmentArray("WindsorKen180.jpg");
            mailBean.setEmbeddedAttachments(new AttachmentBean[]{attachment1, attachment2});
            mailBean.setAttachments(new AttachmentBean[]{attachment2, attachment1});
            FolderBean folderBean = new FolderBean();
            folderBean.setFolderName("Inbox");
            mailBean.setFolder(folderBean);
            mailBean.setSentDate("2018-09-23 23:00:00.0");
    }
    /**
     * This routine recreates the database for every test. This makes sure that
     * a destructive test will not interfere with any other test.
     *
     * This routine is courtesy of Bartosz Majsak, an Arquillian developer at
     * JBoss who helped me out last winter with an issue with Arquillian. Look
     * up Arquillian to learn what it is.
     */


    @Before
    public void createDBTables() {
        final String seedDataScript = loadAsString("CreateEmailAppTables.sql");
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            for (String statement : splitStatements(new StringReader(seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    }   
    
    @After
    public void createNewDBTables() {
        final String seedDataScript = loadAsString("CreateEmailAppTables.sql");
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            for (String statement : splitStatements(new StringReader(seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
        
        final String addDataScript = loadAsString("Add Data.sql");
        try (Connection connection = DriverManager.getConnection(url, user, password);) {
            for (String statement : splitStatements(new StringReader(addDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed seeding database", e);
        }
    } 
    
    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream)) {
            return scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader, String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
    }
    
    
    @Test
    public void testaddEmail() throws SQLException {

        int records = gmailDAO.addEmail(mailBean);

        LOG.info("testCreate: New Attachment ID:" + records);
        assertEquals("A record was not created", 1, records);
    }
         
        @Test
    public void testaddEmail1() throws SQLException {

        int records = gmailDAO.addEmail(mailBean);
        mailBean.setSubject("new test");
        records = gmailDAO.addEmail(mailBean);
        LOG.info("testCreate: New Attachment ID:" + records);
        assertEquals("A record was not created", 2, records);
    }
            
    @Test
    public void testGetEmailByID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testGetEmailByID: ");
    assertEquals("A record was not created", mailBean, mailBeanFromDB);
    }
     
    @Test
    public void testGetEmailByID2() throws SQLException {
        mailBean.setSubject("Good");
    int records = gmailDAO.addEmail(mailBean);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testGetEmailByID: ");
    assertEquals("A record was not created", "Good", mailBeanFromDB.getSubject());
    }
    
    @Test
    public void testdeleteReceiverAddressByMailID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    int deleted = gmailDAO.deleteReceiverAddressByMailID(records);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testGetEmailByID: ");
    assertEquals(deleted,2);
    }
    
    
    @Test
    public void testdeleteCCAddressByMailID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    int deleted = gmailDAO.deleteCCAddressByMailID(records);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testdeleteCCAddressByMailID: ");
    assertEquals(deleted,2);
    }
    
    @Test
    public void testdeleteBCCAddressByMailID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    int deleted = gmailDAO.deleteBCCAddressByMailID(records);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testdeleteCCAddressByMailID: ");
    assertEquals(deleted,1);
    }
    
    
    
    @Test
    public void testdeleteEmbeddedAttachmentByMailID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    int deleted = gmailDAO.deleteEmbeddedAttachmentByMailID(records);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testdeleteCCAddressByMailID: ");
    assertEquals(deleted,2);
    }
    
    
    @Test
    public void testdeleteAttachmentByMailID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    int deleted = gmailDAO.deleteAttachmentByMailID(records);
    MailBean mailBeanFromDB =  gmailDAO.getEmailByID(records);
    LOG.info("testdeleteCCAddressByMailID: ");
    assertEquals(deleted,2);
    }
    
    @Test
    public void testdeleteMailByID() throws SQLException {
    int records = gmailDAO.addEmail(mailBean);
    int deleted = gmailDAO.deleteMailByID(records);
    LOG.info("testdeleteMailByID: ");
    assertEquals(deleted,1);
    }
    
    @Test
    public void testgetAllEmails() throws SQLException {
        gmailDAO.addEmail(mailBean);
        gmailDAO.getAllEmails();
        MailBean[] allAddresses = gmailDAO.getAllEmails();
        LOG.info("testgetAllEmails:");
        assertTrue("testgetAllEmails - FAIL" , mailBean.equals(allAddresses[0]));
    }
    
        @Test
    public void testGetAllEmailAddressesFromFolder() throws SQLException {
        gmailDAO.addEmail(mailBean);
        FolderBean spam = new FolderBean();
        spam.setFolderName("Spam");
        EmailFolderDAO gmailFolderDAO = new GmailFolderDAO();
        gmailFolderDAO.addFolder(spam);
        mailBean.setFolder(spam);
        gmailDAO.addEmail(mailBean); 

        ObservableList<MailBean> allAddresses = gmailDAO.getAllEmailAddressesFromFolder(spam);
        
        LOG.info("testGetAllEmailAddressesFromFolder:");
        assertTrue("testGetAllEmailAddressesFromFolder - FAIL" , mailBean.equals(allAddresses.get(0)));
    }
}

