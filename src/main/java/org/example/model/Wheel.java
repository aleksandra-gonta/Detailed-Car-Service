package org.example.model;

import lombok.*;
import org.example.model.enums.TyreType;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Wheel {
    private String model;
    private int size;
    private TyreType type;


    @Override
    public String toString() {
        return "model: " + model + " size: " + size + " type:" + type;
    }
}
