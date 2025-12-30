package com.kopa.repository;

import com.kopa.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByVip(boolean vip);

    Optional<Prediction> findByMatch(String match);

    @Query("SELECT DISTINCT p.league FROM Prediction p ORDER BY p.league")
    List<String> findDistinctLeagues();
}
