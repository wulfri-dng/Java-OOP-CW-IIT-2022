import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIFrame1 extends GUIController{
    private final JPanel panel = new JPanel();

    private void configurePanel() {
        panel.setPreferredSize(new Dimension(950, 710));
    }

    public GUIFrame1() {
        GridBagConstraints gbc = new GridBagConstraints();
        configurePanel();

        DoctorTable doctorTable = new DoctorTable(westminsterSkinConsultationManager.getDoctorList());
        JScrollPane tableScrollPane = doctorTable.getScrollPane();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(tableScrollPane, gbc);

        JButton alphabeticalBtn = new JButton("Sort Doctors Alphabetically");
        alphabeticalBtn.setPreferredSize(new Dimension(200, 26));
        alphabeticalBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doctorTable.sortTableDataAlphabetically();
            }
        });
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(alphabeticalBtn, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }
}
