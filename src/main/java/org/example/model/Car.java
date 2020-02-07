/*This is class creating car object*/
package org.example.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Car {
    private String model;
    private BigDecimal price;
    private double mileage;
    private Engine engine;
    private CarBody carBody;
    private Wheel wheel;



    @Override
    public String toString() {
        return "Car " +
                "model: " + model +
                " price: " + price +
                " mileage: " + mileage +
                " engine: " + engine +
                " carBody: " + carBody +
                " wheel: " + wheel ;
    }
}
