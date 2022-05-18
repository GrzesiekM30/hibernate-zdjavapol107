package org.example.dao;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

@AllArgsConstructor
public class EntityDao <T>{
    private SessionFactory sessionFactory;
    private Class<T> clazz;

    //create
    public Long save(T entity){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }
    //get by id
    public Optional<T> getById(Long id) {
        Session session = sessionFactory.openSession();
        T entity = session.find(clazz, id);
        session.close();
        return Optional.ofNullable(entity);
    }

    //update
    public void update(T entity){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }

    //delete
    public void delete(Long id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        getById(id).ifPresent(session::delete);
        transaction.commit();
        session.close();
    }
}
