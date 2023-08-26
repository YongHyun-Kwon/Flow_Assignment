package com.flow.assignment.web;

import com.flow.assignment.service.ExtensionService;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import com.flow.assignment.web.dto.ExtensionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/extension/v1/extensions")
@RestController
public class ExtensionApiController {

    private final ExtensionService extensionService;

    @GetMapping
    public List<ExtensionResponseDto> getExtensions() {
        return extensionService.getExtensionsList();
    }

    @DeleteMapping("{id}")
    public Long deleteExtension(@PathVariable Long id) {
        extensionService.deleteExtension(id);
        return id;
    }

    @PutMapping("/check/{id}")
    public Long checkExtension(@PathVariable Long id, @RequestBody Boolean isChecked) {
        return extensionService.checkedExtension(id, isChecked);
    }

    @PostMapping()
    public Long saveExtension(@RequestBody ExtensionRequestDto extensionRequestDto) {
        return extensionService.saveExtension(extensionRequestDto);
    }

}
