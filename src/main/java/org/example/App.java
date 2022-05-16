package org.example;

import org.example.model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Calendar;

public class App
{
    public static void main( String[] args ) {
        //Car fiatPanda = new Car("Fiat panda", 2002, 3);

        SessionFactory sessionFactory = new HibernateFactory().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.close();


        sessionFactory.close();

    }
}
