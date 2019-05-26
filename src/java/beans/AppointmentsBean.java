package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import models.AppointmentClass;

/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "appointmentsBean")
@SessionScoped
public class AppointmentsBean implements Serializable {
    private ArrayList<AppointmentClass> appointments;
    
    public AppointmentsBean() {
        appointments = new ArrayList<>();
        
    }        

    public ArrayList<AppointmentClass> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<AppointmentClass> appointments) {
        this.appointments = appointments;
    } 
}