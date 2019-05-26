package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.PatientClass;
/**
 *
 * @author Dr. Firas Al-Hawari
 * 
 */
public class PatientDao extends ConnectionDao {
    
   public ArrayList<PatientClass> buildpatients() 
            throws Exception {
        ArrayList<PatientClass> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            System.out.print("GOT THE STUPID CONNECTION");
            
                String sql = "SELECT * FROM PatientS "
                            +"JOIN CLINIC ON (APPOINTMENT.CLINIC_ID = CLINIC.ID)";    
                
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();  
            
            System.out.print("Successfully retrieved the Patient data");
            
            while (rs.next()) {
                list.add(populatePatients(rs));
            }
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            System.out.print("ERROR OF THE STUPID QUERY");
            throw new SQLException(e.getMessage());
        }
    }
    private PatientClass populatePatients(ResultSet rs) throws SQLException {
        PatientClass patient = new PatientClass();
        
        patient.setPatientId(rs.getInt("ID"));
        patient.setPatientName(rs.getString("PATIENT_NAME"));
        patient.setUsername(rs.getString("USERNAME"));
        patient.setPassword(rs.getString("PASSWORD"));
        patient.setPhone(rs.getString("PHONE"));
        
        
        return patient;
    }
    
    public void insertPatient(PatientClass patient) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO PATIENTS (ID,"
                    + " PATIENT_NAME,"
                    + " USERNAME,"
                    + " PASSWORD,"
                    + " PHONE,"
                    + " VALUES ((select max(ID) from PATIENTS)+1,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setString(1, patient.getPatientName());
            ps.setString(2, patient.getUsername());
            ps.setString(3, patient.getPassword());
            ps.setString(4, patient.getPhone());
            
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void updatePatient(PatientClass patient) throws Exception {
        try {
            Connection conn = getConnection();
            
            String sql = "UPDATE PATIENTS SET PATIENT_NAME=?,"
                    + " USERNAME=?,"
                    + " PASSWORD=?,"
                    + " PHONE =?"
                    + " WHERE ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, patient.getPatientName());
            ps.setString(2, patient.getUsername());
            ps.setString(3, patient.getPassword());
            ps.setString(4, patient.getPhone());
            ps.setInt(5, patient.getPatientId());
            
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deletePatient(int patientId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM PATIENTS WHERE ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, patientId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public PatientClass getPatient(int patientId) throws Exception {
        try {   
            PatientClass patient = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM PATIENTS "
                            + " WHERE ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, patientId);
            
            ResultSet rs = ps.executeQuery();           
            
            while (rs.next()) {
                patient = populatePatients(rs);
            }
            
            rs.close();
            ps.close();
            
            return patient;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
            
    public static void main(String [] args){        
        try {
            PatientDao dao = new PatientDao();                
           PatientClass doc = dao.getPatient(1);
           System.out.println(doc.toString());
        } catch (Exception ex) {
            Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
