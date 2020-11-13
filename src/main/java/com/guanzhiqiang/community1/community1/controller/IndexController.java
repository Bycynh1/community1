package com.guanzhiqiang.community1.community1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author gzq
 * @date 2020/11/8
 **/

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
