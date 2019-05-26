package beans;

import daos.AppointmentsDao;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.AppointmentClass;
import models.PatientClass;
import daos.DoctorDao;
import daos.PatientDao;
import models.DoctorClass;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "reserveAppointmentBean")
@ViewScoped
public class ReserveAppointmentBean implements Serializable{
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private final DoctorDao doctorDao = new DoctorDao();
    private final PatientDao patientDao = new PatientDao();
    private DoctorClass doctor;
    private PatientClass patient;
    private String nameDoctor;
    private String namePatient;
    private String clinicName;
    private Date date;
    private int patientId;
    private int doctorId;
    private int price;
    private String phone;
    private String workHours;
    private int durationMinutes;
    private String actionPlan;
    private Boolean confirmed;  
    private String specialization;
    
    @Inject
    private SessionBean sessionBean;
    
    public ReserveAppointmentBean() {        
    }
    
    @PostConstruct
    public void init(){                
        try {
            doctorId = sessionBean.getSelectedItemId();
            
            if(doctorId > 0){
                doctor = doctorDao.getDoctor(doctorId);   
                patient = patientDao.getPatient(sessionBean.getPatientId());
                nameDoctor = doctor.getDoctorName();
                phone = doctor.getPhone();
                clinicName = doctor.getClinic().getName();
                workHours = doctor.getWorkHours();
                //price = doctor.getPrice();             // must be added as a doctor table column
                price = 50;
                durationMinutes = 30;
                confirmed = false;
                //durationMinutes = doctor.getDurationMinutes();
                //actionPlan = doctor.getActionPlan();
                //confirmed = doctor.getConfirmed();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditEventBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DoctorClass getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorClass doctor) {
        this.doctor = doctor;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public PatientClass getPatient() {
        return patient;
    }

    public void setPatient(PatientClass patient) {
        this.patient = patient;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }        

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public void saveAppointment() {
        try {
            AppointmentClass appointment = new AppointmentClass();
            
            appointment.setDoctorId(doctor.getDoctorId());
            appointment.setPatientId(patient.getPatientId());
            appointment.setClinicId(doctor.getClinic().getClinicId());
            appointment.setPrice(price);
            appointment.setDurationMinutes(durationMinutes);
            appointment.setActionPlan(actionPlan);
            appointment.setConfirmed(confirmed);
            //appointment.setDate(new Timestamp(date.getTime()));
            appointment.setDate(date);
            
            appointmentsDao.insertAppointment(appointment);
        } catch (Exception ex) {
            Logger.getLogger(AddEditEventBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_schedule");
    }
    
    public Boolean checkIfTimeFits(String workinghours, Date appointmentdate){ //THIS IS THE STRING PARSER FOR WORKING TIMES
    //get a list of appointment dates
    //convert dates to my format
    //string style: xxxxxxx*s/e
    //check for SHIT
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-EEE hh:mm");
    String appdatetext = dateFormat.format(appointmentdate);
    String[] daysofweek = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    for(int i=0;i<=6;i++){
    
        if (appdatetext.contains(daysofweek[i])){
            //we know which day it is now. Time to check if it matches workinghours
            char mychar = workinghours.charAt(i);
            if(mychar == '1'){
            //day is available. Now to check for opening time. TODO LATER
            
            }
        }
    }
        
        
    return true;
            }
    
}
