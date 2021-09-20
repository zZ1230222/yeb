package com.code.server.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/helloOne")
    public String helloOne(){
        return "helloOne";
    }

    @GetMapping("/employee/basic/helloTwo")
    public String helloTwo(){
        return "/employee/basic/helloTwo";
    }

    @GetMapping("/employee/advanced/helloThree")
    public String helloThree(){
        return "/employee/advanced/helloThree";
    }
}
