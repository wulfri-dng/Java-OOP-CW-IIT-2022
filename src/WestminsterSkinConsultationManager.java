import java.io.*;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    private static final ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
    private static final ArrayList<Consultation> consultationDataList = new ArrayList<Consultation>();
    private static final ArrayList<Patient> patientList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Add new doctors to the system.
     */
    @Override
    public void addNewDoctor() {
        if (doctorList.size() < 10) {
            System.out.println("----------- Enter a new doctor -----------");
            String firstName = inputStringValue("First name");
            String surname = inputStringValue("Surname");
            String gender = inputGenderValue();
            Calendar dob = inputDateValue("Date of birth");
            String mobileNumber = inputMobileNumberValue("Mobile no");
            String medicalLicenseNumber = inputMedicalLicenseNumber(true);

            if (medicalLicenseNumber != null) {
                StringBuilder specialisationList = new StringBuilder();

                if (getDoctorSpecialisationList().size() > 0) {
                    specialisationList.append("Existing specialisations : \n");
                }

                for (int i = 0; i < getDoctorSpecialisationList().size(); i++) {
                    specialisationList.append(i + 1).append(") ").append(getDoctorSpecialisationList().get(i)).append("\n");
                }

                String specialisation = inputStringValue(specialisationList + "Specialisation");
                Doctor doctor = new Doctor(firstName, surname, mobileNumber, dob, gender, medicalLicenseNumber, specialisation);
                doctorList.add(doctor);
                System.out.println("New doctor " + doctor.getFullName() + " successfully added!");
            } else {
                System.out.println("Entered doctor already exist. Please check the doctor list.\nMoving to main menu...");
            }
        } else {
            System.out.println("Doctor list has reached to it's maximum limit of 10.\nYou cannot add anymore doctors without deleting a existing one.");
        }
    }

    /**
     * Delete already existing doctor from the system.
     */
    @Override
    public void deleteDoctor() {
        System.out.println("----------- Delete doctor -----------");

        String userInput = inputMedicalLicenseNumber(false);

        boolean isDoctorFound = false;
        for (int i = 0; i < doctorList.size(); i++) {
            if (doctorList.get(i).getMedicalLicenseNo().equals(userInput)) {
                isDoctorFound = true;
                Doctor removedDoctorDetails = doctorList.get(i);
                doctorList.remove(doctorList.get(i));
                System.out.println("Doctor removed! Removed doctor details:");
                System.out.println("Available total number of doctors: " + doctorList.size());
                System.out.println("---------------------------------------");
                System.out.println("Name                   : " + removedDoctorDetails.getFullName());
                System.out.println("Mobile Number          : " + removedDoctorDetails.getMobileNumber());
                System.out.println("Gender                 : " + removedDoctorDetails.getGender());
                System.out.println("Specialization         : " + removedDoctorDetails.getSpecialisation());
                System.out.println("Medical License Number : " + removedDoctorDetails.getMedicalLicenseNo());
            }
        }

        if(!isDoctorFound) {
            System.out.println("Invalid medical license number! Check the number and try again.");
        }
    }

    /**
     * Print all doctors on the console alphabetically based on surnames.
     */
    @Override
    public void printDoctors() {
        System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("                                         Westminster consultation management system - Available doctors                                           %n");
        System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-2s | %-15s | %-15s | %-15s | %-12s | %-15s | %-28s | %-19s |%n", "No", "Name", "surname", "Mobile Number", "Gender", "DOB", "Specialization", "Medical License No.");
        System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------%n");

        ArrayList<Doctor> doctorTemp = doctorList;
        doctorTemp.sort(Comparator.comparing(Doctor::getSurname));

        for (int i = 0; i < doctorTemp.size(); i++) {
            Doctor doctor = doctorTemp.get(i);
            System.out.printf("| %-2s | %-15s | %-15s | %-15s | %-12s | %-15s | %-28s | %-19s |%n", i + 1, doctor.getName(), doctor.getSurname(), doctor.getMobileNumber(), doctor.getGender(), Validator_Date.previewDateInReadableFormat(doctor.getDob().getTime()), doctor.getSpecialisation(), doctor.getMedicalLicenseNo());
        }

        System.out.printf("--------------------------------------------------------------------------------------------------------------------------------------------------%n");
    }

    /**
     * Get a full names list of doctors. (First name + last name)
     * @return Doctor full name list.
     */
    @Override
    public ArrayList<String> getDoctorNameList() {
        ArrayList<String> doctorNameList = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            doctorNameList.add(doctor.getName() + " " + doctor.getSurname());
        }

        return doctorNameList;
    }

    /**
     * Get a full name list of doctors which in selected specialisation.
     * @param specialisation Selected specialisation. Returns value based on this.
     * @return List of doctor full names in selected specialisation.
     */
    @Override
    public ArrayList<String> getDoctorNameList(String specialisation) {
        ArrayList<String> doctorNameList = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            if (doctor.getSpecialisation().equals(specialisation)) {
                doctorNameList.add(doctor.getFullName());
            }
        }

        return doctorNameList;
    }

    /**
     * Get Doctor object from doctor's full name.
     * @param fullName Doctor's full name.
     * @return Doctor object.
     */
    @Override
    public Doctor getDoctorFromFullName(String fullName) {
        Doctor selectedDoctor = null;

        for (Doctor doctor : doctorList) {
            if (doctor.getFullName().equals(fullName)) {
                selectedDoctor = doctor;

                break;
            }
        }

        return selectedDoctor;
    }

    /**
     * Get doctor specialisations in a single list.
     * @return List of doctor specialisations.
     */
    @Override
    public ArrayList<String> getDoctorSpecialisationList() {
        ArrayList<String> specialisationList = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            String specialisation = doctor.getSpecialisation();

            if (specialisationList.size() == 0) {
                specialisationList.add(specialisation);
            } else if (!specialisationList.contains(specialisation)) {
                specialisationList.add(specialisation);
            }
        }

        return specialisationList;
    }

    /**
     * Get a Doctor object list of doctors which in selected specialisation.
     * @param specialisation Selected specialisation. Returns value based on this.
     * @return List of Doctor objects in selected specialisation.
     */
    @Override
    public ArrayList<Doctor> getDoctorsInSelectedSpecialisation(String specialisation) {
        ArrayList<Doctor> sortedDoctorList = new ArrayList<Doctor>();

        for (Doctor doctor : doctorList) {
            if (doctor.getSpecialisation().equals(specialisation)) {
                sortedDoctorList.add(doctor);
            }
        }

        return sortedDoctorList;
    }

    /**
     * Input user input and validate the doctor's medical license number.
     * @param checkDuplicate This method can be used for 2 purposes. One is just only to validate user entered license number and check weather is it on correct format.
     *                      And other one is to check weather are there any duplicated licenses numbers in the system additionally.
     * @return Validated license number or null.
     */
    @Override
    public String inputMedicalLicenseNumber(boolean checkDuplicate) {
        String licenseNumber;

        while (true) {
            licenseNumber = inputStringValue("Medical license no");

            if (licenseNumber.matches("[LPEDS][0-9]{8}")) {
                if (checkDuplicate) {
                    for (Doctor doctor : doctorList) {
                        if (doctor.getMedicalLicenseNo().equals(licenseNumber)) {
                            licenseNumber = null;
                            break;
                        }
                    }
                }

                break;
            } else {
                System.out.println("Invalid input! Please enter a valid medical license number with correct format.");
            }
        }

        return licenseNumber;
    }

    /**
     * Check and validate the gender.
     * @return Validated gender.
     */
    @Override
    public String inputGenderValue() {
        while (true) {
            String gender = inputStringValue("Gender");

            if (gender.toUpperCase(Locale.ROOT).equals("M") || gender.toUpperCase(Locale.ROOT).equals("MALE")) {
                return "Male";
            } else if (gender.toUpperCase(Locale.ROOT).equals("F") || gender.toUpperCase(Locale.ROOT).equals("FEMALE")) {
                return "Female";
            } else {
                System.out.println("Invalid input! Please enter a valid gender. (Format: M/F or Male/Female)");
            }
        }
    }

    /**
     * Reusable method to get Strings from the user and validate.
     * @param displayMessage Message which display to the user on the console when this method runs.
     * @return Validated string value.
     */
    @Override
    public String inputStringValue(String displayMessage) {
        String userInput = null;

        while (true) {
            try {
                System.out.print(displayMessage + ": ");
                userInput = scanner.nextLine();
                Double.parseDouble(userInput);
                System.out.println("Invalid input! Please enter a valid string.");
            } catch (NumberFormatException e) {
                break;
            } catch (NoSuchElementException ignored) {

            }
        }

        return userInput;
    }

    /**
     * Get a mobile number from the user and validate.
     * @param displayMessage Message which display to the user on the console when this method runs.
     * @return Validated mobile number.
     */
    @Override
    public String inputMobileNumberValue(String displayMessage) {
        String userInput;

        while (true) {
            try {
                System.out.print(displayMessage + ": ");
                userInput = scanner.nextLine();

                if (Validator_MobileNumber.isValidNumber(userInput)) {
                    return userInput;
                } else {
                    System.out.println("Invalid input! Please enter a valid mobile number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid mobile number.");
            }
        }
    }

    /**
     * Get a date from the user and validate.
     * @param displayMessage Message which display to the user on the console when this method runs.
     * @return Validated Date.
     */
    @Override
    public Calendar inputDateValue(String displayMessage) {
        String userInput = "";

        while (true) {
            try {
                System.out.print(displayMessage + ": ");
                userInput = scanner.nextLine();
                Double.parseDouble(userInput);
                System.out.println("Invalid input! Please enter a valid date. (Format: dd/MM/yyyy)");
            } catch (NumberFormatException e) {
                Calendar date = Validator_Date.getValidatedDate(userInput);
                if (date != null) {
                    return date;
                } else {
                    System.out.println("Invalid input! Please enter a valid date. (Format: dd/MM/yyyy)");
                }
            }
        }
    }

    /**
     * Save doctor data into a text file.
     */
    @Override
    public void saveDoctors() {
        try {
            File file = new File("appData\\DoctorsData.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Doctor doctor : doctorList) {
                objectOutputStream.writeObject(doctor);
            }

            System.out.println("Doctor details successfully saved!");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Read previously saved doctor data from the text file.
     */
    @Override
    public void readDoctorData() {
        try {
            FileInputStream fileInputStream = new FileInputStream("appData\\DoctorsData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    Doctor doctor = (Doctor) objectInputStream.readObject();
                    doctorList.add(doctor);
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Save consultation data into a text file.
     */
    @Override
    public void saveConsultations() {
        try {
            File file = new File("appData\\ConsultationData.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Consultation consultation : consultationDataList) {
                consultation.setTempImageList(null);
                objectOutputStream.writeObject(consultation);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Read previously saved consultation data from the text file.
     */
    @Override
    public void readConsultationData() {
        try {
            FileInputStream fileInputStream = new FileInputStream("appData\\ConsultationData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    Consultation consultation = (Consultation) objectInputStream.readObject();
                    consultationDataList.add(consultation);
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Save patients data into a text file.
     */
    @Override
    public void savePatients() {
        try {
            File file = new File("appData\\PatientData.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Patient patient : patientList) {
                objectOutputStream.writeObject(patient);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Read previously saved patients data from the text file.
     */
    @Override
    public void readPatientsData() {
        try {
            FileInputStream fileInputStream = new FileInputStream("appData\\PatientData.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    Patient patient = (Patient) objectInputStream.readObject();
                    patientList.add(patient);
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Save other important data into a text file. (Encryption key, imageIdList)
     */
    @Override
    public void saveConfigData() {
        try {
            File file = new File("appData\\config.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new Config(Consultation.imageIdList, EncryptionHandler.encryptionPrivateKey));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Read previously saved config data from the text file.
     */
    @Override
    public void readConfigData() {
        try {
            FileInputStream fileInputStream = new FileInputStream("appData\\config.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true) {
                try {
                    Config config = (Config) objectInputStream.readObject();
                    Consultation.imageIdList = config.getImageIdList();
                    EncryptionHandler.encryptionPrivateKey = config.getImageEncryptKey();
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        } catch (IOException ignored) {
        }
    }

    /**
     * Get the doctor list.
     * @return Doctor list.
     */
    @Override
    public ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }

    /**
     * Get the consultation list.
     * @return Consultation list.
     */
    @Override
    public ArrayList<Consultation> getConsultationDataList() {
        return consultationDataList;
    }

    /**
     * Get the patient list.
     * @return Patient list.
     */
    @Override
    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    /**
     * For unit testing purposes.
     * @return Get scanner object.
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * For unit testing purposes.
     * @param scanner Scanner object.
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}