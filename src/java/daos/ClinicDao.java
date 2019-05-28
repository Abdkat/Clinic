package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Clinic;
/**
 *
 * @author Dr. Firas Al-Hawari
 * 
 */
public class ClinicDao extends ConnectionDao {
    
   public ArrayList<Clinic> buildClinics() 
            throws Exception {
        ArrayList<Clinic> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            System.out.print("GOT THE STUPID CONNECTION");
            
            String sql = "SELECT * FROM CLINIC ";    
                
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();  
            
            System.out.print("Successfully retrieved clinics");
            
            while (rs.next()) {
                list.add(populateClinics(rs));
            }
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            System.out.print("ERROR OF THE STUPID QUERY");
            throw new SQLException(e.getMessage());
        }
    }
    private Clinic populateClinics(ResultSet rs) throws SQLException {
        
        Clinic clinic = new Clinic();
        
        clinic.setClinicId(rs.getInt("ID"));
        clinic.setName(rs.getString("CLINIC_NAME"));
        clinic.setPhoneNumber(rs.getString("PHONE"));
        clinic.setWorkHours(rs.getString("WORKING_HOURS"));
        clinic.setDescription(rs.getString("DESCRIPTION"));
                
        return clinic;
    }
    
    public void insertClinic(Clinic clinic) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO CLINIC (ID,"
                    + " CLINIC_NAME,"
                    + " PHONE,"
                    + " WORKING_HOURS,"
                    + " DESCRIPTION"
                    + " VALUES ((select max(ID) from CLINIC)+1,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setString(1, clinic.getName());
            ps.setString(2, clinic.getPhoneNumber());
            ps.setString(3, clinic.getWorkHours());
            ps.setString(4, clinic.getDescription());
            
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void updateClinic(Clinic clinic) throws Exception {
        try {
            Connection conn = getConnection();
            
            String sql = "UPDATE CLINIC SET CLINIC_NAME=?,"
                    + " WORKING_HOURS=?,"
                    + " DESCRIPTION=?,"
                    + " PHONE =?,"
                    + " WHERE ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, clinic.getName());
            ps.setString(2, clinic.getWorkHours());
            ps.setString(3, clinic.getDescription());
            ps.setString(4, clinic.getPhoneNumber());
            ps.setInt(8, clinic.getClinicId());
            ps.executeUpdate();
            
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteClinic(int clinicId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM CLINIC WHERE ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clinicId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public Clinic getClinic(int clinicId) throws Exception {
        try {   
            Clinic clinic = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM CLINIC "
                            + " WHERE ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, clinicId);
            
            ResultSet rs = ps.executeQuery();           
            
            while (rs.next()) {
                clinic = populateClinics(rs);
            }
            
            rs.close();
            ps.close();
            
            return clinic;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public Clinic getClinicByDoctorID(int doctorId) throws Exception {
        try {   
            Clinic clinic = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM CLINIC "
                       + "JOIN CLINIC_DOCTOR ON (CLINIC.ID = CLINIC_DOCTOR.CLINIC_ID)"
                       + " WHERE CLINIC_DOCTOR.DOCTOR_ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, doctorId);
            
            ResultSet rs = ps.executeQuery();           
            
            while (rs.next()) {
                clinic = populateClinics(rs);
            }
            
            rs.close();
            ps.close();
            
            return clinic;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
            
    public static void main(String [] args){        
        try {
           ClinicDao dao = new ClinicDao();                
           Clinic doc = dao.getClinic(1);
           System.out.println(doc.toString());
        } catch (Exception ex) {
            Logger.getLogger(PatientDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
