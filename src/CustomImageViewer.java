import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomImageViewer {
    private final JLabel imageLabel = new JLabel();

    public CustomImageViewer(Image image, int imageWidth, int imageHeight) {
        Image scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        imageLabel.setIcon(icon);

        imageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JDialog dialog = new JDialog();
                Image scaledImage = image.getScaledInstance(1300, 850, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                JLabel imageLabel = new JLabel();
                imageLabel.setIcon(icon);
                dialog.add(imageLabel);
                dialog.setSize(new Dimension(1300, 850));
                dialog.setResizable(false);
                dialog.setVisible(true);
            }
        });
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }
}
