import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomTextField {
    private final JPanel wrapperPanel = new JPanel(new GridLayout(2, 0));
    private final JTextField nameField = new JTextField();

    public CustomTextField(int bTop, int bLeft, int bBottom, int bRight, int panelWidth, int panelHeight, String labelName) {
        wrapperPanel.setBorder(new EmptyBorder(bTop, bLeft, bBottom, bRight));
        wrapperPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        wrapperPanel.add(new JLabel(labelName));
        wrapperPanel.add(nameField);
    }

    public JPanel getPanel() {
        return wrapperPanel;
    }

    public String getTextFieldValue() {
        return nameField.getText();
    }
}
