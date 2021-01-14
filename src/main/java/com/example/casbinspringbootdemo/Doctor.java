package com.example.casbinspringbootdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.Assert;

import java.util.Objects;

@JsonSerialize(as = Doctor.class)
public class Doctor implements Profile {
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String name;

    public Doctor(String name, String title) {
        Assert.hasText(name, "invalid null/blank name");
        Assert.hasText(title, "invalid null/blank title");
        this.name = name.trim();
        this.title = title.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return name.equals(doctor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
