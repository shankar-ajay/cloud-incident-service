package com.ajay.incident_service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chaos")
public class ChaosController {

    @PostMapping("/crash")
    public void crash() {
        new Thread(() -> {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
            Runtime.getRuntime().halt(137);
        }).start();
    }
}