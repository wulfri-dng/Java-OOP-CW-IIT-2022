import javax.swing.*;
import java.awt.*;

public class ConsultationPreview {
    private final JPanel consultationPreviewPanel = new JPanel(new GridBagLayout());

    public ConsultationPreview(Consultation consultation, int width, int height) {
        consultationPreviewPanel.setPreferredSize(new Dimension(width, height));
        consultationPreviewPanel.setLayout(new BoxLayout(consultationPreviewPanel, BoxLayout.Y_AXIS));

        JPanel patientDetails = new JPanel(new GridBagLayout());
        patientDetails.setPreferredSize(new Dimension(880, 200));
        patientDetails.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints patientGBC = new GridBagConstraints();

        JPanel consultationDetails = new JPanel(new GridBagLayout());
        consultationDetails.setPreferredSize(new Dimension(880, 230));
        consultationDetails.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints consultationsGBC = new GridBagConstraints();

        JPanel extraDetails = new JPanel(new GridBagLayout());
        extraDetails.setPreferredSize(new Dimension(880, 200));
        extraDetails.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints extraGBC = new GridBagConstraints();

        CustomImageGallery customImageGallery = new CustomImageGallery();
        customImageGallery.setGalleryData(consultation.getTempImageList(), 420, 120,1, 3, 135, 120);
        customImageGallery.refreshGallery();

        // Patient Details Section -----------------------
        CustomHeadingLabel patientDetailsHeading = new CustomHeadingLabel("Patient details section", 280, 40, 0, 0, 5, 0);
        patientDetails.add(patientDetailsHeading.getHeading(), patientGBC);

        CustomLabelField firstNameLabel = new CustomLabelField("Patient Name:", consultation.getPatient().getFullName(), 280, 40, 0, 0, 0, 0);
        patientGBC.gridy = 1;
        patientDetails.add(firstNameLabel.getPanel(), patientGBC);

        CustomLabelField patientMobileNumber = new CustomLabelField("Patient Mobile Number:", consultation.getPatient().getMobileNumber(), 280, 40, 0, 0, 0, 0);
        patientGBC.gridx = 1;
        patientDetails.add(patientMobileNumber.getPanel(), patientGBC);

        CustomLabelField patientGender = new CustomLabelField("Patient Gender:", consultation.getPatient().getGender(), 280, 40, 0, 0, 0, 0);
        patientGBC.gridx = 2;
        patientDetails.add(patientGender.getPanel(), patientGBC);

        CustomLabelField patientIDNumber = new CustomLabelField("Patient ID Number:", "" + consultation.getPatient().getId(), 280, 40, 0, 0, 0, 0);
        patientGBC.gridx = 0;
        patientGBC.gridy = 2;
        patientDetails.add(patientIDNumber.getPanel(), patientGBC);

        CustomLabelField patientBirthday = new CustomLabelField("Patient Birthday:", Validator_Date.previewDateInReadableFormat(consultation.getPatient().getDob().getTime()), 560, 40, 0, 0, 0, 0);
        patientGBC.gridx = 1;
        patientGBC.gridwidth = 2;
        patientDetails.add(patientBirthday.getPanel(), patientGBC);

        // Consultation Details Section -----------------------
        CustomHeadingLabel consultationDetailsHeading = new CustomHeadingLabel("Consultation details section", 420, 40, 0, 0, 5, 0);
        consultationDetails.add(consultationDetailsHeading.getHeading(), consultationsGBC);

        CustomLabelField doctorName = new CustomLabelField("Doctor Name:", consultation.getDoctor().getFullName(), 420, 40, 0, 0, 0, 0);
        consultationsGBC.gridy = 1;
        consultationDetails.add(doctorName.getPanel(), consultationsGBC);

        CustomLabelField specialisation = new CustomLabelField("Specialisation:", consultation.getDoctor().getSpecialisation(), 420, 40, 0, 0, 0, 0);
        consultationsGBC.gridx = 1;
        consultationsGBC.gridy = 1;
        consultationDetails.add(specialisation.getPanel(), consultationsGBC);

        CustomLabelField date = new CustomLabelField("Date:", Validator_Date.previewDateInReadableFormat(consultation.getDate()), 420, 40, 0, 0, 0, 0);
        consultationsGBC.gridx = 0;
        consultationsGBC.gridy = 2;
        consultationDetails.add(date.getPanel(), consultationsGBC);

        CustomLabelField timeDuration = new CustomLabelField("Time Duration:", consultation.getTimeDuration(), 420, 40, 0, 0, 0, 0);
        consultationsGBC.gridx = 1;
        consultationsGBC.gridy = 2;
        consultationDetails.add(timeDuration.getPanel(), consultationsGBC);

        String cost = "£" + consultation.getCost();
        String hourlyRate = "£" + Consultation.oldPatientHourlyRate;

        if(consultation.isNewUserBonusApplied()) {
            hourlyRate = "£" + Consultation.newPatientHourlyRate;
            cost += " (New user bonus applied)";
        }

        CustomLabelField costLabel = new CustomLabelField("Cost:", cost, 420, 40, 0, 0, 0, 0);
        consultationsGBC.gridx = 0;
        consultationsGBC.gridy = 3;
        consultationDetails.add(costLabel.getPanel(), consultationsGBC);

        CustomLabelField hourlyRateLabel = new CustomLabelField("Hourly rate:", hourlyRate, 420, 40, 0, 0, 0, 0);
        consultationsGBC.gridx = 1;
        consultationDetails.add(hourlyRateLabel.getPanel(), consultationsGBC);

        consultationPreviewPanel.add(patientDetails);
        consultationPreviewPanel.add(consultationDetails);
    }

    public JPanel getConsultationPreviewPanel() {
        return consultationPreviewPanel;
    }
}
