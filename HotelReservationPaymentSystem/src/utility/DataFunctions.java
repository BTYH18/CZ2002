package utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gianpaolo & Sneha
 */
public class DataFunctions {

    /**
     * Calculate number of weekends (Saturday, Sunday) in between a start date and end date (format in "DD/MM/YYYY")
     * @param start - start date
     * @param end - end date
     * @param expression - date string format ("DD/MM/YYYY")
     * @return int numWeekends - number of weekend days
     */
    public static int weekendsBetween(String start, String end, String expression) {
        SimpleDateFormat format = new SimpleDateFormat(expression);
        int numWeekends = 0;

        Date startDate;
        Date endDate;

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        try {
            startDate = format.parse(start);
            endDate = format.parse(end);

            startCal.setTime(startDate);
            endCal.setTime(endDate);

            endCal.add(Calendar.DAY_OF_YEAR, 1);

            do {
                int day = startCal.get(Calendar.DAY_OF_WEEK);

                if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
                    numWeekends++;
                }

                startCal.add(Calendar.DAY_OF_YEAR, 1);
            } while (!startCal.equals(endCal));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return numWeekends;
    }

    /**
     * Calculate number of days in between a start date and end date (format in "DD/MM/YYYY")
     * @param start - start date
     * @param end - end date
     * @param expression - date string format ("DD/MM/YYYY")
     * @return int diffDays - number of (whole) days
     */
    public static int daysBetween(String start, String end, String expression) {
        SimpleDateFormat format = new SimpleDateFormat(expression);

        Date startDate;
        Date endDate;

        try {
            startDate = format.parse(start);
            endDate = format.parse(end);

            // calculate difference in milliseconds
            long diffMs = endDate.getTime() - startDate.getTime();

            // convert to days
            long diffDays = diffMs / (24 * 60 * 60 * 1000);

            return (int) diffDays;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean stringContainsItems(String inputString, String[] items) {
        for(int i =0; i < items.length; i++) {
            if(inputString.equals(items[i])) {
                return true;
            }
        }
        return false;
    }

    public static void printAllFromArray(ArrayList<String> strings) {
        int arrayList = strings.size() - 1;
        int i = 0;

        //ForEach String in strings
        for (String s: strings) {
            System.out.print(" " + s);
            if(i++<arrayList) {
                System.out.print(",");
            }
        }
    }
}
