import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class GUIFrame2 extends GUIController {
    private static final JPanel panel = new JPanel();
    private Consultation currentConsultation;
    private GUIFrame2_PatientDetailsSection patientDetailsSection;
    private GUIFrame2_ConsultationDetailsSection consultationDetailsSection;
    private GUIFrame2_ExtraDetailsSection extraDetailsSection;
    private final JPanel topContainerPanel = new JPanel();
    private final JPanel bottomNavigationPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    private final JButton continueBtn = new JButton("Continue");
    private final JButton backBtn = new JButton("Back to edit");
    private final JButton confirmBtn = new JButton("Confirm & make the appointment");
    private Doctor selectedDoctor;
    private Date selectedDate;
    private String startingTime;
    private String endingTime;
    private Patient patient;
    private String extraDetails ;
    private ArrayList<String> imageReferenceList;
    private ArrayList<Image> tempImageList;

    public GUIFrame2() {
        panel.setPreferredSize(new Dimension(950, 710));
        topContainerPanel.setLayout(new BoxLayout(topContainerPanel, BoxLayout.Y_AXIS));
        loadInitialSections();

        continueBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedDoctor = westminsterSkinConsultationManager.getDoctorFromFullName(consultationDetailsSection.getSelectedDoctor());
                try {
                    selectedDate = consultationDetailsSection.getDate();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                startingTime = consultationDetailsSection.getFromTime();
                endingTime = consultationDetailsSection.getToTome();
                patient = patientDetailsSection.getPatient();
                try {
                    extraDetails = EncryptionHandler.encryptText(extraDetailsSection.getExtraDetails());
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
                    ex.printStackTrace();
                }
                imageReferenceList = extraDetailsSection.getImageReferenceList();
                tempImageList = extraDetailsSection.getTempImageList();

                try {
                    if(patient != null && validateConsultationDetails(selectedDoctor)) {
                        addNewConsultation(selectedDoctor);
                    } else if(patient != null) {
                        Doctor randomlySelectedDoctor = getRandomlySelectedDoctor();

                        if(randomlySelectedDoctor != null) {
                            addNewConsultation(randomlySelectedDoctor);
                        } else {
                            JOptionPane.showMessageDialog(null, "Sorry! Not available " + selectedDoctor.getSpecialisation() + " specialized doctors at the given time", "Notification", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bottomNavigationPanel.removeAll();
                bottomNavigationPanel.add(continueBtn);
                panel.removeAll();
                panel.add(topContainerPanel);
                panel.add(bottomNavigationPanel);
                refreshPanels(panel);
            }
        });

        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                westminsterSkinConsultationManager.getConsultationDataList().add(currentConsultation);
                westminsterSkinConsultationManager.getPatientList().add(patient);
                westminsterSkinConsultationManager.savePatients();
                westminsterSkinConsultationManager.saveConsultations();
                JOptionPane.showMessageDialog(null, "Appointment placed successfully!\nPatient name: " + currentConsultation.getPatient().getFullName() + "\nDoctor name: " + currentConsultation.getDoctor().getFullName() + "\nDate: " + currentConsultation.getDate() + "\nTime: " + currentConsultation.getTimeDuration(), "Notification", JOptionPane.INFORMATION_MESSAGE);
                refreshAddConsultationWindow();
            }
        });
    }

    private void refreshPanels(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Calculate the cost of the consultation.
     * @return Calculated cost in int.
     */
    private int getCalculatedCost() {
        for (Patient patient : westminsterSkinConsultationManager.getPatientList()) {
            if(this.patient.getId().equals(patient.getId())) {
                 return currentConsultation.getTimeDurationCount() * Consultation.oldPatientHourlyRate;
            }
        }

        currentConsultation.setNewUserBonusApplied(true);
        return currentConsultation.getTimeDurationCount() * Consultation.newPatientHourlyRate;
    }

    private boolean validateConsultationDetails(Doctor selectedDoctor) {
        for(Consultation consultation : westminsterSkinConsultationManager.getConsultationDataList()) {
            if(Objects.equals(consultation.getDoctor().getMedicalLicenseNo(), selectedDoctor.getMedicalLicenseNo()) && consultation.getDate().equals(selectedDate) && !consultation.isDoctorAvailable(startingTime, endingTime)) {
                return false;
            }
        }

        return true;
    }

    private Doctor getRandomlySelectedDoctor() throws ParseException {
        ArrayList<Doctor> sortedDoctorList = westminsterSkinConsultationManager.getDoctorsInSelectedSpecialisation(selectedDoctor.getSpecialisation());
        Collections.shuffle(sortedDoctorList);

        for(Doctor doctor : sortedDoctorList) {
            if(validateConsultationDetails(doctor)) {
                return doctor;
            }
        }

        return null;
    }

    private int getRandomConsultationId() {
        while (true) {
            boolean isDuplicateFound = false;
            int randomNumber = ThreadLocalRandom.current().nextInt(999, 99999);

            for (Consultation consultation : westminsterSkinConsultationManager.getConsultationDataList()) {
                if(consultation.getId() == randomNumber) {
                    isDuplicateFound = true;
                    break;
                }
            }

            if(!isDuplicateFound) {
                return randomNumber;
            }
        }
    }

    private void addNewConsultation(Doctor doctor) {
        currentConsultation = new Consultation(getRandomConsultationId(), doctor, patient, selectedDate, startingTime, endingTime);
        currentConsultation.setCost(getCalculatedCost());
        currentConsultation.setExtraDetails(extraDetails);
        currentConsultation.setImageReferenceList(imageReferenceList);
        currentConsultation.setTempImageList(tempImageList);
        loadConfirmationSection();
    }

    private void loadConfirmationSection() {
        GUIFrame2_FormConfirmationSection formConfirmationSection = new GUIFrame2_FormConfirmationSection(currentConsultation);
        bottomNavigationPanel.removeAll();
        bottomNavigationPanel.add(backBtn);
        bottomNavigationPanel.add(confirmBtn);
        panel.removeAll();
        panel.add(formConfirmationSection.getPanel());
        panel.add(bottomNavigationPanel);
        refreshPanels(panel);
    }

    private void refreshAddConsultationWindow() {
        bottomNavigationPanel.removeAll();
        topContainerPanel.removeAll();
        panel.removeAll();
        refreshPanels(bottomNavigationPanel);
        refreshPanels(topContainerPanel);
        refreshPanels(panel);
        loadInitialSections();
    }

    private void loadInitialSections() {
        patientDetailsSection = new GUIFrame2_PatientDetailsSection();
        consultationDetailsSection = new GUIFrame2_ConsultationDetailsSection();
        extraDetailsSection = new GUIFrame2_ExtraDetailsSection();
        topContainerPanel.add(patientDetailsSection.getPanel());
        topContainerPanel.add(consultationDetailsSection.getPanel());
        topContainerPanel.add(extraDetailsSection.getPanel());
        bottomNavigationPanel.add(continueBtn);
        panel.add(topContainerPanel);
        panel.add(bottomNavigationPanel);
        refreshPanels(bottomNavigationPanel);
        refreshPanels(topContainerPanel);
        refreshPanels(panel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
