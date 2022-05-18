package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firsName;
    private String secondName;
    private String address;
    @OneToMany(mappedBy = "author")
    private Set<Movie> movies;

    public Author(String firsName, String secondName, String address) {
        this.firsName = firsName;
        this.secondName = secondName;
        this.address = address;
    }
}
