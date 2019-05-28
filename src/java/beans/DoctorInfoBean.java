package beans;

import daos.DoctorDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Doctor;
/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "doctorInfoBean")
@ViewScoped
public class DoctorInfoBean implements Serializable{
    private final DoctorDao doctorDao = new DoctorDao();
    private Doctor doctor;
    private int doctorId;
    private String doctorName;
    private String username;
    private String password;    
    private String specialization;
    private String workHours;
    private String description;
    private String phone;
    
    private boolean isUpdated = false;
    
    
    @Inject
    private SessionBean sessionBean;
    
    public DoctorInfoBean() {        
    }
    
    @PostConstruct
    public void init(){                
        try {
            doctorId = sessionBean.getDoctorId();
            
            if(doctorId > 0){
                doctor = doctorDao.getDoctor(doctorId);
                doctorName = doctor.getDoctorName();
                username = doctor.getUsername();
                password = doctor.getPassword();
                specialization = doctor.getSpecialization();
                workHours = doctor.getWorkHours();
                description = doctor.getDescription();
                phone = doctor.getPhone();
            }
        } catch (Exception ex) {
            Logger.getLogger(DoctorInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }
    
    public void saveDoctor() {
        try {
            doctor.setDoctorName(this.doctorName);
            doctor.setUsername(this.username);
            doctor.setPassword(this.password);
            doctor.setPhone(this.phone);
            doctor.setSpecialization(this.specialization);
            doctor.setWorkHours(this.workHours);
            doctor.setDescription(this.description);
            
            if (sessionBean.getDoctorId()> 0) {
                doctorDao.updateDoctor(doctor);
            } else {
                doctorDao.insertDoctor(doctor);
            }
            this.setIsUpdated(true);
        } catch (Exception ex) {
            this.setIsUpdated(false);
            Logger.getLogger(DoctorInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("doctor_info");
    }
}
