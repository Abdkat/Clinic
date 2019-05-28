package models;

import java.io.Serializable;

/**
 *
 * @author Firas.Alhawari
 * 
 */
public class Clinic implements Serializable{
    private String name;
    private int clinicId;
    private String description;
    private Doctor[] doctors;
    private String phoneNumber;
    private String workHours;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Doctor[] getDoctors() {
        return doctors;
    }

    public void setDoctors(Doctor[] doctors) {
        this.doctors = doctors;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }
    
}