package com.example.casbinspringbootdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

@JsonSerialize(as = Patient.class)
public class Patient implements Profile {
    @JsonProperty
    private final String recordId;
    @JsonProperty
    private final PatientHistory patientHistory;
    @JsonProperty
    private String name;

    public Patient(String name, String recordId) {
        Assert.hasText(name, "invalid null/blank name");
        Assert.hasText(recordId, "invalid null/blank recordId");
        this.name = name.trim();
        this.recordId = recordId.trim();
        this.patientHistory = new PatientHistory();
    }

    void updateName(String name) {
        Assert.hasText(name, "invalid null/blank name");
        this.name = name.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRecordId() {
        return recordId;
    }

    public PatientHistory getPatientHistory() {
        return patientHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", recordId='" + recordId + '\'' +
                ", patientHistory=" + patientHistory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return recordId.equals(patient.recordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId);
    }

    public List<String> recordLab(String lab, String result) {
        return getPatientHistory().recordLab(lab, result).getLabResults(lab);
    }
}
