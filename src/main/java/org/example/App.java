package org.example;

import org.example.model.Author;
import org.example.model.Car;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;


public class App
{
    public static void main( String[] args ) {
        //Car fiatPanda = new Car("Fiat panda", 2002, 3);
        Movie movie = new Movie();
        //movie.setTitle("Titanic");
        movie.setReleaseData(LocalDate.now());
        Author author = new Author();
        author.setFirsName("Steven");
        author.setSecondName("Spielberg");
        author.setAddress("New York");


        SessionFactory sessionFactory = new HibernateFactory().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(movie);
        transaction.commit();
        session.close();

        sessionFactory.close();

    }
}
