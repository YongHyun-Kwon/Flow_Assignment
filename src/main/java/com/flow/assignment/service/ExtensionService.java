package com.flow.assignment.service;

import com.flow.assignment.web.dto.ExtensionRequestDto;
import com.flow.assignment.web.dto.ExtensionResponseDto;
import com.flow.assignment.web.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExtensionService {

     ResponseDto<List<ExtensionResponseDto>> getExtensionsList();
     ResponseDto<String> deleteExtension(Long id);
     ResponseDto<String> saveExtension(ExtensionRequestDto requestDto);
     ResponseDto<String> checkedExtension(Long id, Boolean isChecked);
     ResponseDto<String> uploadFile(MultipartFile file);
}