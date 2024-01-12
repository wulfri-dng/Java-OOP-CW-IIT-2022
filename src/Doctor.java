import java.util.Calendar;

public class Doctor extends Person {
    private final String medicalLicenseNo;
    private final String specialisation;

    public Doctor(String name, String surname, String mobileNumber, Calendar dob, String gender, String medicalLicenseNo, String specialisation) {
        super(name, surname, mobileNumber, dob, gender);
        this.medicalLicenseNo = medicalLicenseNo;
        this.specialisation = specialisation;
    }

    public String getMedicalLicenseNo() {
        return medicalLicenseNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }
}
