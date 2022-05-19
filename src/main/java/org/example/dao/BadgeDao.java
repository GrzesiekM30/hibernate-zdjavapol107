package org.example.dao;

import org.example.model.Badge;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class BadgeDao extends EntityDao<Badge> {
    public BadgeDao(SessionFactory sessionFactory) {
        super(sessionFactory, Badge.class);
    }

    public List<Badge> getAllBadges(){
        Session session = sessionFactory.openSession();
        Query<Badge> fromBadge = session.createQuery("From Badge", Badge.class);
        List<Badge> resultList = fromBadge.getResultList();
        session.close();
        return resultList;
    }




}
