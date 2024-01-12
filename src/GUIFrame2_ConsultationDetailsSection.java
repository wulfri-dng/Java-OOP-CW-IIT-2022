import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIFrame2_ConsultationDetailsSection extends GUIController {
    private final JPanel consultationDetailsPanel = new JPanel();
    private JComboBox<String> doctorSpecialisationsComboBox = null;
    private JComboBox<String> doctorNamesComboBox;
    private JComboBox<String> fromTimeComboBox = null;
    private JComboBox toTimeComboBox = null;
    private final CustomDateSelector dobField;

    public GUIFrame2_ConsultationDetailsSection() {
        consultationDetailsPanel.setPreferredSize(new Dimension(880, 200));
        consultationDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        consultationDetailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints doctorInquiryPanelGBC = new GridBagConstraints();

        CustomHeadingLabel customHeadingLabel = new CustomHeadingLabel("Consultation details section", 420, 40, 0, 0, 5, 0);
        consultationDetailsPanel.add(customHeadingLabel.getHeading(), doctorInquiryPanelGBC);

        doctorInquiryPanelGBC.gridy = 1;
        doctorInquiryPanelGBC.gridwidth = 2;
        consultationDetailsPanel.add(renderDoctorDetailsDropdown(), doctorInquiryPanelGBC);

        dobField = new CustomDateSelector(10, 0, 0, 30, 420, 60, "Date:");
        doctorInquiryPanelGBC.gridx = 0;
        doctorInquiryPanelGBC.gridy = 2;
        doctorInquiryPanelGBC.gridwidth = 1;
        consultationDetailsPanel.add(dobField.getPanel(), doctorInquiryPanelGBC);

        doctorInquiryPanelGBC.gridx = 1;
        doctorInquiryPanelGBC.gridy = 2;
        consultationDetailsPanel.add(renderTimeSlotDropdown(), doctorInquiryPanelGBC);
    }

    private JPanel renderDoctorDetailsDropdown() {
        GridLayout layout = new GridLayout(2, 2, 60, 0);
        JPanel wrapperPanel = new JPanel(layout);

        wrapperPanel.setPreferredSize(new Dimension(840, 60));
        wrapperPanel.add(new JLabel("Doctor Name:"));
        wrapperPanel.add(new JLabel("Specialisation"));

        String[] doctorNames = westminsterSkinConsultationManager.getDoctorNameList().toArray(new String[0]);
        doctorNamesComboBox = new JComboBox<>(doctorNames);

        DefaultComboBoxModel<String> specialisationsListModel = new DefaultComboBoxModel<String>(westminsterSkinConsultationManager.getDoctorSpecialisationList().toArray(new String[0]));
        doctorSpecialisationsComboBox = new JComboBox<>(specialisationsListModel);

        doctorNamesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor selectedDoctor = westminsterSkinConsultationManager.getDoctorFromFullName((String) doctorNamesComboBox.getSelectedItem());
                specialisationsListModel.setSelectedItem(selectedDoctor.getSpecialisation());
            }
        });

        doctorSpecialisationsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] updatedDoctorNames = westminsterSkinConsultationManager.getDoctorNameList((String) doctorSpecialisationsComboBox.getSelectedItem()).toArray(new String[0]);
                DefaultComboBoxModel<String> doctorListModel = new DefaultComboBoxModel<>(updatedDoctorNames);
                doctorNamesComboBox.setModel(doctorListModel);
            }
        });

        wrapperPanel.add(doctorNamesComboBox);
        wrapperPanel.add(doctorSpecialisationsComboBox);

        return wrapperPanel;
    }

    private JPanel renderTimeSlotDropdown() {
        GridLayout layout = new GridLayout(2, 2, 30, 0);
        JPanel wrapperPanel = new JPanel(layout);
        wrapperPanel.setPreferredSize(new Dimension(420, 60));
        wrapperPanel.setBorder(new EmptyBorder(0, 30, 0, 0));
        wrapperPanel.add(new JLabel("From:"));
        wrapperPanel.add(new JLabel("To"));

        fromTimeComboBox = new JComboBox<>(Arrays.copyOfRange(Consultation.timeSlots, 0, Consultation.timeSlots.length - 1));
        toTimeComboBox = new JComboBox<>(Arrays.copyOfRange(Consultation.timeSlots, 1, Consultation.timeSlots.length));

        fromTimeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedTimeSlotIndex = Arrays.asList(Consultation.timeSlots).indexOf((String) fromTimeComboBox.getSelectedItem());
                String[] updatedTimeSlots = Arrays.copyOfRange(Consultation.timeSlots, selectedTimeSlotIndex + 1, Consultation.timeSlots.length);
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(updatedTimeSlots);
                toTimeComboBox.setModel(model);
            }
        });

        wrapperPanel.add(fromTimeComboBox);
        wrapperPanel.add(toTimeComboBox);

        return wrapperPanel;
    }

    public String getSelectedDoctor() {
        return (String) doctorNamesComboBox.getSelectedItem();
    }

    public Date getDate() throws ParseException {
        return getDateWithoutTimeUsingFormat(dobField.getSelectedDate().getTime());
    }

    public Date getDateWithoutTimeUsingFormat(Date date)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        return formatter.parse(formatter.format(date));
    }

    public String getFromTime() {
        return (String) fromTimeComboBox.getSelectedItem();
    }

    public String getToTome() {
        return (String) toTimeComboBox.getSelectedItem();
    }

    public JPanel getPanel() {
        return consultationDetailsPanel;
    }
}
