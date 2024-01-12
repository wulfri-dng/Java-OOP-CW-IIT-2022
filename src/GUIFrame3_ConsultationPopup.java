import javax.crypto.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class GUIFrame3_ConsultationPopup extends GUIController {
    public GUIFrame3_ConsultationPopup(Consultation consultation) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        JFrame consultationPreviewPopupFrame = new JFrame();
        consultationPreviewPopupFrame.setName("Consultation: " + consultation.getId());
        consultationPreviewPopupFrame.setSize(1000, 700);
        consultationPreviewPopupFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        consultationPreviewPopupFrame.setVisible(true);
        consultationPreviewPopupFrame.setResizable(false);

        JPanel consultationPopupPanel = new JPanel(new GridBagLayout());
        consultationPopupPanel.setPreferredSize(new Dimension(1000, 600));
        consultationPopupPanel.setLayout(new BoxLayout(consultationPopupPanel, BoxLayout.Y_AXIS));

        ConsultationPreview consultationPreview = new ConsultationPreview(consultation, 1000, 400);

        JPanel extraDetailsPanel = new JPanel(new GridBagLayout());
        extraDetailsPanel.setPreferredSize(new Dimension(880, 200));
        extraDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints extraGBC = new GridBagConstraints();

        // Extra Details Section -----------------------
        CustomHeadingLabel extraDetailsHeading = new CustomHeadingLabel("Extra details section", 420, 40, 0, 0, 5, 0);
        extraDetailsPanel.add(extraDetailsHeading.getHeading(), extraGBC);

        try {
            CustomLabelField extraNotes = new CustomLabelField("Extra Notes:", EncryptionHandler.decryptText(consultation.getExtraDetails()), 420, 40, 0, 0, 0, 0);
            extraGBC.gridy = 1;
            extraDetailsPanel.add(extraNotes.getPanel(), extraGBC);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        ArrayList<Image> decryptedImagesList = new ArrayList<>();
        for (String imageReference : consultation.getImageReferenceList()) {
            File imageFile = new File(Consultation.imagesFolderLocation + imageReference);
            decryptedImagesList.add(EncryptionHandler.decryptImage(imageFile));
        }
        CustomImageGallery customImageGallery = new CustomImageGallery();
        customImageGallery.setGalleryData(decryptedImagesList, 420, 120, 1, 3, 135, 120);
        customImageGallery.refreshGallery();

        extraGBC.gridx = 1;
        extraDetailsPanel.add(customImageGallery.getPanel(), extraGBC);

        consultationPopupPanel.add(consultationPreview.getConsultationPreviewPanel());
        consultationPopupPanel.add(extraDetailsPanel);
        consultationPreviewPopupFrame.add(consultationPopupPanel);
    }
}
