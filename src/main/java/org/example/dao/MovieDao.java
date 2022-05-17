package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.HibernateFactory;
import org.example.model.Movie;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

//CRUD - create, read,update, delete
//DAO - data access object
@AllArgsConstructor
public class MovieDao {
    private SessionFactory sessionFactory;
    //create
    public void save(Movie movie){
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(movie);
            transaction.commit();
            session.close();
        }catch (HibernateException e){
            Transaction transaction = sessionFactory.getCurrentSession().getTransaction();
            transaction.rollback();
        }
    }
    //readOne
    public Optional<Movie> getId(Long id){
        Session session = sessionFactory.openSession();
        Movie movie = session.find(Movie.class, id);
        session.close();
        return Optional.ofNullable(movie);

    }
    //readAll -
    //update
    //delete
}
