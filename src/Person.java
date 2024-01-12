import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;

public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private final String surname;
    private final String mobileNumber;
    private final Calendar dob;
    private final String gender;

    public Person(String name, String surname, String mobileNumber, Calendar dob, String gender) {
        this.name = name;
        this.surname = surname;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Calendar getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
