package com.jpa.library.repository;

import com.jpa.library.entity.Author;
import com.jpa.library.entity.Publisher;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PublisherRepository {
    private final EntityManager em;

    public void save(Publisher author) {
        em.persist(author);
    }

    public Publisher findOne(Long id) {
        return em.find(Publisher.class, id);
    }

    public boolean existsByName(String name) {
        List<Publisher> results = em.createQuery("SELECT p FROM Publisher p WHERE p.name = :name", Publisher.class)
                .setParameter("name", name)
                .getResultList();
        return !results.isEmpty();
    }
}
