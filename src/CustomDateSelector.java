import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomDateSelector {
    private final JPanel wrapperPanel = new JPanel(new GridLayout(2, 2));
    private final Calendar calendar = Calendar.getInstance();
    private final JDateChooser dateSelector = new JDateChooser(calendar.getTime());

    public CustomDateSelector(int bTop, int bLeft, int bBottom, int bRight, int panelWidth, int panelHeight, String labelName) {
        JLabel dateLabel = new JLabel();

        dateSelector.addPropertyChangeListener("date", e -> {
            Calendar selectedDate = dateSelector.getCalendar();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateLabel.setText(dateFormat.format(selectedDate.getTime()));
        });

        wrapperPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        wrapperPanel.setBorder(new EmptyBorder(bTop, bLeft, bBottom, bRight));
        wrapperPanel.add(new JLabel(labelName));
        wrapperPanel.add(dateSelector);
    }

    public Calendar getSelectedDate() {
        return dateSelector.getCalendar();
    }

    public JPanel getPanel() {
        return wrapperPanel;
    }
}
