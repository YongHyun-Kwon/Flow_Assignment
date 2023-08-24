package com.flow.assignment.web;

import com.flow.assignment.service.ExtensionService;
import com.flow.assignment.web.dto.ExtensionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ExtensionApiController {

    private final ExtensionService extensionService;

    @PostMapping("/add-extension")
    public Long saveExtension(@RequestBody ExtensionRequestDto extensionRequestDto) {
        return extensionService.saveExtension(extensionRequestDto);
    }

}
