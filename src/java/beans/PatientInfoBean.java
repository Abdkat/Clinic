package beans;

import daos.PatientDao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.PatientClass;
/**
 *
 * @author Firas.Alhawari
 * 
 */
@Named(value = "patientInfoBean")
@ViewScoped
public class PatientInfoBean implements Serializable{
    private final PatientDao patientDao = new PatientDao();
    private PatientClass patient;
    private int patientId;
    private String patientName;
    private String username;
    private String password;
    private String phone;
    
    private boolean isUpdated = false;
    
    
    @Inject
    private SessionBean sessionBean;
    
    public PatientInfoBean() {        
    }
    
    @PostConstruct
    public void init(){                
        try {
            patientId = sessionBean.getPatientId();
            
            if(patientId > 0){
                patient = patientDao.getPatient(patientId);
                patientName = patient.getPatientName();
                username = patient.getUsername();
                password = patient.getPassword();
                phone = patient.getPhone();
            }
        } catch (Exception ex) {
            Logger.getLogger(DoctorInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }
    
    public void savePatient() {
        try {
            patient.setPatientName(this.patientName);
            patient.setUsername(this.username);
            patient.setPassword(this.password);
            patient.setPhone(this.phone);
            
            if (sessionBean.getPatientId()> 0) {
                patientDao.updatePatient(patient);
            } else {
                patientDao.insertPatient(patient);
            }
            this.setIsUpdated(true);
        } catch (Exception ex) {
            this.setIsUpdated(false);
            Logger.getLogger(PatientInfoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        sessionBean.navigate("patient_info");
    }
}
