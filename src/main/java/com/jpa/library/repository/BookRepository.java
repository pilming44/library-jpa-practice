package com.jpa.library.repository;

import com.jpa.library.dto.BookSearchForm;
import com.jpa.library.entity.Book;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository {
    private final EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }

    public Optional<Book> findById(Long id) {
        Book book = em.find(Book.class, id);
        return Optional.ofNullable(book);
    }

    public List<Book> findAllBySearchForm(BookSearchForm bookSearchForm) {
        EntityGraph<Book> entityGraph = em.createEntityGraph(Book.class);
        entityGraph.addAttributeNodes("author", "publisher");

        StringBuilder jpql = new StringBuilder("SELECT b FROM Book b WHERE 1=1");

        if (bookSearchForm.getTitle() != null && !bookSearchForm.getTitle().isEmpty()) {
            jpql.append(" AND b.title LIKE :title");
        }
        if (bookSearchForm.getAuthorName() != null && !bookSearchForm.getAuthorName().isEmpty()) {
            jpql.append(" AND b.author.name LIKE :authorName");
        }
        if (bookSearchForm.getPublisherName() != null && !bookSearchForm.getPublisherName().isEmpty()) {
            jpql.append(" AND b.publisher.name LIKE :publisherName");
        }

        TypedQuery<Book> query = em.createQuery(jpql.toString(), Book.class)
                .setHint("jakarta.persistence.loadgraph", entityGraph);

        if (bookSearchForm.getTitle() != null && !bookSearchForm.getTitle().isEmpty()) {
            query.setParameter("title", "%" + bookSearchForm.getTitle() + "%");
        }
        if (bookSearchForm.getAuthorName() != null && !bookSearchForm.getAuthorName().isEmpty()) {
            query.setParameter("authorName", "%" + bookSearchForm.getAuthorName() + "%");
        }
        if (bookSearchForm.getPublisherName() != null && !bookSearchForm.getPublisherName().isEmpty()) {
            query.setParameter("publisherName", "%" + bookSearchForm.getPublisherName() + "%");
        }

        query.setFirstResult((bookSearchForm.getPage() - 1) * bookSearchForm.getSize());
        query.setMaxResults(bookSearchForm.getSize());

        return query.getResultList();
    }

    public Long countBySearchForm(BookSearchForm bookSearchForm) {
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(b.id) FROM Book b WHERE 1=1");

        if (bookSearchForm.getTitle() != null && !bookSearchForm.getTitle().isEmpty()) {
            countJpql.append(" AND b.title LIKE :title");
        }
        if (bookSearchForm.getAuthorName() != null && !bookSearchForm.getAuthorName().isEmpty()) {
            countJpql.append(" AND b.author.name LIKE :authorName");
        }
        if (bookSearchForm.getPublisherName() != null && !bookSearchForm.getPublisherName().isEmpty()) {
            countJpql.append(" AND b.publisher.name LIKE :publisherName");
        }

        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);

        if (bookSearchForm.getTitle() != null && !bookSearchForm.getTitle().isEmpty()) {
            countQuery.setParameter("title", "%" + bookSearchForm.getTitle() + "%");
        }
        if (bookSearchForm.getAuthorName() != null && !bookSearchForm.getAuthorName().isEmpty()) {
            countQuery.setParameter("authorName", "%" + bookSearchForm.getAuthorName() + "%");
        }
        if (bookSearchForm.getPublisherName() != null && !bookSearchForm.getPublisherName().isEmpty()) {
            countQuery.setParameter("publisherName", "%" + bookSearchForm.getPublisherName() + "%");
        }

        return countQuery.getSingleResult();
    }
}
