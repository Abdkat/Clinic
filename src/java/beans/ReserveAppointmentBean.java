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
import java.util.ArrayList;
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
    private String workhoursDays;
    private String workhoursTime;
    private String errorMsg;
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
                specialization = doctor.getSpecialization();
                workhoursDays = "";             // must be added as a doctor table column
                price = 50;
                durationMinutes = 30;
                confirmed = false;
                errorMsg = "";
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

    public String getWorkhoursDays() {
        String[] daysofweek = {"Su","Mo","Tu","We","Th","Fr","Sa"};
        for(int i=0;i<this.workHours.indexOf('*');i++){
            if(workHours.charAt(i)=='1'){
                workhoursDays = workhoursDays+daysofweek[i]+" ";
            }
        }
        return workhoursDays;
    }
    
    public String getWorkhoursTime() {
        workhoursTime = "From: "+workHours.substring( workHours.indexOf('*')+1,workHours.indexOf('/'))
                      + " to "+workHours.substring( workHours.indexOf('/')+1);
        return workhoursTime;
    }

    
    public PatientClass getPatient() {
        return patient;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
            if(checkIfTimeOverlaps(doctor.getDoctorId(), appointment.getDurationMinutes(), appointment.getDate()) &&
                    checkIfTimeFits(doctor.getWorkHours(), date))
            {
                appointmentsDao.insertAppointment(appointment);
                sessionBean.navigate("/patient/my_appointments");
            }
            else {                
                sessionBean.navigate("reserve_appointment"); //CHANGE THIS TO SHOW THE USER AN ERROR SAYING BAD TIME
                errorMsg = "Date is not available";
            
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditEventBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_schedule");
    }
    
    public Boolean checkIfTimeFits(String workinghours, Date appointmentdate){ //THIS IS THE STRING PARSER FOR WORKING TIMES
    //get a list of appointment dates
    //convert dates to my format
    //string format: xxxxxxx*s/e
    //check for 
    //System.out.print("LOLTEST");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-EEE hh:mm");
    String appdatetext = dateFormat.format(appointmentdate);
    //System.err.print(appdatetext);
    String[] daysofweek = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    String workinghoursday = workinghours;
    String delims="[*]";
    String[] workinghoursparsed = workinghoursday.split(delims);
    //System.out.print(workinghoursparsed[1]);
    String delims2="[/]";
    String[] startandendhours = workinghoursparsed[1].split(delims2);
   // System.out.print(startandendhours[0]);
    for(int i=0;i<=6;i++){
    
        if (appdatetext.contains(daysofweek[i])){
            //we know which day it is now. Time to check if it matches workinghours
            char mychar = workinghours.charAt(i);
            if(mychar == '1'){
            //day is available. Now to check for opening time. TODO LATER
                int datehour = appointmentdate.getHours();
                int startinghour = Integer.parseInt(startandendhours[0]);
                int endhour = Integer.parseInt(startandendhours[1]);
                if (datehour >= startinghour && datehour <= endhour){
                System.out.print("DATE SAVED");
                errorMsg = "DATE SAVED";
                return true;
                }
                
            }
        }
    }
    System.out.print("DATE FAILED!");
    errorMsg = "DATE FAILED";
    return false;
}
    public Boolean checkIfTimeOverlaps(int doctorid, int durationminutes, Date appdate){
        try{
            ArrayList<AppointmentClass> appointments = appointmentsDao.getAppointmentByDoctorId(doctorid);
            for (AppointmentClass apps: appointments){
                Date updateddate = addHoursToJavaUtilDate(apps.getDate(), 1);
                System.out.print(apps.getDate());
                //System.out.print(updateddate);
                System.out.print(apps.getDate().after(appdate));
                System.out.print(apps.getDate().before((updateddate)));
                if((appdate.after(apps.getDate()) && appdate.before((updateddate)))
                        || apps.getDate().compareTo(appdate) == 0){
                    //TIMING DOES NOT FIT.
                    
                    System.out.print("THE DATE ALREADY EXISTS. CHOOSE ANOTHER");
                    errorMsg = "THE DATE ALREADY EXISTS. CHOOSE ANOTHER";
                    return false;//2019-05-24 17:00
                }
            }
        }
        catch (Exception ex) {
            Logger.getLogger(AddEditEventBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.print("THE DATE IS FINE.");
        errorMsg = "THE DATE IS FINE.";
        return true;
    }
    
    public Date addHoursToJavaUtilDate(Date date, int hour){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }
    
    public void main(String [] args){        
        try {
            //Date mydate = new Date();
            //System.out.print(mydate);
            //Boolean mybool = checkIfTimeFits("1111100*3/24", mydate);
            //System.out.print(mybool);
        } catch (Exception ex) {
            Logger.getLogger(AppointmentsDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
