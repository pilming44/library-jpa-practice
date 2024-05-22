package com.jpa.library.service;

import com.jpa.library.dto.BookForm;
import com.jpa.library.dto.BookSearchForm;
import com.jpa.library.dto.BookSummary;
import com.jpa.library.dto.ResultWrapper;
import com.jpa.library.entity.Author;
import com.jpa.library.entity.Book;
import com.jpa.library.entity.BookLoan;
import com.jpa.library.entity.Publisher;
import com.jpa.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookLoanService bookLoanService;

    @Transactional
    public void save(BookForm bookForm) {
        Author author = authorService.findOne(bookForm.getAuthorId());
        Publisher publisher = publisherService.findOne(bookForm.getPublisherId());
        Book book = new Book(bookForm.getTitle(), author, publisher, bookForm.getStatus(), bookForm.getTotalQuantity());
        bookRepository.save(book);
    }

    public ResultWrapper findBookSummaryList(BookSearchForm bookSearchForm) {
        List<Book> bookList = bookRepository.findAllBySearchForm(bookSearchForm);
        Long totalCount = bookRepository.countBySearchForm(bookSearchForm);
        int totalPage = (int) Math.ceil((double) totalCount / bookSearchForm.getSize());

        List<BookSummary> bookSummaries = new ArrayList<>();
        for (Book book : bookList) {
            List<BookLoan> unreturnedBookLoans = bookLoanService.findUnreturnedBookLoansByBookId(book.getId());
            bookSummaries.add(BookSummary.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .authorName(book.getAuthor().getName())
                    .publisherName(book.getPublisher().getName())
                    .totalQuantity(book.getTotalQuantity())
                    .loanQuantity(unreturnedBookLoans.size())
                    .build());
        }
        return new ResultWrapper<>(bookSummaries, totalCount, bookSearchForm.getPage(), bookSearchForm.getSize(), totalPage);

    }
}
