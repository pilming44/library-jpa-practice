package com.jpa.library.repository;

import com.jpa.library.aop.log.trace.Trace;
import com.jpa.library.entity.BookLoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Trace
public class BookLoanRepository {
    private final EntityManager em;

    public void save(BookLoan bookLoan) {
        em.persist(bookLoan);
    }

    public List<BookLoan> findByBookIdAndReturnDateIsNull(Long bookId) {
        return em.createQuery("SELECT bl FROM BookLoan bl WHERE bl.book.id = :bookId AND bl.returnDate IS NULL", BookLoan.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    public List<BookLoan> findUnreturnedBookLoansByBookId(Long bookId) {
        return em.createQuery("SELECT bl FROM BookLoan bl WHERE bl.book.id = :bookId AND bl.returnDate IS NULL", BookLoan.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    public boolean existsByBookIdAndBorrowerNameAndReturnDateIsNull(Long bookId, String borrowerName) {

        List<BookLoan> results = em.createQuery("SELECT bl FROM BookLoan bl WHERE bl.book.id = :bookId AND bl.borrowerName = :borrowerName AND bl.returnDate IS NULL", BookLoan.class)
                .setParameter("bookId", bookId)
                .setParameter("borrowerName", borrowerName)
                .getResultList();
        return !results.isEmpty();
    }

    public Optional<BookLoan> findByBookNameAndBorrowerName(String bookName, String borrowerName) {
        try {
            BookLoan singleResult = em.createQuery(
                            "SELECT bl FROM BookLoan bl JOIN bl.book b WHERE b.title = :bookName AND bl.borrowerName = :borrowerName AND bl.returnDate IS NULL",
                            BookLoan.class)
                    .setParameter("bookName", bookName)
                    .setParameter("borrowerName", borrowerName)
                    .getSingleResult();
            return Optional.of(singleResult);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}


