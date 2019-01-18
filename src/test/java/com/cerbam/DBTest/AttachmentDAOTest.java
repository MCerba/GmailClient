/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cerbam.DBTest;

import com.cerbam.business.dao.GmailAttachmentDAO;
import com.cerbam.data.AttachmentBean;
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

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cerba Mihail
 */
public class AttachmentDAOTest {

    AttachmentBean attachment1;

    // This is my local MySQL server
    private final String url = "jdbc:mysql://localhost:3306/EMAIL_APP_DB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "CerbaM";
    private final String password = "javaapp";
    

    
    private final static Logger LOG = LoggerFactory.getLogger(AttachmentDAOTest.class);

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
    public void testCreate() throws SQLException {
        GmailAttachmentDAO gmailAttachmentDAO = new GmailAttachmentDAO();

        attachment1 = new AttachmentBean();
        attachment1.setPath("FreeFall.jpg");
        attachment1.setAttachmentArray("FreeFall.jpg");
        int records = gmailAttachmentDAO.addAttachment(attachment1);
        LOG.info("New Attachment ID:" + records);
        assertEquals("A record was not created", 1, records);
    }
    
    @Test
    public void deleteAttachmentByIDTest () throws SQLException{
    GmailAttachmentDAO gmailAttachmentDAO = new GmailAttachmentDAO();
    
            attachment1 = new AttachmentBean();
        attachment1.setPath("FreeFall.jpg");
        attachment1.setAttachmentArray("FreeFall.jpg");
    int records = gmailAttachmentDAO.addAttachment(attachment1);
    LOG.info("deleteAttachmentByIDTest: " + records);
    int result = gmailAttachmentDAO.deleteAttachmentByID(records);
    assertEquals("A record was not created",result, 1);
    }
}
