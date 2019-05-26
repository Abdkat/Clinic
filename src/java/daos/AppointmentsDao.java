package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.AppointmentClass;
import beans.SessionBean;

/**
 *
 * @author Dr. Firas Al-Hawari
 * 
 */
public class AppointmentsDao extends ConnectionDao {
    
   public ArrayList<AppointmentClass> buildAppointments() 
            throws Exception {
        ArrayList<AppointmentClass> list = new ArrayList<>();        
        
        try {   
            Connection conn = getConnection();
            
            System.out.print("GOT THE STUPID CONNECTION");
            
                String sql = "SELECT * FROM APPOINTMENT "
                            +"JOIN CLINIC ON (APPOINTMENT.CLINIC_ID = CLINIC.ID)" 
                            +"JOIN DOCTORS ON (APPOINTMENT.DOCTOR_ID = DOCTORS.ID)" 
                            +"JOIN PATIENTS ON (APPOINTMENT.PATIENT_ID = PATIENTS.ID)";    
                
            PreparedStatement ps = conn.prepareStatement(sql);            

            ResultSet rs = ps.executeQuery();  
            
            System.out.print("Successfully retrieved the GODDAMN database data");
            
            while (rs.next()) {
                list.add(populateAppointments(rs));
            }
            rs.close();
            ps.close();
            
            return list;            
        } catch (SQLException e) {
            System.out.print("ERROR OF THE STUPID QUERY");
            throw new SQLException(e.getMessage());
        }
    }
    
    private AppointmentClass populateAppointments(ResultSet rs) throws SQLException {
        AppointmentClass appointment = new AppointmentClass();
        
        appointment.setAppointmentId(rs.getInt("ID"));
        appointment.setDoctorId(rs.getInt("DOCTOR_ID"));
        appointment.setPatientId(rs.getInt("PATIENT_ID"));
        appointment.setPrice(rs.getInt("PRICE"));
        appointment.setActionPlan(rs.getString("ACTION_PLAN"));
        appointment.setDate(rs.getTimestamp("APP_DATE"));
        appointment.setConfirmed(rs.getBoolean("CONFIRMED"));
        appointment.setNameDoctor(rs.getString("DOCTOR_NAME"));
        appointment.setNamePatient(rs.getString("PATIENT_NAME"));
        appointment.setClinicName(rs.getString("CLINIC_NAME"));
        return appointment;
    }
    
    public void insertAppointment(AppointmentClass appointment) throws Exception {                
        try {
            Connection conn = getConnection();
            
            String sql = "INSERT INTO APPOINTMENT (ID,"
                    + " DOCTOR_ID,"
                    + " PATIENT_ID,"
                    + " CLINIC_ID,"
                    + " APP_DATE,"
                    + " CONFIRMED,"
                    + " PRICE,"
                    + " MINUTES)"
                    + " VALUES ((select max(ID) from APPOINTMENT)+1,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql); 
            
            System.out.print(appointment.getDoctorId());
            ps.setInt(1, appointment.getDoctorId());
            ps.setInt(2, appointment.getPatientId());
            ps.setInt(3, appointment.getClinicId());
            ps.setTimestamp(4, new Timestamp(appointment.getDate().getTime()));
            ps.setBoolean(5, appointment.getConfirmed());
            ps.setInt(6, appointment.getPrice());
            ps.setInt(7, appointment.getDurationMinutes());
            
            System.out.print("app info" + appointment.getDoctorId() + " " + appointment.getPatientId() + appointment.getClinicId());
            
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void updateAppointment(AppointmentClass appointment) throws Exception {
        try {
            Connection conn = getConnection();

            String sql = "UPDATE APPOINTMENT SET ACTION_PLAN=?,"
                    + " CONFIRMED=?,"
                    + " PRICE=?"
                    + " WHERE APPOINTMENT.ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, appointment.getActionPlan());
            ps.setInt(2, appointment.getPrice());
            ps.setBoolean(3, appointment.getConfirmed());
            ps.setInt(4, appointment.getAppointmentId());

            ps.executeUpdate();
            
            ps.close();
            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public void deleteAppointment(int appointmentId) throws Exception {
        Connection conn = getConnection();
        
        try {
            String sql = "DELETE FROM APPOINTMENT WHERE ID=?";                               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    public AppointmentClass getAppointmentById(int appointmentId) throws Exception {
        try {   
            AppointmentClass appointment = null;
            Connection conn = getConnection();

            String sql = "SELECT * FROM APPOINTMENT "
                            +"JOIN CLINIC ON (APPOINTMENT.CLINIC_ID = CLINIC.ID)" 
                            +"JOIN DOCTORS ON (APPOINTMENT.DOCTOR_ID = DOCTORS.ID)" 
                            +"JOIN PATIENTS ON (APPOINTMENT.PATIENT_ID = PATIENTS.ID)"
                            + " WHERE APPOINTMENT.ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, appointmentId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                appointment = populateAppointments(rs);
            }

            rs.close();
            ps.close();
            
            return appointment;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public AppointmentClass getAppointmentByDoctorId(int doctorId) throws Exception {
        try {   
            AppointmentClass appointment = null;
            Connection conn = getConnection();

            String sql = "SELECT * FROM APPOINTMENT "
                            +"JOIN CLINIC ON (APPOINTMENT.CLINIC_ID = CLINIC.ID)" 
                            +"JOIN DOCTORS ON (APPOINTMENT.DOCTOR_ID = DOCTORS.ID)" 
                            +"JOIN PATIENTS ON (APPOINTMENT.PATIENT_ID = PATIENTS.ID)"
                            + " WHERE DOCTORS.ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, doctorId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                appointment = populateAppointments(rs);
            }

            rs.close();
            ps.close();
            
            return appointment;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    public ArrayList<AppointmentClass> getAppointmentByPatientId(int patientId) throws Exception {
        try {   
            ArrayList<AppointmentClass> appointments = new ArrayList<>();
            Connection conn = getConnection();

            String sql = "SELECT * FROM APPOINTMENT "
                            +"JOIN CLINIC ON (APPOINTMENT.CLINIC_ID = CLINIC.ID)" 
                            +"JOIN DOCTORS ON (APPOINTMENT.DOCTOR_ID = DOCTORS.ID)" 
                            +"JOIN PATIENTS ON (APPOINTMENT.PATIENT_ID = PATIENTS.ID)"
                            + " WHERE PATIENTS.ID=?";                        
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, patientId);
            
            ResultSet rs = ps.executeQuery();           

            while (rs.next()) {
                appointments.add(populateAppointments(rs));
            }

            rs.close();
            ps.close();
            
            return appointments;            
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
    
    
            
    public static void main(String [] args){        
        try {
            AppointmentsDao dao = new AppointmentsDao();                
            ArrayList<AppointmentClass> apps = dao.buildAppointments();
        } catch (Exception ex) {
            Logger.getLogger(AppointmentsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
