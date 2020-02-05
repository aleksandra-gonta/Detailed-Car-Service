package org.example.service;

import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;
import org.example.converter.CarsJsonConverter;
import org.example.exception.AppException;
import org.example.model.Car;
import org.example.model.data.Data;
import org.example.model.data.EnginePowerStatistics;
import org.example.model.data.MileageStatistics;
import org.example.model.data.PriceStatistics;
import org.example.model.enums.*;
import org.example.validator.CarValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarsService {
    private final Set<Car> cars;

    public CarsService(String jsonFilename) {
        this.cars = parseAndValidateCars(jsonFilename);
    }

    private static Set<Car> parseAndValidateCars(String jsonFilename) {

        if (jsonFilename == null) {
            throw new AppException("json filename string is null");
        }

        AtomicInteger counter = new AtomicInteger(1);
        CarValidator carValidator = new CarValidator();

        return new CarsJsonConverter(jsonFilename)
                .fromJson()
                .orElseThrow(() -> new AppException("cannot parse data from json file " + jsonFilename))
                .stream()
                .filter(car -> {
                    var errors = carValidator.validate(car);
                    if (carValidator.hasErrors()) {
                        System.out.println("Validation errors for car no. " + counter.get() + ":");
                        errors.forEach((field, message) -> System.out.println(field + ": " + message));
                        System.out.println("-----------------------------------------------------");
                    }
                    counter.incrementAndGet();
                    return !carValidator.hasErrors();
                }).collect(Collectors.toSet());
    }


    public List<Car> sortBy(OrderBy orderBy, boolean descending) {
        List<Car> orderedCars = switch (orderBy) {

            case NUMBER_OF_COMPONENTS -> cars
                    .stream()
                    .sorted(Comparator.comparing(c -> c.getCarBody().getComponents().size()))
                    .collect(Collectors.toList());


            case ENGINE_POWER -> cars
                    .stream()
                    .sorted(Comparator.comparing(c -> c.getEngine().getPower()))
                    .collect(Collectors.toList());


            default -> cars
                    .stream()
                    .sorted(Comparator.comparing(c -> c.getWheel().getSize()))
                    .collect(Collectors.toList());


        };
        if (descending) {
            Collections.reverse(orderedCars);
        }

        return orderedCars;

    }

    public List<Car> findCarsBodyTypeInThePriceRange(CarBodyType type, BigDecimal minPrice, BigDecimal maxPrice) {


        return cars
                .stream()
                .filter(c -> c.getCarBody().getType().equals(type) && c.getPrice().compareTo(minPrice) > 0 && c.getPrice().compareTo(maxPrice) < 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());


    }

    public List<Car> findCarsWithEngineType(EngineType type) {

        return cars
                .stream()
                .filter(c -> c.getEngine().getType().equals(type))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());

    }


    public Data calculateCarsStatistics(CarStatistics statistics) {

        switch (statistics) {
            case PRICE:
                BigDecimalSummaryStatistics priceStats = cars.stream()
                        .collect(Collectors2.summarizingBigDecimal(c -> c.getPrice()));
                var priceStatistics = new PriceStatistics(
                        priceStats.getMin(), priceStats.getMax(), priceStats.getAverage()
                );

                return new Data<>(priceStatistics);
            case MILEAGE:
                DoubleSummaryStatistics mileageStats = cars.stream()
                        .collect(Collectors.summarizingDouble(c -> c.getMileage()));
                var mileageStatistics = new MileageStatistics(
                        mileageStats.getMin(), mileageStats.getMax(), mileageStats.getAverage());

                return new Data<>(mileageStatistics);
            default:
                DoubleSummaryStatistics engineStats = cars.stream()
                        .collect(Collectors.summarizingDouble(c -> c.getEngine().getPower()));
                var enginePowerStatistics = new EnginePowerStatistics(
                        engineStats.getMin(), engineStats.getMax(), engineStats.getAverage());

                return new Data<>(enginePowerStatistics);
        }

    }


    public Map<Car, Double> showCarsAndTheirMileage() {
        return cars
                .stream()
                .collect(Collectors.toMap(Function.identity(), Car::getMileage))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::max, LinkedHashMap::new));
    }


    public Map<TyreType, List<Car>> showCarsTyreType() {


        return cars
                .stream()
                .collect(Collectors.groupingBy(car -> car.getWheel().getType()))
                .entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, () -> new LinkedHashMap<>()));


    }

    public List<Car> showCarsContainingComponents(List<String> components) {

        return cars
                .stream()
                .filter(c -> c.getCarBody().getComponents().containsAll(components))
                .collect(Collectors.toList());


    }


    @Override
    public String toString() {
        return cars.stream().map(Car::toString).collect(Collectors.joining("\n"));
    }
}
