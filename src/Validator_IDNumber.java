public class Validator_IDNumber {
    public static boolean isValidIDNumber(String idNumber) {
        return idNumber.matches("\\d{12}|\\d{9}[Vv]");
    }

    public static String getValidIdNumber(String idNumber) {
        if (isValidIDNumber(idNumber)) {
            if (idNumber.matches("\\d{9}[V]")) {
                return idNumber.substring(0, 9) + "v";
            } else {
                return idNumber;
            }
        } else {
            return null;
        }
    }
}
