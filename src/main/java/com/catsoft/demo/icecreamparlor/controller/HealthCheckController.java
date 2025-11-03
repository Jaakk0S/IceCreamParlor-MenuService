package com.catsoft.demo.icecreamparlor.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/admin/v1")
public class HealthCheckController {

    @GetMapping("/healthcheck")
    void getHealthCheck() {}
}
