package com.capgemini.gymapp.services.interfaces;

import com.capgemini.gymapp.entities.FitnessMetrics;

public interface IFitnessMetricsService {
    double calculateBMI(double height, double weight);

    double calculateLBM(double weight, double bodyFatPercentage);

    double calculateORM(double weight, int reps);

    FitnessMetrics calculateMetrics(double height, double weight, double bodyFatPercentage, int reps);

    void assignMetrics(double height, double weight, double bodyFatPercentage, int reps, Integer userId);
}
