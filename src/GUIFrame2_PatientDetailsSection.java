import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Objects;

public class GUIFrame2_PatientDetailsSection {
    private final JPanel patientPanel = new JPanel();
    private final CustomTextField firstNameField;
    private final CustomTextField surnameField;
    private final CustomTextField mobileNumberField;
    private final CustomTextField idNumberField;
    private final CustomDateSelector dobField;
    private final CustomDropdownSelector genderDropdownSelector;

    public GUIFrame2_PatientDetailsSection() {
        patientPanel.setPreferredSize(new Dimension(880, 250));
        patientPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        patientPanel.setLayout(new GridBagLayout());
        GridBagConstraints patientPanelGBC = new GridBagConstraints();

        CustomHeadingLabel customHeadingLabel = new CustomHeadingLabel("Patient details section", 420, 40, 0, 0, 5, 0);
        patientPanel.add(customHeadingLabel.getHeading(), patientPanelGBC);

        firstNameField = new CustomTextField(0, 0, 0, 30, 420, 50, "First Name:");
        patientPanelGBC.gridy = 1;
        patientPanel.add(firstNameField.getPanel(), patientPanelGBC);

        surnameField = new CustomTextField(0, 30, 0, 0, 420, 50, "Surname:");
        patientPanelGBC.gridx = 1;
        patientPanel.add(surnameField.getPanel(), patientPanelGBC);

        dobField = new CustomDateSelector(10, 0, 0, 30, 420, 60, "Date of Birth:");
        patientPanelGBC.gridx = 0;
        patientPanelGBC.gridy = 2;
        patientPanel.add(dobField.getPanel(), patientPanelGBC);

        mobileNumberField = new CustomTextField(10, 30, 0, 0, 420, 60, "Mobile Number:");
        patientPanelGBC.gridx = 1;
        patientPanel.add(mobileNumberField.getPanel(), patientPanelGBC);

        idNumberField = new CustomTextField(10, 0, 0, 30, 420, 60, "ID:");
        patientPanelGBC.gridx = 0;
        patientPanelGBC.gridy = 3;
        patientPanel.add(idNumberField.getPanel(), patientPanelGBC);

        genderDropdownSelector = new CustomDropdownSelector(10, 30, 0, 0, 420, 60, "gender:", new String[]{"Male", "Female"});
        patientPanelGBC.gridx = 1;
        patientPanel.add(genderDropdownSelector.getPanel(), patientPanelGBC);
    }

    private void popupError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    public Patient getPatient() {
        String firstName = firstNameField.getTextFieldValue();
        String surname = surnameField.getTextFieldValue();
        Calendar dob = dobField.getSelectedDate();
        String gender = Objects.requireNonNull(genderDropdownSelector.getComboBox().getSelectedItem()).toString();
        String mobileNumber = mobileNumberField.getTextFieldValue();
        String id = idNumberField.getTextFieldValue();

        String errorMessage = "";

        if (firstName.isEmpty()) {
            errorMessage += "First Name field is empty\n";
        }

        if (surname.isEmpty()) {
            errorMessage += "Surname field is empty\n";
        }

        if (dob.toString().equals("")) {
            errorMessage += "Date of Birth field is empty\n";
        }

        if (gender.isEmpty()) {
            errorMessage += "Gender field is empty\n";
        }

        if (mobileNumber.isEmpty()) {
            errorMessage += "Mobile Number field is empty\n";
        }

        if (id.isEmpty()) {
            errorMessage += "ID field is empty\n";
        }

        if (!errorMessage.isEmpty()) {
            popupError(errorMessage);
            return null;
        }

        if (!Validator_MobileNumber.isValidNumber(mobileNumber)) {
            errorMessage += "Enter a valid mobile number";
            popupError(errorMessage);
            return null;
        }

        if(!Validator_IDNumber.isValidIDNumber(id)) {
            errorMessage += "Enter a valid ID number";
            popupError(errorMessage);
            return null;
        }

        return new Patient(firstName, surname, mobileNumber, dob, gender, Validator_IDNumber.getValidIdNumber(id));
    }

    public JPanel getPanel() {
        return patientPanel;
    }
}
