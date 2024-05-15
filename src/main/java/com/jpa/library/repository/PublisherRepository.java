package com.jpa.library.repository;

import com.jpa.library.entity.Publisher;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
