package com.jpa.library;

import com.jpa.library.service.DataInitializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {
    private final DataInitializationService dataInitializationService;

    @Override
    public void run(ApplicationArguments args) {
        dataInitializationService.initializeData();
    }
}
