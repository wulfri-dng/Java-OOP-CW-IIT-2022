public class Validator_MobileNumber {
    public static boolean isValidNumber(String mobileNumber) {
        String mobileNumberVersion = "";

        if (mobileNumber.charAt(0) == '0' && mobileNumber.length() == 10) { // 0771234531
            mobileNumberVersion = "version1";
        } else if (mobileNumber.charAt(0) != '0' && mobileNumber.length() == 9) { // 771234531
            mobileNumberVersion = "version1";
        } else if (mobileNumber.startsWith("+94") && mobileNumber.length() == 12) { // +94771234531
            mobileNumberVersion = "version2";
        }

        switch (mobileNumberVersion) {
            case "version1" -> {
                return validateVersion1(mobileNumber);
            }
            case "version2" -> {
                return validateVersion2(mobileNumber);
            }
            default -> {
                return false;
            }
        }
    }

    private static boolean validateVersion1(String mobileNumber) {
        try {
            Integer.parseInt(mobileNumber);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateVersion2(String mobileNumber) {
        try {
            Long.parseLong(mobileNumber.substring(1, 12));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
