import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomTextAreaField {
    private final JPanel wrapperPanel = new JPanel(new GridBagLayout());
    private final JTextArea extraInfoTextArea = new JTextArea();

    public CustomTextAreaField(String labelName, int panelWidth, int panelHeight, int bTop, int bLeft, int bBottom, int bRight) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        wrapperPanel.setBorder(new EmptyBorder(bTop, bLeft, bBottom, bRight));
        wrapperPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        JLabel title = new JLabel(labelName);
        title.setPreferredSize(new Dimension(panelWidth - bRight - bTop, 35));
        wrapperPanel.add(title, gridBagConstraints);

        extraInfoTextArea.setPreferredSize(new Dimension(panelWidth - bRight - bTop, 95));
        gridBagConstraints.gridy = 1;
        wrapperPanel.add(extraInfoTextArea, gridBagConstraints);
    }

    public JPanel getPanel() {
        return wrapperPanel;
    }

    public String getTextAreaValue() {
        return extraInfoTextArea.getText();
    }
}
