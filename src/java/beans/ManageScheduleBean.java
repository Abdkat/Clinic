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
/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "manageScheduleBean")
@ViewScoped
public class ManageScheduleBean implements Serializable{
    private AppointmentClass selectedAppointment;  
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private ArrayList<AppointmentClass> appointments; 
    
    @Inject 
    private SessionBean sessionBean;
    
    public ManageScheduleBean() {        
    }       
    
    @PostConstruct
    public void init(){
        try {         
            /*
            appointments = new ArrayList<>();
            AppointmentClass app = new AppointmentClass(); 
            app.setAppointmentId(1);          // cooment out from this line to line 43 to display the table
            app.setNamePatient("Abdullah");
            app.setNameDoctor("Abdullah");
            app.setPatientId(1);
            app.setDoctorId(1);
            app.setPrice(1000);
            app.setDurationMinutes(30);
            app.setDate(new Date());
            app.setClinicName("GJU");
            app.setActionPlan(" ");
            app.setConfirmed(Boolean.FALSE);
            appointments.add(app);
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, "Abdullah");
            */
            appointments = appointmentsDao.getAppointmentByDoctorId(sessionBean.getDoctorId());
            // System.out.println("got appointments ");
        } catch (Exception ex) {
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, "Abdullah 111");
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
        sessionBean.setSelectedItemId(selectedAppointment.getAppointmentId());
        //Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, sessionBean.getSelectedItemId());
    }
    
    public void confirmSelectedAppointment(){
        try {
            selectedAppointment.setConfirmed(Boolean.TRUE);
            appointmentsDao.updateAppointment(selectedAppointment);
        } catch (Exception ex) {
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cancelSelectedAppointment(){
        try {
            selectedAppointment.setConfirmed(Boolean.FALSE);
            appointmentsDao.updateAppointment(selectedAppointment);
        } catch (Exception ex) {
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSelectedAppointment(){
        try {
            appointmentsDao.deleteAppointment(selectedAppointment.getAppointmentId());
        } catch (Exception ex) {
            Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
