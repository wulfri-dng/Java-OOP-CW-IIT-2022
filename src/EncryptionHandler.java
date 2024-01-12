import javax.crypto.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionHandler {
    static Key encryptionPrivateKey;

    /**
     * Encrypt given text using AES algorithm and return encrypted text.
     * @param text Text that need to be encrypted.
     * @return Encrypted text.
     * @throws NoSuchPaddingException Handle exceptions.
     * @throws NoSuchAlgorithmException Handle exceptions.
     * @throws InvalidKeyException Handle exceptions.
     * @throws IllegalBlockSizeException Handle exceptions.
     * @throws BadPaddingException Handle exceptions.
     */
    public static String encryptText(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, encryptionPrivateKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypt given text using AES algorithm and return human-readable text.
     * @param encryptedText Encrypted text that need to be decrypted.
     * @return Decrypted text.
     * @throws NoSuchPaddingException Handle exceptions.
     * @throws NoSuchAlgorithmException Handle exceptions.
     * @throws InvalidKeyException Handle exceptions.
     * @throws IllegalBlockSizeException Handle exceptions.
     * @throws BadPaddingException Handle exceptions.
     */
    public static String decryptText(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, encryptionPrivateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    /**
     * Encrypt given image using AES algorithm and store in the given file location.
     * @param file File that needs to be encrypted.
     * @param newFile Used to get the location and save the file where it needs to be stored.
     * @throws NoSuchPaddingException Handle exceptions.
     * @throws NoSuchAlgorithmException Handle exceptions.
     * @throws InvalidKeyException Handle exceptions.
     * @throws IOException Handle exceptions.
     */
    public static void encryptImage(File file, File newFile) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, encryptionPrivateKey);
        CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(file), cipher);
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);

        int i;
        while ((i = cipherInputStream.read()) != -1) {
            fileOutputStream.write(i);
        }
    }

    /**
     * Decrypt given image using AES algorithm and store in specific location.
     * @param file File that needs to be decrypted.
     * @throws NoSuchPaddingException Handle exceptions.
     * @throws NoSuchAlgorithmException Handle exceptions.
     * @throws InvalidKeyException Handle exceptions.
     * @throws IOException Handle exceptions.
     */
    public static Image decryptImage(File file) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, encryptionPrivateKey);

        CipherInputStream cipherInputStream = new CipherInputStream(new FileInputStream(file), cipher);
        FileOutputStream fileOutputStream = new FileOutputStream(Consultation.imagesDecryptLocation + file.getName());

        int i;
        while ((i = cipherInputStream.read()) != -1) {
            fileOutputStream.write(i);
        }

        File inputFile = new File(Consultation.imagesDecryptLocation + file.getName());
        return ImageIO.read(inputFile);
    }
}
