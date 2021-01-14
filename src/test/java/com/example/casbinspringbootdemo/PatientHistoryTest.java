package com.example.casbinspringbootdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientHistoryTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void test() throws InterruptedException {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.recordLab("123", "pass");
        patientHistory.recordLab("abc", "fail");

        for (int i=0; i<10; i++) {
            Thread.sleep(1000);
            patientHistory.recordLab("123", "pass"+i);
        }

        System.out.println(patientHistory);
        System.out.println("Dates/results for lab.name 123:");
        System.out.println(patientHistory.getLabResults("123"));
    }
}