package com.example.casbinspringbootdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@JsonSerialize(as = PatientHistory.class)
public class PatientHistory {
    @JsonProperty
    private final LinkedHashMap<Lab, String> results;

    public PatientHistory() {
        results = new LinkedHashMap<>();
    }

    public PatientHistory recordLab(String labName, String result) {
        Lab lab = Lab.recordNow(labName);
        Assert.state(!results.containsKey(lab), "Already recorded the lab within the minute");
        results.put(lab, result);
        return this;
    }

    public List<String> getLabResults(String lab) {
        return results.entrySet().stream()
                .filter(entry -> entry.getKey().name.equals(lab))
                .map(entry -> String.format("%s -> %s", entry.getKey().date, entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "PatientHistory{results=" + results + '}';
    }

    @JsonSerialize(as = Lab.class)
    private static class Lab {
        @JsonProperty
        private final String name;
        @JsonProperty
        private final Date date;

        Lab(String name, Date date) {
            Assert.hasText(name, "invalid null/blank lab name");
            Assert.notNull(date, "invalid null lab date");
            this.name = name;
            this.date = date;
        }

        static final Lab recordNow(String lab) {
            return new Lab(lab, dateStamp());
        }

        static final Date dateStamp() {
            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        @Override
        public String toString() {
            return "Lab{" +
                    "name='" + name + '\'' +
                    ", date=" + date +
                    "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lab entry = (Lab) o;
            return name.equals(entry.name) && date.equals(entry.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, date);
        }
    }
}
