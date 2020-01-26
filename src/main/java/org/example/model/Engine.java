package org.example.model;

import lombok.*;
import org.example.model.enums.EngineType;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Engine {
    private EngineType type;
    private double power;



    @Override
    public String toString() {
        return "model.Engine type: " + type + " power: " + power;
    }
}
