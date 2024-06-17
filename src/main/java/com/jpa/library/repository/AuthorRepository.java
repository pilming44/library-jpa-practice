package com.jpa.library.repository;

import com.jpa.library.aop.log.trace.Trace;
import com.jpa.library.entity.Author;
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
public class AuthorRepository {
    private final EntityManager em;

    public void save(Author author) {
        em.persist(author);
    }

    public Optional<Author> findOne(Long id) {
        Author author = em.find(Author.class, id);
        return Optional.ofNullable(author);
    }

    public boolean existsByName(String name) {
        List<Author> results = em.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class)
                .setParameter("name", name)
                .getResultList();
        return !results.isEmpty();
    }
}
