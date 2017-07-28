package com.viktarx.coherence;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.sessions.Session;

/**
 * Database connection details required for Coherence Write-Behind support.
 */
public class JdbcSessionCustomizer implements SessionCustomizer {

    public void customize(Session session) throws Exception {
        session.getLogin().setDatabaseURL("jdbc:oracle:thin:@localhost:1521:xe");
        session.getLogin().setUserName("system");
        session.getLogin().setPassword("manager");
        session.getLogin().setDriverClassName("oracle.jdbc.driver.OracleDriver");
    }
}
