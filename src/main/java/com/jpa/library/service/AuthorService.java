package com.jpa.library.service;

import com.jpa.library.dto.AuthorForm;
import com.jpa.library.entity.Author;
import com.jpa.library.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public void save(AuthorForm authorForm) {
        authorRepository.save(new Author(authorForm.getName()));
    }

    public Author findOne(Long id) {
        return authorRepository.findOne(id);
    }
}
