package com.carato.carato_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemporalController {
    @GetMapping("/hello-world")
    public String showHelloWorld() {
        return "Hello world";
    }
}
