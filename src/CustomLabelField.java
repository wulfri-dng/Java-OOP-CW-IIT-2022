import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomLabelField {
    private final JPanel wrapperPanel = new JPanel(new GridLayout(0, 2));

    public CustomLabelField(String key, String value, int panelWidth, int panelHeight, int bTop, int bLeft, int bBottom, int bRight) {
        wrapperPanel.setBorder(new EmptyBorder(bTop, bLeft, bBottom, bRight));
        wrapperPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        wrapperPanel.add(new JLabel(key));
        wrapperPanel.add(new JLabel(value));
    }

    public JPanel getPanel() {
        return wrapperPanel;
    }
}
