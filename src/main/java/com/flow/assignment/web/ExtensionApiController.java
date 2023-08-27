package com.flow.assignment.web;

import com.flow.assignment.service.ExtensionService;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import com.flow.assignment.web.dto.ExtensionResponseDto;
import com.flow.assignment.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/extension/v1/extensions")
@RestController
public class ExtensionApiController {

    private final ExtensionService extensionService;

    @GetMapping
    public ResponseDto<List<ExtensionResponseDto>> getExtensions() {
            return extensionService.getExtensionsList();
    }
    @DeleteMapping("{id}")
    public ResponseDto<String> deleteExtension(@PathVariable Long id) {
        return extensionService.deleteExtension(id);
    }

    @PutMapping("/check/{id}")
    public ResponseDto<String> checkExtension(@PathVariable Long id, @RequestBody Boolean isChecked) {

        return extensionService.checkedExtension(id, isChecked);
    }

    @PostMapping()
    public ResponseDto<String> saveExtension(@RequestBody ExtensionRequestDto extensionRequestDto) {
            return extensionService.saveExtension(extensionRequestDto);
    }


}
