package com.example.casbinspringbootdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<Patient> patientByRecordId(@PathVariable("recordId") String recordId) {
        Assert.hasText(recordId, "invalid null/blank record id");
        return ResponseEntity.of(patientRepository.findByRecordId(recordId.trim()));
    }

    @PostMapping("/{recordId}/{name}")
    public void newPatient(@PathVariable("recordId") String recordId, @PathVariable("name") String name) {
        Assert.hasText(recordId, "invalid null/blank record id");
        Assert.hasText(name, "invalid null/blank lab name");
        patientRepository.store(new Patient(name.trim(), recordId.trim()));
    }

    @PutMapping("/{recordId}/{name}")
    public void updateName(@PathVariable("recordId") String recordId, @PathVariable("name") String name) {
        Assert.hasText(recordId, "invalid null/blank record id");
        Assert.hasText(name, "invalid null/blank lab name");
        patientRepository.findByRecordId(recordId.trim()).get().updateName(name.trim());
    }

    @PostMapping("/{recordId}/labs/{lab}/{result}")
    public List<String> recordLab(@PathVariable("recordId") String recordId, @PathVariable("lab") String lab, @PathVariable("result") String result) {
        Assert.hasText(recordId, "invalid null/blank record id");
        Assert.hasText(lab, "invalid null/blank lab name");
        Assert.hasText(result, "invalid null/blank result value");
        return patientRepository.findByRecordId(recordId.trim()).get().recordLab(lab.trim(), result.trim());
    }

    @GetMapping
    public ResponseEntity<Collection<String>> allPatientRecordIds() {
        return ResponseEntity.ok(patientRepository.getAllIds());
    }
}
