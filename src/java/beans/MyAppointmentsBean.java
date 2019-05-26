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
import models.AppointmentClass;
import daos.DoctorDao;
import models.DoctorClass;

import java.util.Date;
/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "myAppointmentsBean")
@ViewScoped
public class MyAppointmentsBean implements Serializable{
    private AppointmentClass selectedAppointment;  
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private ArrayList<AppointmentClass> appointments; 
    
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

    public AppointmentClass getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(AppointmentClass selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public ArrayList<AppointmentClass> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<AppointmentClass> appointments) {
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
