import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Validator_Date {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String previewDateInReadableFormat(Date date) {
        String formattedDate = "";

        try {
            formattedDate = dateFormat.format(date);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return formattedDate;
    }

    public static Calendar getValidatedDate(String input) {
        Calendar calender = Calendar.getInstance();

        try {
            calender.setTime(dateFormat.parse(input));
        } catch (ParseException ignored) {
            return null;
        }

        return calender;
    }
}
