package com.jpa.library.service;

import com.jpa.library.dto.AuthorForm;
import com.jpa.library.dto.AuthorInfo;
import com.jpa.library.entity.Author;
import com.jpa.library.exception.DuplicateException;
import com.jpa.library.exception.EntityNotFoundException;
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
    public AuthorInfo save(AuthorForm authorForm) {
        dupilcationCheck(authorForm);

        Author author = new Author(authorForm.getName());

        authorRepository.save(author);

        return new AuthorInfo(author.getId(), author.getName());
    }

    private void dupilcationCheck(AuthorForm authorForm) {
        boolean isExist = authorRepository.existsByName(authorForm.getName());
        if (isExist) {
            throw new DuplicateException("이미 등록된 저자입니다.");
        }
    }

    public Author findOne(Long id) {
        return authorRepository.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
    }
}
