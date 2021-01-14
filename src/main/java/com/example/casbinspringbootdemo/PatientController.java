package com.example.casbinspringbootdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<Patient> patientByRecordId(@PathVariable("recordId") String recordId) {
        Optional<Patient> patient = patientRepository.findByRecordId(recordId);
        System.out.println("Found: " + patient);
        return ResponseEntity.of(patient);
    }

    @PostMapping("/{recordId}/{name}")
    public void newPatient(@PathVariable("recordId") String recordId, @PathVariable("name") String name) {
        patientRepository.store(new Patient(name, recordId));
    }

    @PutMapping("/{recordId}/{name}")
    public void updateName(@PathVariable("recordId") String recordId, @PathVariable("name") String name) {
        patientRepository.findByRecordId(recordId).get().updateName(name);
    }

    @PostMapping("/{recordId}/labs/{lab}/{result}")
    public List<String> recordLab(@PathVariable("recordId") String recordId, @PathVariable("lab") String lab, @PathVariable("result") String result) {
        Assert.hasText(recordId, "invalid null/blank record id");
        Assert.hasText(lab, "invalid null/blank lab name");
        Assert.hasText(result, "invalid null/blank result value");
        Optional<Patient> patient = patientRepository.findByRecordId(recordId);
        return patient.get().getPatientHistory().recordLab(lab, result).getLabResults(lab);
    }

    @GetMapping
    public ResponseEntity<Collection<String>> allPatientRecordIds() {
        return ResponseEntity.ok(patientRepository.getAllIds());
    }
}
