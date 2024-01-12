import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ConsultationTable {
    private final String[] columnNames = {"ID", "Patient Name", "Patient Mobile No", "Patient Gender", "Doctor Name", "Specialisation", "Date", "Time Duration"};
    private final DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    private final JTable table = new JTable(model);
    private final JScrollPane scrollPane = new JScrollPane(table);

    public ConsultationTable(ArrayList<Consultation> consultationList) {
        for (Consultation consultation : consultationList) {
            Patient patient = consultation.getPatient();
            Doctor doctor = consultation.getDoctor();

            String[] rowData = new String[] {"" + consultation.getId(), patient.getFullName(), patient.getMobileNumber(), patient.getGender(), doctor.getFullName(), doctor.getSpecialisation(), Validator_Date.previewDateInReadableFormat(consultation.getDate()), consultation.getTimeDuration()};
            model.addRow(rowData);
        }

        scrollPane.setPreferredSize(new Dimension(880, 490));
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
