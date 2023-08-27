package com.flow.assignment.web;

import com.flow.assignment.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ExtensionService extensionService;
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("extensions", extensionService.getExtensionsList().getData());
        return "index";
    }
}
