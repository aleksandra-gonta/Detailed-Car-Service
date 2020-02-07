    /*This class provides console menu for car service*/
package org.example.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.MapUtils;
import org.example.exception.AppException;
import org.example.model.Car;
import org.example.model.data.Data;
import org.example.model.enums.*;
import org.example.service.CarsService;
import org.example.service.utils.UserDataService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MenuService {
    private final CarsService carsService;
    /*Method providing the chosen option*/
    public void mainMenu() {
        while (true) {
            try {
                int option = chooseOption();
                switch (option) {
                    case 1 -> option1();
                    case 2 -> option2();
                    case 3 -> option3();
                    case 4 -> option4();
                    case 5 -> option5();
                    case 6 -> option6();
                    case 7 -> option7();
                    case 8 -> option8();
                    case 9 -> {
                        UserDataService.close();
                        System.out.println("have a nice day");
                        return;
                    }
                    default -> System.out.println("No option with number " + option);
                }
            } catch (AppException e) {
                System.out.println("----------------------- !!! EXCEPTION !!! --------------------");
                System.out.println(e.getMessage());
                System.out.println("--------------------------------------------------------------");
            }
        }
    }

    private int chooseOption() {

        System.out.println("1 - show all cars");
        System.out.println("2 - sort cars");
        System.out.println("3 - cars body type in the price range");
        System.out.println("4 - cars with the specific type of the engine");
        System.out.println("5 - calculate statistics");
        System.out.println("6 - cars and their mileage");
        System.out.println("7 - cars according to tyre types");
        System.out.println("8 - cars containing specific components");
        System.out.println("9 - end of app");

        return UserDataService.getInt("Choose option:");
    }
    /*Methods option1-option8 used in the menu, based on all the methods from CarService Class*/
    private void option1() {
        System.out.println(carsService.toString());
    }

    private void option2() {
        OrderBy orderBy = UserDataService.getOrderBy();
        boolean descending = UserDataService.getBoolean("Descending sort?");
        List<Car> sortedCars = carsService.sortBy(orderBy, descending);
        System.out.println(toJson(sortedCars));
    }
    private void option3() {
        BigDecimal min = UserDataService.getBigDecimal("Enter minimum price:");
        BigDecimal max = UserDataService.getBigDecimal("Enter maximum price:");
        CarBodyType bodyType = UserDataService.getCarBodyType();
        List<Car> carsWithinThePriceRange = carsService.findCarsBodyTypeInThePriceRange(bodyType,min,max);
        System.out.println(toJson(carsWithinThePriceRange));
    }
    private void option4() {
        EngineType engineType = UserDataService.getEngineType();
        List<Car> carsWithEngineType = carsService.findCarsWithEngineType(engineType);
        System.out.println(toJson(carsWithEngineType));
    }
    private void option5() {
        CarStatistics statistics = UserDataService.getCarStatistics();
        Data data = carsService.calculateCarsStatistics(statistics);
        System.out.println(data);
    }
    private void option6() {
        Map<Car, Double> carsMileage = carsService.showCarsAndTheirMileage();
        convertMap(carsMileage);
    }
    private void option7() {
        Map<TyreType, List<Car>> tyres= carsService.showCarsTyreType();
        convertMap(tyres);
    }
    private void option8 (){
        List<String> components = new ArrayList<>();
        boolean nextComponent;
       do {

            components.add(UserDataService.getString("Enter the name of the component you are looking for:").toUpperCase());
            String answer = UserDataService.getString("Do you want to enter another component? choose[y/n]").toLowerCase();
            nextComponent = !answer.equals("y");
        }while (!nextComponent);
        List<Car> carsContainingComponents = carsService.showCarsContainingComponents(components);
        System.out.println(toJson(carsContainingComponents));
    }

    /*Method returning object from JSON file in a pretty printing manner*/
    private static <T> String toJson(T element) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(element);
    }
    /*Method converting map*/
    private void convertMap(Map<?, ?> map) {
         MapUtils.verbosePrint(System.out,"", map);
    }
}
