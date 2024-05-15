package com.jpa.library.service;

import com.jpa.library.dto.PublisherForm;
import com.jpa.library.entity.Publisher;
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
    public void save(PublisherForm publisherForm) {
        publisherRepository.save(new Publisher(publisherForm.getName()));
    }

    public Publisher findOne(Long id) {
        return publisherRepository.findOne(id);
    }
}
