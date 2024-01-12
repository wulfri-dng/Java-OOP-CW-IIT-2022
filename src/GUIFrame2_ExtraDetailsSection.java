import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GUIFrame2_ExtraDetailsSection extends GUIController {
    private final JPanel extraDetailsPanel = new JPanel();
    private final CustomImageGallery customImageGallery = new CustomImageGallery();
    private final ArrayList<Image> imageList = new ArrayList<>();
    private final ArrayList<String> imageReferenceList = new ArrayList<>();
    private final CustomTextAreaField extraInfoTextArea;

    public GUIFrame2_ExtraDetailsSection() {
        extraDetailsPanel.setLayout(new GridBagLayout());
        extraDetailsPanel.setPreferredSize(new Dimension(880, 200));
        extraDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JButton selectImageBtn = new JButton("Click to select images");
        selectImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMultipleImagesUploader();
                customImageGallery.setGalleryData(imageList, 390, 90, 1, 3, 125, 90);
                customImageGallery.refreshGallery();
            }
        });

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        CustomHeadingLabel customHeadingLabel = new CustomHeadingLabel("Extra info section", 840, 40, 0, 0, 5, 0);
        extraDetailsPanel.add(customHeadingLabel.getHeading(), gridBagConstraints);

        extraInfoTextArea = new CustomTextAreaField("Extra notes:", 450, 135, 0, 0, 0, 60);
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 2;
        extraDetailsPanel.add(extraInfoTextArea.getPanel(), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 1;
        extraDetailsPanel.add(customImageGallery.getPanel(), gridBagConstraints);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        extraDetailsPanel.add(selectImageBtn, gridBagConstraints);
    }

    private void openMultipleImagesUploader() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                try {
                    BufferedImage image = ImageIO.read(file);

                    if (imageList.size() < 3) {
                        String fileName = generateRandomImageName() + ".jpg";
                        File newFile = new File(Consultation.imagesFolderLocation + fileName);

                        imageReferenceList.add(fileName);
                        imageList.add(image);
                        EncryptionHandler.encryptImage(file, newFile);
                    } else {
                        JOptionPane.showMessageDialog(null, "You cannot upload more than 3 images.", "Notification", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private String generateRandomImageName() {
        String randomNumber;

        while (true) {
            randomNumber = Integer.toString(ThreadLocalRandom.current().nextInt(999, 99999));

            if (!Consultation.imageIdList.contains(randomNumber)) {
                Consultation.imageIdList.add(randomNumber);
                westminsterSkinConsultationManager.saveConfigData();
                break;
            }
        }

        return randomNumber;
    }

    public ArrayList<String> getImageReferenceList() {
        return imageReferenceList;
    }

    public String getExtraDetails() {
        return extraInfoTextArea.getTextAreaValue();
    }

    public ArrayList<Image> getTempImageList() {
        return imageList;
    }

    public JPanel getPanel() {
        return extraDetailsPanel;
    }
}
