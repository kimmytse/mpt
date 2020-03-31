package com.ruijing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kimmy
 */
@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
