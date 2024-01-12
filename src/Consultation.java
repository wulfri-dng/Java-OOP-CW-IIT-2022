import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Consultation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    static String[] timeSlots = {"2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00"};
    static ArrayList<String> imageIdList = new ArrayList<>();
    static String imagesFolderLocation = "appData\\images\\";
    static String imagesDecryptLocation = "appData\\temp\\";
    static int newPatientHourlyRate = 15;
    static int oldPatientHourlyRate = 25;
    private final int id;
    private final Doctor doctor;
    private final Patient patient;
    private final Date date;
    private final String startingTime;
    private final String endingTime;
    private int cost;
    private String extraDetails;
    private ArrayList<String> imageReferenceList;
    private ArrayList<Image> tempImageList;
    private boolean isNewUserBonusApplied = false;

    public Consultation(int id, Doctor doctor, Patient patient, Date date, String startingTime, String endingTime) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
    }

    /**
     * This special method use to check the current doctor's availability of a given date.
     * This use when making a consultation to avoid clashes between doctors and appointment times.
     * @param startingTime Consultation starting time of the given consultation.
     * @param endingTime Consultation ending time of the given consultation.
     * @return Return true when this doctor available at the given time and false when not available.
     */
    public boolean isDoctorAvailable(String startingTime, String endingTime) {
        int startingTimeSlotIndex = Arrays.asList(timeSlots).indexOf((String) this.startingTime);
        int endingTimeSlotIndex = Arrays.asList(timeSlots).indexOf((String) this.endingTime);

        String[] updatedTimeSlots = Arrays.copyOfRange(timeSlots, startingTimeSlotIndex, endingTimeSlotIndex + 1);

        for(String slot : updatedTimeSlots) {
            if(slot.equals(startingTime) || slot.equals(endingTime)) {
                return startingTime.equals(updatedTimeSlots[updatedTimeSlots.length - 1]) || endingTime.equals(updatedTimeSlots[0]);
            }
        }

        return true;
    }

    /**
     * Get time duration of current consultation in String.
     * @return Time duration in String.
     */
    public String getTimeDuration() {
        return startingTime + " - " + endingTime;
    }

    /**
     * Get time duration of current consultation in Integer.
     * @return Time duration in Integer.
     */
    public int getTimeDurationCount() {
        int startingTimeSlotIndex = Arrays.asList(timeSlots).indexOf((String) this.startingTime);
        int endingTimeSlotIndex = Arrays.asList(timeSlots).indexOf((String) this.endingTime);

        return endingTimeSlotIndex - startingTimeSlotIndex;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public int getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Date getDate() {
        return date;
    }

    public String getExtraDetails() {
        return extraDetails;
    }

    public void setExtraDetails(String extraDetails) {
        this.extraDetails = extraDetails;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isNewUserBonusApplied() {
        return isNewUserBonusApplied;
    }

    public void setNewUserBonusApplied(boolean newUserBonusApplied) {
        isNewUserBonusApplied = newUserBonusApplied;
    }

    public ArrayList<String> getImageReferenceList() {
        return imageReferenceList;
    }

    public void setImageReferenceList(ArrayList<String> imageReferenceList) {
        this.imageReferenceList = imageReferenceList;
    }

    public ArrayList<Image> getTempImageList() {
        return tempImageList;
    }

    public void setTempImageList(ArrayList<Image> tempImageList) {
        this.tempImageList = tempImageList;
    }
}
