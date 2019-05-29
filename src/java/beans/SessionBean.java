package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.enterprise.context.*;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import daos.ConnectionDao;
import java.sql.DriverManager;

/**
 *
 * @Author: Dr. Firas Al-Hawari
 *
 */
@Named(value = "sessionBean")
@SessionScoped
//@ApplicationScoped
public class SessionBean implements Serializable {
    private String username;
    private String password;    
    private Connection connection; 
    private int selectedItemId;     
    private int menuIndex = 0;
    private int doctorId = 0;
    private int patientId = 0;

    public SessionBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public int getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(int selectedItemId) {
        this.selectedItemId = selectedItemId;
    }    

    public int getMenuIndex() {
        return menuIndex;
    }

    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    public void login() throws Exception {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {

        }
        
        setDoctorId( validateLogin(this.username, this.password, "DOCTORS") );
        System.err.println(doctorId);
        if(doctorId>0){
           navigate("/first_page_doctor");
        }
        patientId = validateLogin(this.username, this.password, "PATIENTS");
        if(patientId>0){
           navigate("/first_page_patient");
        }
    }

    public void logout() throws Exception {
        try {
            // Release and close database resources and connections 
            if (connection != null) {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }

                connection.close();
                connection = null;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            setPassword(null);
            setUsername(null);
            
            setPatientId(0);
            setDoctorId(0);
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().invalidateSession();
        }
    }
    private int validateLogin(String username, String password, String table) throws SQLException{
        
        try {
            String oracleDriver = "oracle.jdbc.driver.OracleDriver";
            String oracleUrl = "jdbc:oracle:thin:@localhost:1521:xe";
            String databaseUsername = "SYSTEM";
            String databasePassword = "password";
            Class.forName(oracleDriver).newInstance();
            Connection conn = DriverManager.getConnection(oracleUrl,databaseUsername,databasePassword);
             
            //System.err.println("username & pass = "+username+password);
            String sql  = "SELECT * FROM "+table;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); 
            
            
            while (rs.next()) {
                if(username.equals(rs.getString("USERNAME")) && password.equals(rs.getString("PASSWORD")) ){
                    return rs.getInt("ID");
                }
            }
            rs.close();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }
    
    public void navigate(String url) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext != null) {
            NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
            navigationHandler.handleNavigation(facesContext, null, url + "?faces-redirect=true");
        }
    }
}
