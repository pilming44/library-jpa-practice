package com.jpa.library.repository;

import com.jpa.library.entity.Book;
import com.jpa.library.entity.BookLoan;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookLoanRepository {
    private final EntityManager em;

    public void save(BookLoan bookLoan) {
        em.persist(bookLoan);
    }

    public List<BookLoan> findUnreturnedBookLoansByBookId(Long bookId) {
        return em.createQuery("SELECT bl FROM BookLoan bl WHERE bl.book.id = :bookId AND bl.returnDate IS NULL", BookLoan.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
