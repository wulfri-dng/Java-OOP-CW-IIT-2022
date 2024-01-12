import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController extends JFrame {
    static WestminsterSkinConsultationManager westminsterSkinConsultationManager;
    private GUIMainPanel guiMainPanel;
    private GUIFrame3 guiFrame3;

    /**
     * Configuration of the main frame. Every section lies on this frame.
     */
    private void configureFrame() {
        setName("Westminster Skin Consultation Management System");
        setSize(1200, 750);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Reusable method to refresh given panel preview.
     * @param panel Given panel that need to be refreshed.
     */
    private void updateFrame(JPanel panel) {
        remove(guiMainPanel.getMainPanel());
        guiMainPanel.setMainPanel(panel);
        add(guiMainPanel.getMainPanel());
        revalidate();
        repaint();
    }

    /**
     * This calls from the console when the user decide to run GUI. This is the entry point of GUI.
     * @param westminsterSkinConsultationManager Current object of westminsterSkinConsultationManager. Every detail that need to preview on GUI stored in here.
     */
    public void startGUI(WestminsterSkinConsultationManager westminsterSkinConsultationManager) {
        guiMainPanel = new GUIMainPanel();
        GUIController.westminsterSkinConsultationManager = westminsterSkinConsultationManager;
        configureFrame();

        GUIFrame1 guiFrame1 = new GUIFrame1();
        GUIFrame2 guiFrame2 = new GUIFrame2();

        JPanel sideBarPanel = new JPanel(new GridBagLayout());
        sideBarPanel.setPreferredSize(new Dimension(170, 700));
        sideBarPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        sideBarPanel.setLayout(new BorderLayout());

        JPanel sideBarButtonWrapperPanel = new JPanel(new BorderLayout());
        sideBarButtonWrapperPanel.setPreferredSize(new Dimension(170, 123));

        JButton doctorsBtn = new JButton("Current Doctors");
        doctorsBtn.setPreferredSize(new Dimension(170, 40));

        JButton viewConsultationsBtn = new JButton("View all consultations");
        viewConsultationsBtn.setPreferredSize(new Dimension(170, 40));

        JButton addAConsultationBtn = new JButton("Add a consultation");
        addAConsultationBtn.setPreferredSize(new Dimension(170, 40));

        sideBarButtonWrapperPanel.add(doctorsBtn, BorderLayout.PAGE_START);
        sideBarButtonWrapperPanel.add(viewConsultationsBtn, BorderLayout.CENTER);
        sideBarButtonWrapperPanel.add(addAConsultationBtn, BorderLayout.PAGE_END);

        doctorsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFrame(guiFrame1.getPanel());
            }
        });

        addAConsultationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFrame(guiFrame2.getPanel());
            }
        });

        viewConsultationsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guiFrame3 = new GUIFrame3();
                updateFrame(guiFrame3.getPanel());
            }
        });

        sideBarPanel.add(sideBarButtonWrapperPanel, BorderLayout.PAGE_START);
        add(sideBarPanel);
        add(guiMainPanel.getMainPanel());
        updateFrame(guiFrame1.getPanel());
    }
}