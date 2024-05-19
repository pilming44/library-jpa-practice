package com.jpa.library.repository;

import com.jpa.library.dto.BookSearchForm;
import com.jpa.library.entity.Book;
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
        StringBuilder jpql = new StringBuilder("SELECT b FROM Book b LEFT JOIN b.author a LEFT JOIN b.publisher p WHERE 1=1");

        if (bookSearchForm.getTitle() != null && !bookSearchForm.getTitle().isEmpty()) {
            jpql.append(" AND b.title LIKE :title");
        }
        if (bookSearchForm.getAuthorName() != null && !bookSearchForm.getAuthorName().isEmpty()) {
            jpql.append(" AND a.name LIKE :authorName");
        }
        if (bookSearchForm.getPublisherName() != null && !bookSearchForm.getPublisherName().isEmpty()) {
            jpql.append(" AND p.name LIKE :publisherName");
        }

        TypedQuery<Book> query = em.createQuery(jpql.toString(), Book.class);

        if (bookSearchForm.getTitle() != null && !bookSearchForm.getTitle().isEmpty()) {
            query.setParameter("title", "%" + bookSearchForm.getTitle() + "%");
        }
        if (bookSearchForm.getAuthorName() != null && !bookSearchForm.getAuthorName().isEmpty()) {
            query.setParameter("authorName", "%" + bookSearchForm.getAuthorName() + "%");
        }
        if (bookSearchForm.getPublisherName() != null && !bookSearchForm.getPublisherName().isEmpty()) {
            query.setParameter("publisherName", "%" + bookSearchForm.getPublisherName() + "%");
        }

        query.setFirstResult((bookSearchForm.getPage()-1) * bookSearchForm.getSize());
        query.setMaxResults(bookSearchForm.getSize());
        
        
        return query.getResultList();
    }
}
