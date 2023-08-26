package com.flow.assignment.service;

import com.flow.assignment.web.dto.ExtensionRequestDto;
import com.flow.assignment.web.dto.ExtensionResponseDto;

import java.util.List;

public interface ExtensionService {
     List<ExtensionResponseDto> getExtensionsList();
     void deleteExtension(Long id);
     Long saveExtension(ExtensionRequestDto requestDto);
     Long checkedExtension(Long id, Boolean isChecked);
}