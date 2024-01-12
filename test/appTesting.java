import org.junit.*;
import org.junit.runners.MethodSorters;
import java.io.*;
import java.util.Calendar;
import java.util.Scanner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class appTesting {
    private WestminsterSkinConsultationManager westminsterSkinConsultationManager;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStream);;
    private Scanner scannerBackup;

    @Before
    public void setup() {
        westminsterSkinConsultationManager = new WestminsterSkinConsultationManager();
        scannerBackup = westminsterSkinConsultationManager.getScanner();
        System.setOut(printStream);
    }

    @After
    public void restoreScanner() {
        westminsterSkinConsultationManager.setScanner(scannerBackup);
    }

    @Test
    public void test01_SavedDoctorDataLoadChecker() {
        Assert.assertFalse(westminsterSkinConsultationManager.getDoctorList().size() > 0);
        westminsterSkinConsultationManager.readDoctorData();
        Assert.assertTrue(westminsterSkinConsultationManager.getDoctorList().size() > 0);
    }

    @Test
    public void test02_ValidGenderChecker() {
        String input = "f";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        String actual = westminsterSkinConsultationManager.inputGenderValue();
        Assert.assertEquals(actual, "Female");
    }

    @Test
    public void test03_InvalidGenderChecker() {
        String input = "fle\nmale";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        westminsterSkinConsultationManager.inputGenderValue();
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String firstLine = lines[0].trim();
        Assert.assertEquals(firstLine, "Gender: Invalid input! Please enter a valid gender. (Format: M/F or Male/Female)");
    }

    @Test
    public void test04_ValidDateChecker() {
        String input = "17/11/2000";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        Calendar expect = westminsterSkinConsultationManager.inputDateValue("Date checker");
        Calendar date = Validator_Date.getValidatedDate(input);
        Assert.assertEquals(expect, date);
    }

    @Test
    public void test05_InvalidDateChecker() {
        String input = "17112000\n17/11/2000";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        westminsterSkinConsultationManager.inputDateValue("Date checker");
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String firstLine = lines[0].trim();
        Assert.assertEquals(firstLine, "Date checker: Invalid input! Please enter a valid date. (Format: dd/MM/yyyy)");
    }

    @Test
    public void test06_ValidMobileNumberChecker() {
        String input = "0764060551";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        String expect = westminsterSkinConsultationManager.inputMobileNumberValue("Mobile number");
        Assert.assertEquals(expect, input);
    }

    @Test
    public void test07_InvalidMobileNumberChecker() {
        String input = "1231aaa\n0764060551";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        westminsterSkinConsultationManager.inputMobileNumberValue("Mobile number");
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String firstLine = lines[0].trim();
        Assert.assertEquals(firstLine, "Mobile number: Invalid input! Please enter a valid mobile number.");
    }

    @Test
    public void test08_ValidMedicalLicenseNumberCheckerWithoutDuplicationChecker() {
        String input = "L12321423";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        String actual = westminsterSkinConsultationManager.inputMedicalLicenseNumber(false);
        Assert.assertEquals(actual, input);
    }

    @Test
    public void test09_InvalidMedicalLicenseNumberCheckerWithoutDuplicationChecker() {
        String input = "testString\nP21212432";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        westminsterSkinConsultationManager.setScanner(new Scanner(in));
        westminsterSkinConsultationManager.inputMedicalLicenseNumber(false);
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String firstLine = lines[0].trim();
        Assert.assertEquals(firstLine, "Medical license no: Invalid input! Please enter a valid medical license number with correct format.");
    }

    @Test
    public void test10_AddNewDoctorChecker() {
        String input = "Samantha\nPerera\nMale\n17/11/2000\n0764060551\nL32123421\nCosmetic Dermatology";
        byte[] inputBytes = input.getBytes();
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        westminsterSkinConsultationManager.setScanner(new Scanner(inputStream));
        int previousDoctorListSize = westminsterSkinConsultationManager.getDoctorList().size();
        westminsterSkinConsultationManager.addNewDoctor();
        int currentDoctorListSize = westminsterSkinConsultationManager.getDoctorList().size();
        Assert.assertEquals(currentDoctorListSize, previousDoctorListSize + 1);
    }

    @Test
    public void test11_ValidMedicalLicenseNumberCheckerWithDuplicationChecker() {
        String input = "L32123421";
        byte[] inputBytes = input.getBytes();
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        westminsterSkinConsultationManager.setScanner(new Scanner(inputStream));
        String actual = westminsterSkinConsultationManager.inputMedicalLicenseNumber(true);
        Assert.assertNull(actual);
    }

    @Test
    public void test12_AddAlreadyExistingDoctorChecker() {
        String input = "Samantha\nPerera\nMale\n17/11/2000\n0764060551\nL32123421";
        byte[] inputBytes = input.getBytes();
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        westminsterSkinConsultationManager.setScanner(new Scanner(inputStream));
        westminsterSkinConsultationManager.addNewDoctor();
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String lastLine = lines[lines.length - 1].trim();
        Assert.assertEquals(lastLine, "Moving to main menu...");
    }

    @Test
    public void test13_ValidDoctorDeleteChecker() {
        String input = "L32123421";
        byte[] inputBytes = input.getBytes();
        InputStream inputStream = new ByteArrayInputStream(inputBytes);
        westminsterSkinConsultationManager.setScanner(new Scanner(inputStream));
        int previousDoctorListSize = westminsterSkinConsultationManager.getDoctorList().size();
        westminsterSkinConsultationManager.deleteDoctor();
        int currentDoctorListSize = westminsterSkinConsultationManager.getDoctorList().size();
        Assert.assertEquals(currentDoctorListSize, previousDoctorListSize - 1);
    }

    @Test
    public void test14_SaveDoctorDetailsChecker() {
        westminsterSkinConsultationManager.saveDoctors();
        String output = outputStream.toString();
        String[] lines = output.split("\n");
        String firstLine = lines[0].trim();
        Assert.assertEquals(firstLine, "Doctor details successfully saved!");
    }
}
