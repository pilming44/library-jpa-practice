package com.jpa.library.repository;

import com.jpa.library.entity.Author;
import com.jpa.library.entity.BookLoan;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorRepository {
    private final EntityManager em;

    public void save(Author author) {
        em.persist(author);
    }

    public Author findOne(Long id) {
        return em.find(Author.class, id);
    }

    public boolean existsByName(String name) {
        List<Author> results = em.createQuery("SELECT a FROM Author a WHERE a.name = :name", Author.class)
                .setParameter("name", name)
                .getResultList();
        return !results.isEmpty();
    }
}
