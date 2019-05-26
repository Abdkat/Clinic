package models;

import java.io.Serializable;

/**
 *
 * @author Firas.Alhawari
 * 
 */
public class DoctorClass implements Serializable{
    private String doctorName;
    private int doctorId;
    private String specialization;
    private String workHours;
    private String description;
    private String phone;
    private String username;
    private String password;
    
    // helper attributes 
    private ClinicClass clinic;
    
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClinicClass getClinic() {
        return clinic;
    }

    public void setClinic(ClinicClass clinic) {
        this.clinic = clinic;
    }

}