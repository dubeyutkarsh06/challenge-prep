package com.katanox.api.repository.impl;

//import com.katanox.test.sql.tables.Hotels;
import com.katanox.api.model.orm.Hotel;

import com.katanox.api.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JPAHotelRepository implements HotelRepository {

    private EntityManager entityManager;

    private JPAPriceRepository JPAPriceRepository;

    private JPAExtraChargesRepository JPAExtraChargesRepository;

    private JPAExtraChargesPercentageRepository JPAExtraChargesPercentageRepository;

    @Autowired
    public JPAHotelRepository(EntityManager entityManager, JPAPriceRepository JPAPriceRepository, JPAExtraChargesRepository JPAExtraChargesRepository, JPAExtraChargesPercentageRepository JPAExtraChargesPercentageRepository){
        this.entityManager = entityManager;
        this.JPAPriceRepository = JPAPriceRepository;
        this.JPAExtraChargesRepository = JPAExtraChargesRepository;
        this.JPAExtraChargesPercentageRepository = JPAExtraChargesPercentageRepository;
    }

    @Override
    public List<Hotel> findAll() {
        TypedQuery<Hotel> query = this.entityManager.createQuery("FROM Hotel ", Hotel.class);
        return query.getResultList();
    }

}
