package org.example;

import org.example.dao.old.OldAuthorDao;
import org.example.dao.old.OldMovieDao;
import org.example.model.Author;
import org.example.model.Movie;
import org.hibernate.SessionFactory;


import java.time.LocalDate;
import java.util.Optional;


public class App
{
    public static void main( String[] args ) {
        SessionFactory sessionFactory = new HibernateFactory().getSessionFactory();
        OldMovieDao oldMovieDao = new OldMovieDao(sessionFactory);
        OldAuthorDao oldAuthorDao = new OldAuthorDao(sessionFactory);

        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setReleaseData(LocalDate.now());
        oldMovieDao.save(movie);

        saveAuthorExample(oldAuthorDao);

        getByIdWithOptional(oldMovieDao);

        updateExample(oldMovieDao);

        deleteExample(oldAuthorDao);


        sessionFactory.close();
    }

    private static void deleteExample(OldAuthorDao oldAuthorDao) {
        Long id = oldAuthorDao.save(new Author("James", "Cameron", "Miami"));
        oldAuthorDao.delete(id);
        System.out.println("Author with id " + id + " was deleted.");
    }

    private static void updateExample(OldMovieDao oldMovieDao) {
        Optional<Movie> byId = oldMovieDao.getId(4L);
        if (byId.isPresent()){
            Movie movie1 = byId.get();
            System.out.println("Movie before update: " + movie1);
            movie1.setTitle("Updated title");
            oldMovieDao.update(movie1);
        }
        Optional<Movie> updatedMovie = oldMovieDao.getId(4L);
        if (updatedMovie.isPresent()) {
            System.out.println("Movie after update:" + updatedMovie.get());
        }
        // lub updatedMovie.ifPresent(System.out::println);
        // updatedMovie.ifPresent(value -> System.out.println("Movie after update:" + value));
    }

    private static void getByIdWithOptional(OldMovieDao oldMovieDao) {
        oldMovieDao.save(new Movie("Terminator",LocalDate.now()));
        //get by id
        Optional<Movie> optionalMovie = oldMovieDao.getId(99L);
        if (optionalMovie.isPresent()) {
            Movie movieById = optionalMovie.get();
            System.out.println(movieById);
        }
    }

    private static void saveAuthorExample(OldAuthorDao oldAuthorDao) {
        //save example 1
        Author author = new Author();
        author.setFirsName("Steven");
        author.setSecondName("Spielberg");
        author.setAddress("New York");
        oldAuthorDao.save(author);
        //save example 2 constructor
        oldAuthorDao.save(new Author("Joe","Doe","Chicago"));
        //save example 3 builder
        oldAuthorDao.save(Author.builder()
                .firsName("Guy")
                .secondName("Richi")
                .address("Los Angeles")
                .build());
    }
}
