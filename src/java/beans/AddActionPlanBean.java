package beans;

import daos.AppointmentsDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Appointment;

/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "addActionPlanBean")
@ViewScoped
public class AddActionPlanBean implements Serializable{
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private Appointment appointment;
    private int appointmentId;
    
    private String actionPlan;
    private String patientName;
    private String clinicName;
    private Date date;
    private int price;
    @Inject
    private SessionBean sessionBean;
    
    public AddActionPlanBean() {        
    }
    
    @PostConstruct
    public void init(){                
        try {
            appointmentId = sessionBean.getSelectedItemId();
            
            if(appointmentId > 0){
                appointment = appointmentsDao.getAppointmentById(appointmentId);
                patientName = appointment.getNamePatient();
                actionPlan = appointment.getActionPlan();
                date = appointment.getDate();
                price = appointment.getPrice();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddActionPlanBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
    public void saveAppointment() {
        try {
            appointmentId = sessionBean.getSelectedItemId();
            
            if(appointmentId > 0){
                System.out.print("Changing action plan for damned appointment");
                appointment = appointmentsDao.getAppointmentById(appointmentId);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddActionPlanBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            System.out.print(this.actionPlan);
            appointment.setActionPlan(this.actionPlan); //THIS IS CAUSING SOME STUPID ERROR FOR SOME STUPID DAMNED REASON.
            if (sessionBean.getSelectedItemId() > 0) {
                appointmentsDao.updateAppointment(appointment);
            } else {
                appointmentsDao.insertAppointment(appointment);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddActionPlanBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_schedule");
    }
}
