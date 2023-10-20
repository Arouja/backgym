package com.capgemini.gymapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FitnessMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double height;
    private double weight;
    private double bmi;
    private double lbm;
    private double orm;

    public FitnessMetrics(int i, double weight, int i1, double lbm, int i2) {
    }

    public FitnessMetrics(double height, double weight, double bmi, int lbm, int i2) {
    }

    public FitnessMetrics(int i, double weight, int i1, int i2, double orm) {

    }

    public FitnessMetrics(double height, double weight, double bmi, double lbm, double orm) {
    }
}
