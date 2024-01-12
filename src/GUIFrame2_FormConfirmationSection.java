import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class GUIFrame2_FormConfirmationSection extends GUIController {
    private final JPanel formConfirmationPanel = new JPanel(new GridBagLayout());

    public GUIFrame2_FormConfirmationSection(Consultation consultation) {
        formConfirmationPanel.setPreferredSize(new Dimension(880, 650));
        formConfirmationPanel.setLayout(new BoxLayout(formConfirmationPanel, BoxLayout.Y_AXIS));

        ConsultationPreview consultationPreview = new ConsultationPreview(consultation, 880, 450);
        JPanel extraDetails = new JPanel(new GridBagLayout());
        extraDetails.setPreferredSize(new Dimension(880, 200));
        extraDetails.setBorder(BorderFactory.createLineBorder(Color.black));
        GridBagConstraints extraGBC = new GridBagConstraints();

        // Extra Details Section -----------------------
        CustomHeadingLabel extraDetailsHeading = new CustomHeadingLabel("Extra details section", 420, 40, 0, 0, 5, 0);
        extraDetails.add(extraDetailsHeading.getHeading(), extraGBC);

        try {
            CustomLabelField extraNotes = new CustomLabelField("Extra Notes:", EncryptionHandler.decryptText(consultation.getExtraDetails()), 420, 40, 0, 0, 0, 0);
            extraGBC.gridy = 1;
            extraDetails.add(extraNotes.getPanel(), extraGBC);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        CustomImageGallery customImageGallery = new CustomImageGallery();
        customImageGallery.setGalleryData(consultation.getTempImageList(), 420, 120,1, 3, 135, 120);
        customImageGallery.refreshGallery();
        extraGBC.gridy = 1;
        extraDetails.add(customImageGallery.getPanel(), extraGBC);

        formConfirmationPanel.add(consultationPreview.getConsultationPreviewPanel());

        extraGBC.gridy = 2;
        formConfirmationPanel.add(extraDetails);
    }

    public JPanel getPanel() {
        return formConfirmationPanel;
    }
}
