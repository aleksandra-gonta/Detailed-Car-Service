/*This is class allowing retrieving data from users*/
package org.example.service.utils;

import org.example.exception.AppException;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.CarStatistics;
import org.example.model.enums.EngineType;
import org.example.model.enums.OrderBy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public final class UserDataService {

    private UserDataService() {}

    private final static Scanner scanner = new Scanner(System.in);

    public static String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
    /*Method retrieving int value*/
    public static int getInt(String message) {
        System.out.println(message);

        String valueAsString = scanner.nextLine();
        if (!valueAsString.matches("\\d+")) {
            throw new AppException("int value is not correct");
        }

        return Integer.parseInt(valueAsString);
    }
    /*Method retrieving double value*/
    public static double getDouble(String message) {
        System.out.println(message);

        String valueAsString = scanner.nextLine();
        if (!valueAsString.matches("\\d+\\.*\\d+")) {
            throw new AppException("double value is not correct");
        }

        return Double.parseDouble(valueAsString);
    }
    /*Method retrieving BigDecimal value*/
    public static BigDecimal getBigDecimal(String message) {
        System.out.println(message);

        String valueAsString = scanner.nextLine();
        if (!valueAsString.matches("\\d+\\.*\\d+")) {
            throw new AppException("Big Decimal value is not correct");
        }

        return new BigDecimal(valueAsString);
    }
    /*Method retrieving sorting value*/
    public static OrderBy getOrderBy() {

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(OrderBy.values())
                .forEach( orderBy -> System.out.println(counter.getAndIncrement() + ". " + orderBy) );
        int option = getInt("Choose order by option:");

        if (option < 1 || option > OrderBy.values().length) {
            throw new AppException("option number is not correct");
        }
        return OrderBy.values()[option - 1];
    }
    /*Method retrieving car body type*/
    public static CarBodyType getCarBodyType() {

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(CarBodyType.values())
                .forEach( carBodyType -> System.out.println(counter.getAndIncrement() + ". " + carBodyType) );
        int option = getInt("Choose car body type by option:");


        if (option < 1 || option > CarBodyType.values().length) {
            throw new AppException("option number is not correct");
        }
        return CarBodyType.values()[option - 1];
    }
    /*Method retrieving engine type*/
    public static EngineType getEngineType() {

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(EngineType.values())
                .forEach( engineType -> System.out.println(counter.getAndIncrement() + ". " + engineType) );
        int option = getInt("Choose engine type by option:");


        if (option < 1 || option > EngineType.values().length) {
            throw new AppException("option number is not correct");
        }
        return EngineType.values()[option - 1];
    }
    /*Method retrieving type of car statistics*/
    public static CarStatistics getCarStatistics() {

        AtomicInteger counter = new AtomicInteger(1);
        Arrays
                .stream(CarStatistics.values())
                .forEach( carStatistics -> System.out.println(counter.getAndIncrement() + ". " + carStatistics) );
        int option = getInt("Choose statistics type by option:");


        if (option < 1 || option > CarStatistics.values().length) {
            throw new AppException("option number is not correct");
        }
        return CarStatistics.values()[option - 1];
    }
    /*Method retrieving boolean value*/
    public static boolean getBoolean(String message) {
        System.out.println(message + " [y/n]");
        return scanner.nextLine().toLowerCase().equals("y");
    }
    /*Method closing scanner*/
    public static void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
