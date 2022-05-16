package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//lombok example
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Car {
    private String model;
    private int productionYear;
    private int amountOfDoors;

}
