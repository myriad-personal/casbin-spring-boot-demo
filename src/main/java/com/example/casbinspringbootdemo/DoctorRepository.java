package com.example.casbinspringbootdemo;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class DoctorRepository {
    private static final Map<String, Doctor> doctors = new HashMap<>();

    Optional<Doctor> findByName(String name) {
        Assert.hasText(name, "invalid null/blank name");
        return Optional.ofNullable(doctors.get(name.trim()));
    }

    void save(Doctor doctor) {
        Assert.state(!doctors.containsKey(doctor.getName()), "Doctor already stored");
        doctors.put(doctor.getName(), doctor);
    }
}
