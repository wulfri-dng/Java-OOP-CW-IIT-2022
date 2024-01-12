import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomHeadingLabel {
    private final JLabel patientSectionHeading = new JLabel();

    public CustomHeadingLabel(String title, int width, int height, int bTop, int bLeft, int bBottom, int bRight) {
        patientSectionHeading.setText(title);
        patientSectionHeading.setPreferredSize(new Dimension(width, height));
        patientSectionHeading.setBorder(new EmptyBorder(bTop, bLeft, bBottom, bRight));
        patientSectionHeading.setFont(new Font(patientSectionHeading.getFont().getName(), Font.PLAIN, 18));
    }

    public JLabel getHeading() {
        return patientSectionHeading;
    }
}
