package beans;

import daos.AppointmentsDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Appointment;
import daos.DoctorDao;
import models.Doctor;

import java.util.Date;
/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "myAppointmentsBean")
@ViewScoped
public class MyAppointmentsBean implements Serializable{
    private Appointment selectedAppointment;  
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private ArrayList<Appointment> appointments; 
    
    @Inject 
    private SessionBean sessionBean;
    
    public MyAppointmentsBean() {        
    }       
    
    @PostConstruct
    public void init(){
        try {         
            appointments = appointmentsDao.getAppointmentByPatientId(sessionBean.getPatientId());
        } catch (Exception ex) {
            Logger.getLogger(ViewDoctorsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }


    public void searchAppointments(){        
    }
    
    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedAppointment.getDoctorId());
        //Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, sessionBean.getSelectedItemId());
    }
    
    public void deleteAppointment(){
        try {
            appointmentsDao.deleteAppointment(selectedAppointment.getAppointmentId());
        } catch (Exception ex) {
            Logger.getLogger(MyAppointmentsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
