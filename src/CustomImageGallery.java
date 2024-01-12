import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CustomImageGallery {
    private final JPanel panel = new JPanel();
    private ArrayList<Image> imageList;
    private int rowCount = 1;
    private int columnCount = 3;
    private int panelWidth = 390;
    private int panelHeight = 90;
    private int imageWidth = 125;
    private int imageHeight = 140;
    private int imageCount = 0;

    public CustomImageGallery() {
        panel.setPreferredSize(new Dimension(390, 90));
        panel.setLayout(new GridLayout(rowCount, columnCount, 5, 0));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    /**
     * Use to refresh the image gallery when the image list update.
     */
    public void refreshGallery() {
        if(imageList != null) {
            for(Image image : imageList) {
                if(imageCount < rowCount*columnCount) {
                    panel.add(new CustomImageViewer(image, imageWidth, imageHeight).getImageLabel());
                    imageCount++;
                }
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    /**
     * Set image data and set other styles.
     */
    public void setGalleryData(ArrayList<Image> imageList, int panelWidth, int panelHeight, int rowCount, int columnCount, int imageWidth, int imageHeight) {
        this.imageList = imageList;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public JPanel getPanel() {
        return panel;
    }
}
