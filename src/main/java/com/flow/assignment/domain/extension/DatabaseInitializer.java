package com.flow.assignment.domain.extension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private ExtensionRepository extensionRepository;

    @Override
    public void run(String... args) {
        if (extensionRepository.count() == 0) {
            List<String> predefinedExtensions = Arrays.asList("bat", "cmd", "com", "cpl", "exe", "scr", "js");

            for (String str : predefinedExtensions) {

                Extension extension = Extension.builder()
                        .name(str)
                        .type(ExtensionType.DEFAULT)
                        .build();
                extensionRepository.save(extension);
            }
        }
    }
}
