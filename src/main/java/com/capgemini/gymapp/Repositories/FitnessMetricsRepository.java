package com.capgemini.gymapp.Repositories;

import com.capgemini.gymapp.entities.FitnessMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessMetricsRepository extends JpaRepository<FitnessMetrics, Integer> {
}
