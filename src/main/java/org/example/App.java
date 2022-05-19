package org.example;


import org.example.dao.ActorDao;
import org.example.dao.AuthorDao;
import org.example.dao.BadgeDao;
import org.example.dao.MovieDao;
import org.example.dao.old.OldAuthorDao;
import org.example.dao.old.OldMovieDao;
import org.example.model.Actor;
import org.example.model.Author;
import org.example.model.Badge;
import org.example.model.Movie;
import org.hibernate.SessionFactory;


import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


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

        MovieDao movieDao = new MovieDao(sessionFactory);
        BadgeDao badgeDao = new BadgeDao(sessionFactory);
        AuthorDao authorDao = new AuthorDao(sessionFactory);
        ActorDao actorDao = new ActorDao(sessionFactory);

        oneToOneRelationExample(movieDao, badgeDao);

        oneToManyExample(movieDao, authorDao);

        manyToManyExample(movieDao, actorDao);

        hqlExample(badgeDao, actorDao);


        sessionFactory.close();
    }

    private static void hqlExample(BadgeDao badgeDao, ActorDao actorDao) {
        System.out.println("ALL BADGES:");
        badgeDao.getAllBadges().forEach(System.out::println);
        System.out.println("ALL ZENEK'S;");
        actorDao.getByName("Zenek").forEach(System.out::println);
        System.out.println("UPDATE!! Zmiana Zenka na Zenona");
        actorDao.updateAllNames("Zenek","Zenon");
        actorDao.getByName("Zenon").forEach(System.out::println);
    }

    private static void manyToManyExample(MovieDao movieDao, ActorDao actorDao) {
        Actor actor1 = new Actor();
        actor1.setName("Jurek");
        actor1.setYearsOfExperience(4);
        Actor actor2 = new Actor();
        actor2.setName("Zenek");
        actor2.setYearsOfExperience(2);

        Movie movie1 = new Movie("Jurek i Zenek", LocalDate.now());
        Movie movie2 = new Movie("Jurek i Zenek Ostateczne Starcie", LocalDate.now());
        //te dwa settery nie są konieczne ale jak mamy poprawnie zdefiniowane relacjie możemy ich użyć i tak
        //actor1.setMovies(Set.of(movie1,movie2));
        //actor2.setMovies(Set.of(movie1,movie2));

        movie1.setActors(Set.of(actor1,actor2));
        movie2.setActors(Set.of(actor1,actor2));

        //najpierw trzeba zapisać aktorów a poźniej filmy do których ich przypisaliśmy
        //w każdej relacji ta kolejność jest ważna
        actorDao.save(actor1);
        actorDao.save(actor2);

        movieDao.save(movie1);
        movieDao.save(movie2);
    }

    private static void oneToManyExample(MovieDao movieDao, AuthorDao authorDao) {
        Author authorPeter = new Author("Peter", "Jackson", "New Zeland");
        Movie lotr = new Movie("Lord of the Rings", LocalDate.of(2002, 9, 15));
        Movie hobbit = new Movie("Hobbit", LocalDate.of(2015, 8, 10));

        lotr.setAuthor(authorPeter);
        hobbit.setAuthor(authorPeter);

        authorDao.save(authorPeter);
        movieDao.save(lotr);
        movieDao.save(hobbit);
    }

    private static void oneToOneRelationExample(MovieDao movieDao, BadgeDao badgeDao) {
        Movie projectXMovie = new Movie("Project X", LocalDate.of(2012, 11, 1));
        Badge badge = new Badge();
        badge.setName("Great badge, great movie");
        badge.setValue(3);

        projectXMovie.setBadge(badge);
        //badge.setMovie(projectXMovie);

        //kolejność jest ważna!!! musi być tak jak pod spodem
        badgeDao.save(badge);
        movieDao.save(projectXMovie);
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
