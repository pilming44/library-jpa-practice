package com.jpa.library.service;

import com.jpa.library.dto.AuthorForm;
import com.jpa.library.dto.BookForm;
import com.jpa.library.dto.PublisherForm;
import com.jpa.library.enums.BookStatus;
import com.jpa.library.exception.DataInitializationException;
import com.jpa.library.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService {
    private final String AUTHOR_NAME_FILE_PATH = "src/main/resources/static/author_name.txt";
    private final String PUBLISHER_NAME_FILE_PATH = "src/main/resources/static/publisher_name.txt";
    private final String BOOK_NAME_FILE_PATH = "src/main/resources/static/book_name.txt";

    private final FileUtil fileUtil;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookService bookService;

    private final List<String> authorNameList = new ArrayList<>();
    private final List<String> publisherNameList = new ArrayList<>();

    public void initializeData() {
        try {
            authorsInitFromFile(AUTHOR_NAME_FILE_PATH);
            publisherInitFromFile(PUBLISHER_NAME_FILE_PATH);
            bookInitFromFile(BOOK_NAME_FILE_PATH);
        } catch (Exception e) {
            throw new DataInitializationException("초기 데이터를 이미 초기화 했습니다.");
        }
    }

    private void authorsInitFromFile(String filePath) throws IOException {
        List<String> testNames = extractFileData(filePath);
        authorNameList.addAll(testNames);

        testNames.stream()
                .map(AuthorForm::new)
                .forEach(authorService::save);
    }

    private void publisherInitFromFile(String filePath) throws IOException {
        List<String> testPublisher = extractFileData(filePath);
        publisherNameList.addAll(testPublisher);

        testPublisher.stream()
                .map(PublisherForm::new)
                .forEach(publisherService::save);
    }

    private void bookInitFromFile(String filePath) throws IOException {
        List<String> testBookTitle = extractFileData(filePath);

        Random random = new Random();
        for (String bookTitle : testBookTitle) {
            Long randomAuthorId = random.nextLong(authorNameList.size()) + 1;
            Long randomPublisherId = random.nextLong(publisherNameList.size()) + 1;
            int randomQuantity = random.nextInt(10) + 1;
            bookService.save(new BookForm(bookTitle, randomAuthorId, randomPublisherId, BookStatus.IN_STOCK.name(), randomQuantity));
        }
    }

    private List<String> extractFileData(String filePath) throws IOException {
        List<String> lines = fileUtil.readLines(filePath);
        return lines.stream()
                .flatMap(line -> Arrays.stream(line.split(",")))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
