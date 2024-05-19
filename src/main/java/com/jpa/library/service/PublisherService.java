package com.jpa.library.service;

import com.jpa.library.dto.AuthorInfo;
import com.jpa.library.dto.PublisherForm;
import com.jpa.library.dto.PublisherInfo;
import com.jpa.library.entity.Author;
import com.jpa.library.entity.Publisher;
import com.jpa.library.exception.DuplicateException;
import com.jpa.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;

    @Transactional
    public PublisherInfo save(PublisherForm publisherForm) {
        dupilcationCheck(publisherForm);

        Publisher publisher = new Publisher(publisherForm.getName());

        publisherRepository.save(publisher);

        return new PublisherInfo(publisher.getId(), publisher.getName());
    }

    private void dupilcationCheck(PublisherForm publisherForm) {
        boolean isExist = publisherRepository.existsByName(publisherForm.getName());
        if (isExist) {
            throw new DuplicateException("이미 등록된 출판사입니다.");
        }
    }

    public Publisher findOne(Long id) {
        return publisherRepository.findOne(id);
    }
}
