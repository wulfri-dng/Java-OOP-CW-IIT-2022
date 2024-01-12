import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class DoctorTable {
    private final ArrayList<Doctor> doctorList;
    private final String[] columnNames = {"Name", "Surname", "Specialisation", "Gender", "Mobile number", "Medical License No", "Date of Birth"};
    private final DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    private final JTable table = new JTable(model);
    private final JScrollPane scrollPane = new JScrollPane(table);

    public DoctorTable(ArrayList<Doctor> doctorList) {
        this.doctorList = doctorList;

        for (Doctor doctor : doctorList) {
            String[] rowData = new String[] {doctor.getName(), doctor.getSurname(), doctor.getSpecialisation(), doctor.getGender(), doctor.getMobileNumber(), doctor.getMedicalLicenseNo(),  Validator_Date.previewDateInReadableFormat(doctor.getDob().getTime())};
            model.addRow(rowData);
        }

        scrollPane.setPreferredSize(new Dimension(880, 400));
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void sortTableDataAlphabetically() {
        doctorList.sort(Comparator.comparing(Doctor::getName));

        for (int i = 0; i < doctorList.size(); i++) {
            Doctor doctor = doctorList.get(i);
            model.setValueAt(doctor.getName(), i, 0);
            model.setValueAt(doctor.getSurname(), i, 1);
            model.setValueAt(doctor.getSpecialisation(), i, 2);
            model.setValueAt(doctor.getGender(), i, 3);
            model.setValueAt(doctor.getMobileNumber(), i, 4);
            model.setValueAt(doctor.getMedicalLicenseNo(), i, 5);
            model.setValueAt(Validator_Date.previewDateInReadableFormat(doctor.getDob().getTime()), i, 6);
        }
    }
}
