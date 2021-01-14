package com.example.casbinspringbootdemo;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PatientRepository {

    private static final Map<String, Patient> patients = new HashMap<>();

    boolean hasRecordId(String recordId) {
        Assert.hasText(recordId, "invalid null/blank recordId");
        return patients.containsKey(recordId);
    }

    Optional<Patient> findByRecordId(String recordId) {
        Assert.hasText(recordId, "invalid null/blank recordId");
        return Optional.ofNullable(patients.get(recordId.trim()));
    }

    void store(Patient patient) {
        Assert.state(!patients.containsKey(patient.getName()), "Patient already stored");
        patients.put(patient.getRecordId(), patient);
    }

    public Collection<String> getAllIds() {
        return patients.keySet().stream().collect(Collectors.toList());
    }
}
