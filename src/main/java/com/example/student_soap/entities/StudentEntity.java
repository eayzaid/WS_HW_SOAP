package com.example.student_soap.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "student")
public class StudentEntity {

    // Standard Getters and Setters required for Hibernate proxying
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;

    public StudentEntity() {}

    public StudentEntity(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

}