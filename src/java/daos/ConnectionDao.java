package daos;

import beans.SessionBean;
import java.io.Serializable;
import java.sql.Connection;
import javax.faces.context.FacesContext;
import java.sql.DriverManager;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * Author: Dr. Firas Al-Hawari
 *
 */
public class ConnectionDao implements Serializable {
    private DataSource dataSource;
    private String oracleUrl;
    private String databaseUsername;
    private String databasePassword;
    private final String oracleDriver;    
    private final boolean useConnectionPool = false;
    private final SessionBean sessionBean;
    
    public ConnectionDao() {
        
        oracleDriver = "oracle.jdbc.driver.OracleDriver";
        //sessionBean = new SessionBean();

        if (!useConnectionPool) {
            //oracleUrl = "jdbc:oracle:thin:@52.232.34.123:1521:CE471DB";
            oracleUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            databaseUsername = "SYSTEM";
            databasePassword = "password";
        }

        FacesContext context = FacesContext.getCurrentInstance();
        sessionBean = (SessionBean) context.getELContext().getELResolver().getValue(
                                         context.getELContext(), null, "sessionBean"); 
        System.out.print("GOT THE DAMN SESSION BEAN");
    }

    public Connection getConnection() throws Exception {
        Connection connection = null;

        if (sessionBean != null) {
            connection = sessionBean.getConnection();

            if (connection == null || connection.isClosed()) {
                connection = openSessionConnection();
                sessionBean.setConnection(connection);                
            }
        }

        return connection;
    }

    public void closeConnection() throws Exception {
        if (sessionBean != null) {
            Connection connection = sessionBean.getConnection();

            if (connection != null) {
                connection.close();
                sessionBean.setConnection(null);
            }
        }
    }

    private Connection openSessionConnection() throws Exception {
        Connection connection = null;

        if (sessionBean != null) {
            if (useConnectionPool) {
                dataSource = (DataSource) new InitialContext().lookup("jdbc/XE_oraDB18");
                connection = dataSource.getConnection();
            } else {
                Class.forName(oracleDriver).newInstance();
                connection = DriverManager.getConnection(oracleUrl,databaseUsername,databasePassword);
            }
        }

        return connection;
    }   
}
