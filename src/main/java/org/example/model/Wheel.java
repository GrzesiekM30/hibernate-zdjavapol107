package org.example.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wheel {

    @Id
    private Long id;
    private String name;
    private Integer size;
    private String type;


}
