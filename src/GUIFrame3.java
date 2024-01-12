import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class GUIFrame3 extends GUIController {
    private final JPanel panel = new JPanel();
    private final JPanel bottomDetailsPanel = new JPanel(new GridBagLayout());
    private ConsultationTable consultationTable;
    private JScrollPane tableScrollPane;
    private Consultation selectedConsultation = null;
    private final JButton previewBtn = new JButton("Preview consultation data");
    private final JButton deleteBtn = new JButton("Delete selected consultation");

    public GUIFrame3() {
        panel.setPreferredSize(new Dimension(950, 710));
        bottomDetailsPanel.setPreferredSize(new Dimension(880, 180));
        bottomDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        deleteBtn.setPreferredSize(new Dimension(200, 26));
        previewBtn.setPreferredSize(new Dimension(200, 26));

        consultationTable = new ConsultationTable(westminsterSkinConsultationManager.getConsultationDataList());
        tableScrollPane = consultationTable.getScrollPane();

        consultationTable.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = consultationTable.getTable().rowAtPoint(e.getPoint());

                for(Consultation consultation : westminsterSkinConsultationManager.getConsultationDataList()) {
                    if(("" + consultation.getId()).equals(consultationTable.getTable().getModel().getValueAt(row ,0))) {
                        selectedConsultation = consultation;
                    }
                }

                if(selectedConsultation != null) {
                    bottomDetailsPanel.removeAll();
                    loadBottomSection();
                }
            }
        });

        previewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GUIFrame3_ConsultationPopup(selectedConsultation);
                } catch (NoSuchPaddingException | NoSuchAlgorithmException | IOException | InvalidKeyException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Do you want to delete the selected consultation?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    westminsterSkinConsultationManager.getConsultationDataList().remove(selectedConsultation);
                    westminsterSkinConsultationManager.saveConsultations();
                    panel.removeAll();
                    bottomDetailsPanel.removeAll();
                    consultationTable = new ConsultationTable(westminsterSkinConsultationManager.getConsultationDataList());
                    tableScrollPane = consultationTable.getScrollPane();
                    panel.add(tableScrollPane);
                    panel.add(bottomDetailsPanel);
                    refreshPanels(panel);
                }
            }
        });

        panel.add(tableScrollPane);
        panel.add(bottomDetailsPanel);
        refreshPanels(panel);
    }

    /**
     * Load bottom section of consultation preview.
     */
    private void loadBottomSection() {
            GridBagConstraints bottomPanelGBC = new GridBagConstraints();

            CustomLabelField patientNameLabel = new CustomLabelField("Patient Name:", selectedConsultation.getPatient().getFullName(), 400, 30, 0, 0, 0, 20);
            bottomDetailsPanel.add(patientNameLabel.getPanel(), bottomPanelGBC);

            CustomLabelField DoctorName = new CustomLabelField("Doctor name:", selectedConsultation.getDoctor().getFullName(), 400, 30, 0, 20, 0, 0);
            bottomPanelGBC.gridx = 1;
            bottomDetailsPanel.add(DoctorName.getPanel(), bottomPanelGBC);

            CustomLabelField DoctorSpecialization = new CustomLabelField("Doctor specialization:", selectedConsultation.getDoctor().getFullName(), 400, 30, 0, 0, 0, 20);
            bottomPanelGBC.gridx = 0;
            bottomPanelGBC.gridy = 1;
            bottomDetailsPanel.add(DoctorSpecialization.getPanel(), bottomPanelGBC);

            CustomLabelField costLabel = new CustomLabelField("Cost:", "£" + selectedConsultation.getCost(), 400, 30, 0, 20, 0, 0);
            bottomPanelGBC.gridx = 1;
            bottomDetailsPanel.add(costLabel.getPanel(), bottomPanelGBC);

            CustomLabelField dateLabel = new CustomLabelField("Date:", selectedConsultation.getDate().toString(), 400, 30, 0, 0, 40, 20);
            bottomPanelGBC.gridx = 0;
            bottomPanelGBC.gridy = 3;
            bottomDetailsPanel.add(dateLabel.getPanel(), bottomPanelGBC);

            CustomLabelField timeLabel = new CustomLabelField("Time:", selectedConsultation.getTimeDuration(), 400, 30, 0, 20, 40, 0);
            bottomPanelGBC.gridx = 1;
            bottomDetailsPanel.add(timeLabel.getPanel(), bottomPanelGBC);

            refreshPanels(bottomDetailsPanel);

            bottomPanelGBC.gridx = 0;
            bottomPanelGBC.gridy = 4;
            bottomDetailsPanel.add(previewBtn, bottomPanelGBC);

            bottomPanelGBC.gridx = 1;
            bottomDetailsPanel.add(deleteBtn, bottomPanelGBC);
    }

    private void refreshPanels(JPanel panel) {
        panel.revalidate();
        panel.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
}
