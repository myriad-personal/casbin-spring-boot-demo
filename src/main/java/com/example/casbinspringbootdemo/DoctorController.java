package com.example.casbinspringbootdemo;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private Enforcer enforcer;

    DoctorController(Enforcer enforcer) {
        this.enforcer = enforcer;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Doctor> profile(@PathVariable String name) {
        Assert.hasText(name, "invalid null/blank name");
        return ResponseEntity.ok(new Doctor(name, "Some Dum Doctor Title"));
    }
}
