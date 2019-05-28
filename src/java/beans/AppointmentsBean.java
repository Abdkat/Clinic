package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import models.Appointment;

/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "appointmentsBean")
@SessionScoped
public class AppointmentsBean implements Serializable {
    private ArrayList<Appointment> appointments;
    
    public AppointmentsBean() {
        appointments = new ArrayList<>();
        
    }        

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    } 
}