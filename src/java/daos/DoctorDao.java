package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Doctor;
import models.Clinic;
/**
 *
 * @author Dr. Firas Al-Hawari
 * 
 */
public class DoctorDao extends ConnectionDao {
    
   public ArrayList<Doctor> buildDoctors() 
            throws Exception {
        ArrayList<Doctor> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            System.out.print("GOT THE STUPID CONNECTION");
            
                String sql = "SELECT * FROM DOCTORS ";    
                
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();  
            
            System.out.print("Successfully retrieved the doctor data");
            
            while (rs.next()) {
                list.add(populateDoctors(rs));
            }
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            System.out.print("ERROR OF THE STUPID QUERY");
            throw new SQLException(e.getMessage());
        }
    }
    private Doctor populateDoctors(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        
        doctor.setDoctorId(rs.getInt("ID"));
        doctor.setDoctorName(rs.getString("DOCTOR_NAME"));
        doctor.setUsername(rs.getString("USERNAME"));
        doctor.setPassword(rs.getString("PASSWORD"));
        doctor.setPhone(rs.getString("PHONE"));
        doctor.setDescription(rs.getString("DESCRIPTION"));
        doctor.setWorkHours(rs.getString("WORKING_HOURS"));
        doctor.setSpecialization(rs.getString("SPECIALIZATION"));
        
        // Check this masterpiece 
        ClinicDao clinicDao = new ClinicDao();
        try{
            Clinic clinic = clinicDao.getClinicByDoctorID(doctor.getDoctorId());
            doctor.setClinic(clinic);
            //System.out.println("clinic name = "+clinic.getWorkHours());
        } catch(Exception ex){
            Logger.getLogger(DoctorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
        
        return doctor;
    }
    
    public void insertDoctor(Doctor doctor) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO DOCTORS (ID,"
                    + " DOCTOR_NAME,"
                    + " USERNAME,"
                    + " PASSWORD,"
                    + " PHONE,"
                    + " SPECIALIZATION,"
                    + " WORKING_HOURS,"
                    + " DESCRIPTION,"
                    + " VALUES ((select max(ID) from DOCTORS)+1,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            ps.setString(1, doctor.getDoctorName());
            ps.setString(2, doctor.getUsername());
            ps.setString(3, doctor.getPassword());
            ps.setString(4, doctor.getPhone());
            ps.setString(5, doctor.getSpecialization());
            ps.setString(6, doctor.getWorkHours()); // need modification 
            ps.setString(7, doctor.getDescription());
            
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void updateDoctor(Doctor doctor) throws Exception {
        try {
            Connection conn = getConnection();
            
            String sql = "UPDATE DOCTORS SET DOCTOR_NAME=?,"
                    + " USERNAME=?,"
                    + " PASSWORD=?,"
                    + " PHONE =?,"
                    + " SPECIALIZATION=?,"
                    + " WORKING_HOURS=?,"
                    + " DESCRIPTION=?"
                    + " WHERE ID=?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, doctor.getDoctorName());
            ps.setString(2, doctor.getUsername());
            ps.setString(3, doctor.getPassword());
            ps.setString(4, doctor.getPhone());
            ps.setString(5, doctor.getSpecialization());
            ps.setString(6, doctor.getWorkHours()); 
            ps.setString(7, doctor.getDescription());
            ps.setInt(8, doctor.getDoctorId());
            ps.executeUpdate();
            
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteDoctor(int DoctorId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM DOCTORS WHERE ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, DoctorId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public Doctor getDoctor(int DoctorId) throws Exception {
        try {   
            Doctor doctor = null;
            Connection conn = getConnection();
            
            String sql = "SELECT * FROM DOCTORS "
                            + " WHERE ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, DoctorId);
            
            ResultSet rs = ps.executeQuery();           
            
            while (rs.next()) {
                doctor = populateDoctors(rs);
            }
            
            rs.close();
            ps.close();
            
            return doctor;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
            
    public static void main(String [] args){        
        try {
            DoctorDao dao = new DoctorDao();                
           Doctor doc = dao.getDoctor(1);
           System.out.println(doc.toString());
        } catch (Exception ex) {
            Logger.getLogger(DoctorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
