import javax.crypto.KeyGenerator;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    /**
     * This is the main method and the entry point of the application.
     * @throws NoSuchAlgorithmException To handle the exception when entered encryption method invalid.
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        clearTempFolder();
        WestminsterSkinConsultationManager westminsterSkinConsultationManager = new WestminsterSkinConsultationManager();
        EncryptionHandler.encryptionPrivateKey = KeyGenerator.getInstance("AES").generateKey();
        westminsterSkinConsultationManager.readConfigData();
        westminsterSkinConsultationManager.readDoctorData();
        westminsterSkinConsultationManager.readConsultationData();
        westminsterSkinConsultationManager.readPatientsData();
        GUIController GUIController = new GUIController();

        boolean isConsoleRunning = false;
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("             Welcome to Westminster consultation management system              ");
        System.out.println("--------------------------------------------------------------------------------");
        while (true) {
            System.out.print("Enter an option listed below\n1: Run admin console\n2: Open user interface\nSelect: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                System.out.println("----------------------------------------");
                System.out.println("             Admin console              ");
                System.out.println("----------------------------------------");
                isConsoleRunning = true;
                break;
            } else if (input.equals("2")) {
                GUIController.startGUI(westminsterSkinConsultationManager);
                break;
            }
        }

        while (isConsoleRunning) {
            try {
                printMenu();
                int userInput = Integer.parseInt(scanner.nextLine());

                switch (userInput) {
                    case 1 -> westminsterSkinConsultationManager.addNewDoctor();
                    case 2 -> westminsterSkinConsultationManager.deleteDoctor();
                    case 3 -> westminsterSkinConsultationManager.printDoctors();
                    case 4 -> westminsterSkinConsultationManager.saveDoctors();
                    case 5 -> GUIController.startGUI(westminsterSkinConsultationManager);
                    case 9 -> isConsoleRunning = false;
                    default -> System.out.println("Please enter a number between 1 - 4");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number between 1 - 4");
            }
        }
    }

    /**
     * Print console menu.
     */
    public static void printMenu() {
        System.out.println("1 - Add a new doctor");
        System.out.println("2 - Delete a doctor");
        System.out.println("3 - Print all doctors");
        System.out.println("4 - Save doctors");
        System.out.println("5 - Open GUI");
        System.out.println("9 - Exit");
        System.out.print("Select: ");
    }

    /**
     * Clear temporary stored decrypted images to prevent data leaks.
     */
    private static void clearTempFolder() {
        File folder = new File(Consultation.imagesDecryptLocation);
        File[] files = folder.listFiles();

        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
