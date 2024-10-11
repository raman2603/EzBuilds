package com.example.EasyBuildApp.controller;

import com.example.EasyBuildApp.service.ChatGptService; // Make sure to import your service class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private ChatGptService chatGptService; // Injecting the ChatGptService

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/process_questions") // This will handle the form submission
    public String processQuestions(@RequestParam String question, RedirectAttributes redirectAttributes) {
        String yamlScript = chatGptService.getYamlScript(question); // Call the service to get the YAML script
        redirectAttributes.addFlashAttribute("message", "Generated YAML Script: " + yamlScript); // Add the message to flash attributes
        return "redirect:/"; // Redirect back to home after processing
    }
}
