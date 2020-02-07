/*This is class creating carBody object*/
package org.example.model;

import lombok.*;
import org.example.model.enums.CarBodyType;
import org.example.model.enums.Color;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CarBody {
    private Color color;
    private CarBodyType type;
    private List<String> components;



    @Override
    public String toString() {
        return
                "color: " + color +
                " type: " + type +
                " components: " + components;
    }
}
