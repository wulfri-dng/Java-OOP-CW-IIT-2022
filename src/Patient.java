import java.util.Calendar;

public class Patient extends Person {
    private final String id;

    public Patient(String name, String surname, String mobileNumber, Calendar dob, String gender, String id) {
        super(name, surname, mobileNumber, dob, gender);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
