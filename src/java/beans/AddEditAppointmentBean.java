package beans;

import daos.AppointmentsDao;
import daos.PatientDao;
import daos.ClinicDao;
import daos.DoctorDao;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Appointment;
import models.Patient;
import models.Clinic;
import models.Doctor;
import java.util.ArrayList;
/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "addEditAppointmentBean")
@ViewScoped
public class AddEditAppointmentBean implements Serializable{
    private final AppointmentsDao appointmentsDao = new AppointmentsDao();
    private final PatientDao patientDao = new PatientDao();
    private final ClinicDao clinicDao = new ClinicDao();
    private final DoctorDao doctorDao = new DoctorDao();

    private ArrayList<Patient> patients;
    private Doctor doctor;

    private ArrayList<Clinic> clinics;
    private int appointmentId;
    private int nameDoctor;
    private int namePatient;
    private int clinicName;
    private Date date;
    private int patientId;
    private int doctorId;
    private int price;
    private int phone;
    private int durationMinutes;
    private String actionPlan;
    private Boolean confirmed;  
    
    @Inject
    private SessionBean sessionBean;
    
    public AddEditAppointmentBean() {        
    }
    
    @PostConstruct
    public void init(){                
        try {
            appointmentId = sessionBean.getSelectedItemId();
            patients = patientDao.buildpatients();
            clinics = clinicDao.getClinicsByDoctorID(sessionBean.getDoctorId());
            doctor = doctorDao.getDoctor(sessionBean.getDoctorId());
            if(appointmentId > 0){
                Appointment appointment = appointmentsDao.getAppointmentById(appointmentId);                
                nameDoctor = appointment.getDoctorId();
                namePatient = appointment.getPatientId();
                clinicName = appointment.getClinicId();
                date = appointment.getDate();
                patientId = appointment.getPatientId();
                doctorId = appointment.getDoctorId();
                price = appointment.getPrice();
                durationMinutes = appointment.getDurationMinutes();
                actionPlan = appointment.getActionPlan();
                confirmed = appointment.getConfirmed();
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public int getNameDoctor() {
        return nameDoctor;
    }
    
    public void setNameDoctor(int nameDoctor) {
        this.nameDoctor = nameDoctor;
    }
    
    public int getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(int namePatient) {
        this.namePatient = namePatient;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public ArrayList<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(ArrayList<Clinic> clinics) {
        this.clinics = clinics;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    
    public int getClinicName() {
        return clinicName;
    }

    public void setClinicName(int clinicName) {
        this.clinicName = clinicName;
    }
    
    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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
        
    public void saveAppointment() {
        try {
            Appointment appointment = new Appointment();
            
            appointment.setDoctorId(doctor.getDoctorId());
            appointment.setPatientId(namePatient);
            appointment.setClinicId(clinicName);
            appointment.setPrice(price);
            appointment.setDurationMinutes(durationMinutes);
            appointment.setActionPlan(actionPlan);
            appointment.setConfirmed(confirmed);
            appointment.setDate(date);
            if (sessionBean.getSelectedItemId() > 0) {
                appointmentsDao.updateAppointment(appointment);
            } else {
                appointmentsDao.insertAppointment(appointment);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddEditAppointmentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("manage_schedule");
    }
}
