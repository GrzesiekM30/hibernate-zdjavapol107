package org.example;

import org.example.dao.AuthorDao;
import org.example.dao.MovieDao;
import org.example.model.Author;
import org.example.model.Movie;
import org.hibernate.SessionFactory;


import java.time.LocalDate;
import java.util.Optional;


public class App
{
    public static void main( String[] args ) {
        SessionFactory sessionFactory = new HibernateFactory().getSessionFactory();
        MovieDao movieDao = new MovieDao(sessionFactory);
        AuthorDao authorDao = new AuthorDao(sessionFactory);

        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setReleaseData(LocalDate.now());

        movieDao.save(movie);
        //save example 1
        Author author = new Author();
        author.setFirsName("Steven");
        author.setSecondName("Spielberg");
        author.setAddress("New York");
        authorDao.save(author);
        //save example 2 constructor
        authorDao.save(new Author("Joe","Doe","Chicago"));
        //save example 3 builder
        authorDao.save(Author.builder()
                .firsName("Guy")
                .secondName("Richi")
                .address("Los Angeles")
                .build());

        movieDao.save(new Movie("Terminator",LocalDate.now()));
        //get by id
        Optional<Movie> optionalMovie = movieDao.getId(99l);
        if (optionalMovie.isPresent()) {
            Movie movieById = optionalMovie.get();
            System.out.println(movieById);
        }


        sessionFactory.close();
    }
}
