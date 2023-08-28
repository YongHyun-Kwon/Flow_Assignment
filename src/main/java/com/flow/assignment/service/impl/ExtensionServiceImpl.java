package com.flow.assignment.service.impl;

import com.flow.assignment.domain.extension.Extension;
import com.flow.assignment.domain.extension.ExtensionRepository;
import com.flow.assignment.service.ExtensionService;
import com.flow.assignment.utils.ResponseMessage;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import com.flow.assignment.web.dto.ExtensionResponseDto;
import com.flow.assignment.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ExtensionServiceImpl implements ExtensionService {

    private final ExtensionRepository extensionRepository;

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<ExtensionResponseDto>> getExtensionsList() {
        try {
            List<ExtensionResponseDto> responseDto = extensionRepository.findAll().stream().
                    map(ExtensionResponseDto::new).collect(Collectors.toList());
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS_EXTENSION_LIST_MESSAGE, responseDto);
        } catch (Exception error) {
            return ResponseDto.setFailed(ResponseMessage.SERVER_ERROR_MESSAGE);
        }
    }


    @Override
    public ResponseDto<String> deleteExtension(Long id) {
        try {
            Extension extension = extensionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            extensionRepository.delete(extension);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS_DELETED_MESSAGE, String.valueOf(id));
        } catch (Exception error) {
            return ResponseDto.setFailed(ResponseMessage.SERVER_ERROR_MESSAGE);
        }

    }

    @Override
    public ResponseDto<String> checkedExtension(Long id, Boolean isChecked) {
        try {
            Extension extension = extensionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            extension.checkedExtension(isChecked);
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS_CHECKED_MESSAGE, String.valueOf(id));
        } catch (Exception error) {
            return ResponseDto.setFailed(ResponseMessage.SERVER_ERROR_MESSAGE);
        }
    }

    @Override
    public ResponseDto<String> saveExtension(ExtensionRequestDto requestDto) {
        try {
            int MAX_SIZE = 200;
            if (extensionRepository.countAllExtensions() >= MAX_SIZE) {
                return ResponseDto.setFailed("추가 확장자는 200개 까지 저장 가능합니다.");
            }
            if (extensionRepository.existsByName(requestDto.getName())) {
                return ResponseDto.setFailed("이미 존재하는 확장자입니다.");
            }
            return ResponseDto.setSuccess(ResponseMessage.SUCCESS_SAVE_MESSAGE,
                    String.valueOf(extensionRepository.save(requestDto.toExtension()).getId()));
        } catch (Exception error) {
            return ResponseDto.setFailed(ResponseMessage.SERVER_ERROR_MESSAGE);
        }
    }

    @Override
    public ResponseDto<String> uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileExtension = getFileExtension(originalFilename);
        try {
            if (extensionRepository.existsByName(fileExtension)) return ResponseDto.setFailed("Blocked file extension: " + fileExtension);
            return ResponseDto.setSuccess("File uploaded successfully", originalFilename);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.SERVER_ERROR_MESSAGE);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }

}