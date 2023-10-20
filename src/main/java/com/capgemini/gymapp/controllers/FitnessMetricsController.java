package com.capgemini.gymapp.controllers;

import com.capgemini.gymapp.entities.FitnessMetrics;
import com.capgemini.gymapp.services.interfaces.IFitnessMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/fitness-metrics")
public class FitnessMetricsController {

    @Autowired
    private IFitnessMetricsService fitnessMetricsService;

    @PostMapping("/calculate-bmi")
    public ResponseEntity<Double> calculateBmi(@RequestBody Map<String, Object> request) {
        try {
            double height = Double.parseDouble(request.get("height").toString());
            double weight = Double.parseDouble(request.get("weight").toString());
            double bmi = fitnessMetricsService.calculateBMI(height, weight);
            return ResponseEntity.ok(bmi);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//weight, bodyFatPercentage
    @PostMapping("/calculate-lbm")
    public ResponseEntity<Double> calculateLbm(@RequestBody Map<String, Object> request) {
        try {
            double bodyFatPercentage = Double.parseDouble(request.get("bodyFatPercentage").toString());
            double weight = Double.parseDouble(request.get("weight").toString());
            double lbm = fitnessMetricsService.calculateLBM(weight, bodyFatPercentage);
            return ResponseEntity.ok(lbm);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/calculate-orm")
    public ResponseEntity<Double> calculateORM(@RequestBody Map<String, Object> request) {
        try {
            double weight = Double.parseDouble(request.get("weight").toString());
            int reps = Integer.parseInt(request.get("reps").toString());
            double orm = fitnessMetricsService.calculateORM(weight, reps);
            return ResponseEntity.ok(orm);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/assign-metrics")
    public void assignMetrics(
            @RequestParam double height,
            @RequestParam double weight,
            @RequestParam double bodyFatPercentage,
            @RequestParam int reps,
            @RequestParam Integer userId
    ) {
        fitnessMetricsService.assignMetrics(height, weight, bodyFatPercentage, reps, userId);
    }

}
