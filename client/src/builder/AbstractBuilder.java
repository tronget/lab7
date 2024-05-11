package builder;

import utility.ConsoleWriter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public abstract class AbstractBuilder {
    private static final ConsoleWriter consoleWriter = ConsoleWriter.getInstance();

    public static String buildString(String message) {
        String string = null;
        while (string == null) {
            Scanner scanner = new Scanner(System.in);
            consoleWriter.printWithColon(message);
            string = scanner.nextLine().trim();
            if (string.isEmpty()) {
                string = null;
            }
        }
        return string;
    }

    public static int buildInt(String message, String exceptionMessage) {
        Integer integer = null;
        while (integer == null) {
            Scanner scanner = new Scanner(System.in);
            consoleWriter.printWithColon(message);
            if (!scanner.hasNextInt()) {
                consoleWriter.alert(exceptionMessage);
                continue;
            }
            integer = scanner.nextInt();
        }
        return integer;
    }

    public static int buildIntWithCondition(String message, String exceptionMessage, Predicate<Integer> condition) {
        int integer = buildInt(message, exceptionMessage);
        while (!condition.test(integer)) {
            consoleWriter.alert(exceptionMessage);
            integer = buildInt(message, exceptionMessage);
        }
        return integer;
    }

    public static double buildDouble(String message, String exceptionMessage) {
        Double dble = null;
        while (dble == null) {
            Scanner scanner = new Scanner(System.in);
            consoleWriter.printWithColon(message);
            if (!scanner.hasNextDouble()) {
                consoleWriter.alert(exceptionMessage);
                continue;
            }
            dble = scanner.nextDouble();
        }
        return dble;
    }

    public static LocalDateTime buildLocalDateTime(String message, String exceptionMessage) {
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            Scanner scanner = new Scanner(System.in);
            consoleWriter.printWithColon(message);
            String unparsedDate = scanner.nextLine().trim();
            if (unparsedDate.isEmpty() || unparsedDate.contains("//") ||
                    unparsedDate.charAt(0) == '/' ||
                    unparsedDate.charAt(unparsedDate.length() - 1) == '/'
                    || unparsedDate.length() - unparsedDate.replace("/", "").length() != 5) {
                consoleWriter.alert(exceptionMessage);
                continue;
            }
            List<Integer> parsedDateList;
            try {
                parsedDateList = Arrays.stream(unparsedDate.split("\\s*/\\s*")).map(Integer::parseInt).toList();
            } catch (NumberFormatException e) {
                consoleWriter.alert(exceptionMessage);
                continue;
            }

            try {
                int
                        year = parsedDateList.get(0),
                        month = parsedDateList.get(1),
                        day = parsedDateList.get(2),
                        hour = parsedDateList.get(3),
                        minute = parsedDateList.get(4),
                        second = parsedDateList.get(5);

                dateTime = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute, second));
            } catch (DateTimeException e) {
                consoleWriter.alert(exceptionMessage);
            }
        }
        return dateTime;
    }


    public static <T extends Enum<T>> T buildEnumValue(String message, String exceptionMessage, Class<T> enumClass) {
        T enumValue = null;
        while (enumValue == null) {
            String enumString = buildString(message).toUpperCase();
            try {
                enumValue = Enum.valueOf(enumClass, enumString);
            } catch (IllegalArgumentException e) {
                consoleWriter.alert(exceptionMessage);
            }
        }
        return enumValue;
    }

}
