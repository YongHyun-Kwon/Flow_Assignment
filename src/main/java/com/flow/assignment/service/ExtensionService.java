package com.flow.assignment.service;

import com.flow.assignment.domain.extension.CustomExtension;
import com.flow.assignment.domain.extension.CustomExtensionRepository;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExtensionService {

    private final CustomExtensionRepository customExtensionRepository;

    public Long saveExtension(ExtensionRequestDto extensionRequestDto) {
        return validateAndSaveExtension(extensionRequestDto);
    }
    private Long validateAndSaveExtension(ExtensionRequestDto extensionRequestDto) {
        int MAX_SIZE = 200;
        if (customExtensionRepository.countAllCustomExtensions() > MAX_SIZE) {
            throw new IllegalStateException("확장자는 200개 까지 저장 가능합니다.");
        }
        existsExtension(extensionRequestDto);
        CustomExtension customExtension = CustomExtension.createCustomException(extensionRequestDto);
        return customExtensionRepository.save(customExtension).getId();
    }
    public void existsExtension(ExtensionRequestDto extensionRequestDto) {
            long exists =  customExtensionRepository.countByNameOrExtensionName(extensionRequestDto.getName());
            if (exists > 0) throw new IllegalStateException("이미 존재하는 확장자입니다.");
    }

}
