package com.flow.assignment.service.impl;

import com.flow.assignment.domain.extension.*;
import com.flow.assignment.service.ExtensionService;
import com.flow.assignment.web.IndexController;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import com.flow.assignment.web.dto.ExtensionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExtensionServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;
    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDto> getExtensionsList() {
        return extensionRepository.findAll().stream().
                map(ExtensionResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void deleteExtension(Long id) {
        Extension extension = extensionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        extensionRepository.delete(extension);
    }

    @Override
    public Long checkedExtension(Long id, Boolean isChecked) {
        Extension extension = extensionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        extension.checkedExtension(isChecked);
        return extension.getId();
    }

    @Override
    public Long saveExtension(ExtensionRequestDto requestDto) {
        return validateAndSaveExtension(requestDto);
    }

    private Long validateAndSaveExtension(ExtensionRequestDto requestDto) {
        int MAX_SIZE = 200;
        if (extensionRepository.countAllExtensions() > MAX_SIZE) {
            throw new IllegalStateException("추가 확장자는 200개 까지 저장 가능합니다.");
        }
        existsExtension(requestDto);
        Extension extension = Extension.createExtension(requestDto);
        return extensionRepository.save(extension).getId();
    }

    private void existsExtension(ExtensionRequestDto extensionRequestDto) {
        boolean exists =  extensionRepository.existsByName(extensionRequestDto.getName());
        if (exists) {
            throw new IllegalStateException("이미 존재하는 확장자입니다.");
        }
    }
}