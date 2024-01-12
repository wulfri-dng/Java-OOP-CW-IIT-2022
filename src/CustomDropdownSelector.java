import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomDropdownSelector {
    private final JPanel wrapperPanel = new JPanel(new GridLayout(2, 0));
    private final JComboBox<String> comboBox;

    public CustomDropdownSelector(int bTop, int bLeft, int bBottom, int bRight, int panelWidth, int panelHeight, String labelName, String[] options) {
        wrapperPanel.setBorder(new EmptyBorder(bTop, bLeft, bBottom, bRight));
        wrapperPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        wrapperPanel.add(new JLabel(labelName));
        comboBox = new JComboBox<>(options);
        wrapperPanel.add(comboBox);
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public JPanel getPanel() {
        return wrapperPanel;
    }
}
