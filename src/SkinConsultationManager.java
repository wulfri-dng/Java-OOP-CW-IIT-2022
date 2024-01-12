import java.util.ArrayList;
import java.util.Calendar;

public interface SkinConsultationManager {
    void addNewDoctor();
    void deleteDoctor();
    void printDoctors();
    void saveDoctors();
    void readDoctorData();

    String inputMedicalLicenseNumber(boolean checkDuplicate);
    String inputGenderValue();
    String inputStringValue(String displayMessage);
    String inputMobileNumberValue(String displayMessage);
    Calendar inputDateValue(String displayMessage);

    ArrayList<String> getDoctorNameList();
    ArrayList<String> getDoctorNameList(String specialisation);
    Doctor getDoctorFromFullName(String fullName);
    ArrayList<String> getDoctorSpecialisationList();
    ArrayList<Doctor> getDoctorsInSelectedSpecialisation(String specialisation);
    ArrayList<Doctor> getDoctorList();
    ArrayList<Consultation> getConsultationDataList();
    ArrayList<Patient> getPatientList();

    void saveConsultations();
    void readConsultationData();
    void savePatients();
    void readPatientsData();
    void saveConfigData();
    void readConfigData();
}
