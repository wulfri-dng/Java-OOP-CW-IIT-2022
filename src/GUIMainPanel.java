import javax.swing.*;
import java.awt.*;

public class GUIMainPanel extends GUIController {
    private JPanel mainPanel = new JPanel(new BorderLayout());

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}