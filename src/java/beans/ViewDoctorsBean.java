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
@Named(value = "viewDoctorsBean")
@ViewScoped
public class ViewDoctorsBean implements Serializable{
    private DoctorClass selectedDoctor;  
    private final DoctorDao doctorDao = new DoctorDao();
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private ArrayList<DoctorClass> doctors; 
    
    @Inject 
    private SessionBean sessionBean;
    
    public ViewDoctorsBean() {        
    }       
    
    @PostConstruct
    public void init(){
        try {         
            doctors = doctorDao.buildDoctors();
        } catch (Exception ex) {
            Logger.getLogger(ViewDoctorsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DoctorClass getSelectedDoctor() {
        return selectedDoctor;
    }

    public void setSelectedDoctor(DoctorClass selectedDoctor) {
        this.selectedDoctor = selectedDoctor;
    }  

    public ArrayList<DoctorClass> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<DoctorClass> doctors) {
        this.doctors = doctors;
    }   
    
    public void searchAppointments(){        
    }
    
    public void saveSelectedItemId(){
        sessionBean.setSelectedItemId(selectedDoctor.getDoctorId());
        //Logger.getLogger(ManageScheduleBean.class.getName()).log(Level.SEVERE, null, sessionBean.getSelectedItemId());
    }
    
    public String workhoursDays(String workHours) {
        String[] daysofweek = {"Su","Mo","Tu","We","Th","Fr","Sa"};
        String workhoursDays = "";
        for(int i=0;i<workHours.indexOf('*');i++){
            if(workHours.charAt(i)=='1'){
                workhoursDays = workhoursDays+daysofweek[i]+" ";
            }
        }
        return workhoursDays;
    }
    
    public String workhoursTime(String workHours) {
        String workhoursTime = "From: "+workHours.substring( workHours.indexOf('*')+1,workHours.indexOf('/'))
                             + " to "+workHours.substring( workHours.indexOf('/')+1);
        return workhoursTime;
    }
}
