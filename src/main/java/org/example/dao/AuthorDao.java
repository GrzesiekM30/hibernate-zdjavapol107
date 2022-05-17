package org.example.dao;


import lombok.AllArgsConstructor;
import org.example.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@AllArgsConstructor
//CRUD
public class AuthorDao {

    private SessionFactory sessionFactory;
    //create
    public void save(Author author){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(author);
        transaction.commit();
        session.close();

    }
}
