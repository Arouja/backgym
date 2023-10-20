package com.capgemini.gymapp.services.impl;

import com.capgemini.gymapp.Repositories.FitnessMetricsRepository;
import com.capgemini.gymapp.Repositories.UserRepository;
import com.capgemini.gymapp.entities.FitnessMetrics;
import com.capgemini.gymapp.entities.User;
import com.capgemini.gymapp.services.interfaces.IFitnessMetricsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FitnessMetricsService implements IFitnessMetricsService {

    @Autowired
    private FitnessMetricsRepository fitnessMetricsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public double calculateBMI(double height, double weight) {

        double bmi = weight / (height * height);

        return bmi;
    }

    @Override
    public double calculateLBM(double weight, double bodyFatPercentage) {

        double lbm = weight * (1 - (bodyFatPercentage / 100));

        return lbm;
    }

    @Override
    public double calculateORM(double weight, int reps) {
        double orm = weight * (1 + (0.0333 * reps));
        return orm;
    }

    @Override
    public FitnessMetrics calculateMetrics(double height, double weight, double bodyFatPercentage, int reps) {

        double bmi = weight / (height * height);
        double lbm = weight * (1 - (bodyFatPercentage / 100));
        double orm = weight * (1 + (0.0333 * reps));

        FitnessMetrics metrics = new FitnessMetrics(height, weight, bmi, lbm, orm);
        return fitnessMetricsRepository.save(metrics);
    }

    @Override

    public void assignMetrics(double height, double weight, double bodyFatPercentage, int reps, Integer userId) {
        FitnessMetrics fitnessMetrics = this.calculateMetrics(height, weight,  bodyFatPercentage, reps);
        Optional<User> optionalClient = userRepository.findById(userId);

        if (optionalClient.isPresent()) {

            User client = optionalClient.get();
            List<FitnessMetrics> metrics = client.getMetrics();
            metrics.add(fitnessMetrics);
            client.setMetrics(metrics);
            userRepository.save(client);
        } else {
            throw new EntityNotFoundException("FitnesMetrics or client not found");
        }
    }
}

