package com.jpa.library.repository;

import com.jpa.library.aop.log.trace.Trace;
import com.jpa.library.entity.Publisher;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Trace
public class PublisherRepository {
    private final EntityManager em;

    public void save(Publisher author) {
        em.persist(author);
    }

    public Optional<Publisher> findOne(Long id) {
        Publisher publisher = em.find(Publisher.class, id);
        return Optional.ofNullable(publisher);
    }

    public boolean existsByName(String name) {
        List<Publisher> results = em.createQuery("SELECT p FROM Publisher p WHERE p.name = :name", Publisher.class)
                .setParameter("name", name)
                .getResultList();
        return !results.isEmpty();
    }
}
