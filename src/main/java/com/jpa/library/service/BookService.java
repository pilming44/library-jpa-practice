package com.jpa.library.service;

import com.jpa.library.dto.*;
import com.jpa.library.entity.Author;
import com.jpa.library.entity.Book;
import com.jpa.library.entity.BookLoan;
import com.jpa.library.entity.Publisher;
import com.jpa.library.enums.BookStatus;
import com.jpa.library.exception.DuplicateException;
import com.jpa.library.exception.EntityNotFoundException;
import com.jpa.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookLoanService bookLoanService;

    @Transactional
    public Long save(BookForm bookForm) {
        validBookStatus(bookForm);
        Author author = authorService.findOne(bookForm.getAuthorId());
        Publisher publisher = publisherService.findOne(bookForm.getPublisherId());
        duplicationCheck(bookForm, author, publisher);
        Book book = Book.createBook(bookForm.getTitle(), author, publisher, BookStatus.valueOf(bookForm.getStatus()), bookForm.getTotalQuantity());
        bookRepository.save(book);
        return book.getId();
    }

    private void duplicationCheck(BookForm bookForm, Author author, Publisher publisher) {
        Long duplicationBookCount = bookRepository.countBySearchForm(
                BookSearchForm.builder()
                        .title(bookForm.getTitle())
                        .authorName(author.getName())
                        .publisherName(publisher.getName())
                        .build());
        if (duplicationBookCount > 0) {
            throw new DuplicateException("이미 등록 된 책입니다.");
        }
    }

    private static void validBookStatus(BookForm bookForm) {
        try {
            BookStatus.valueOf(bookForm.getStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 책 상태입니다: " + bookForm.getStatus());
        }
    }

    public ResultWrapper findBookSummaryList(BookSearchForm bookSearchForm) {
        List<Book> bookList = bookRepository.findAllBySearchForm(bookSearchForm);
        Long totalCount = bookRepository.countBySearchForm(bookSearchForm);
        int totalPage = (int) Math.ceil((double) totalCount / bookSearchForm.getSize());

        List<BookSummary> bookSummaries = new ArrayList<>();
        for (Book book : bookList) {
            long loanQuantity = book.getBookLoans().stream()
                    .filter(bookLoan -> bookLoan.getReturnDate() == null)
                    .count();
            bookSummaries.add(BookSummary.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .authorName(book.getAuthor().getName())
                    .publisherName(book.getPublisher().getName())
                    .totalQuantity(book.getTotalQuantity())
                    .loanQuantity(loanQuantity)
                    .build());
        }
        return new ResultWrapper<>(bookSummaries, totalCount, bookSearchForm.getPage(), bookSearchForm.getSize(), totalPage);

    }

    public ResultWrapper findBookInfo(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            AuthorInfo authorInfo = new AuthorInfo(book.getAuthor().getId(), book.getAuthor().getName());
            PublisherInfo publisherInfo = new PublisherInfo(book.getPublisher().getId(), book.getPublisher().getName());
            return new ResultWrapper<>(new BookInfo(book.getId(), book.getTitle(), authorInfo, publisherInfo, book.getStatus(), book.getTotalQuantity()));
        } else {
            throw new EntityNotFoundException("책을 찾을 수 없습니다. ID: " + bookId);
        }
    }

    public ResultWrapper findBookDeatil(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("책을 찾을 수 없습니다. ID: " + bookId));

        List<BookLoan> bookLoans = bookLoanService.findByBookIdAndReturnDateIsNull(bookId);

        AuthorInfo authorInfo = new AuthorInfo(book.getAuthor().getId(), book.getAuthor().getName());
        PublisherInfo publisherInfo = new PublisherInfo(book.getPublisher().getId(), book.getPublisher().getName());

        List<BookLoanStatus> bookLoanStatusList = bookLoans.stream()
                .map(loan -> new BookLoanStatus(
                        loan.getBorrowerName(),
                        loan.getLoanDate(),
                        loan.getDueDate(),
                        loan.getOverdueDays(LocalDateTime.now())
                ))
                .collect(Collectors.toList());
        BookDetail bookDetail = new BookDetail(
                book.getId(),
                book.getTitle(),
                authorInfo,
                publisherInfo,
                book.getStatus(),
                book.getTotalQuantity(),
                bookLoanStatusList
        );
        return new ResultWrapper<>(bookDetail);
    }

    @Transactional
    public ResultWrapper returnBook(BookReturnForm bookReturnForm) {
        BookLoan bookLoan = bookLoanService.findByBookNameAndBorrowerName(bookReturnForm.getBookName(), bookReturnForm.getBorrowerName());
        Long overdueFee = bookLoan.returnBook(bookReturnForm.getReturnDate());

        BookReturnResult returnResult = BookReturnResult.builder()
                .title(bookLoan.getBook().getTitle())
                .loanDate(bookLoan.getLoanDate())
                .dueDate(bookLoan.getDueDate())
                .returnDate(bookLoan.getReturnDate())
                .overdueFee(overdueFee)
                .build();

        return new ResultWrapper<>(returnResult);

    }
}
