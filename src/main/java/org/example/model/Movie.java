package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private LocalDate releaseData;
    @OneToOne
    private Badge badge;
    @ManyToOne
    private Author author;
    @ManyToMany
    private Set<Actor> actors;

    public Movie(String title, LocalDate releaseData) {
        this.title = title;
        this.releaseData = releaseData;
    }
}
