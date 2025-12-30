package com.kopa.controller;

import com.kopa.model.Prediction;
import com.kopa.model.Result;
import com.kopa.model.User;
import com.kopa.repository.PredictionRepository;
import com.kopa.repository.ResultRepository;
import com.kopa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "https://bettingtips-gamma.vercel.app")
public class AdminController {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserRepository userRepository;

    /* ================= PREDICTIONS ================= */

    @PostMapping("/predictions")
    public Prediction addPrediction(@RequestBody Prediction prediction) {
        return predictionRepository.save(prediction);
    }

    @PutMapping("/predictions/{id}")
    public Prediction updatePrediction(@PathVariable Long id,
                                       @RequestBody Prediction updated) {
        return predictionRepository.findById(id).map(pred -> {
            pred.setMatch(updated.getMatch());
            pred.setLeague(updated.getLeague());
            pred.setMatchDate(updated.getMatchDate());
            pred.setMatchTime(updated.getMatchTime());
            pred.setPrediction(updated.getPrediction());
            pred.setOdds(updated.getOdds());
            pred.setVip(updated.isVip());
            pred.setActive(updated.isActive());
            return predictionRepository.save(pred);
        }).orElseThrow(() -> new RuntimeException("Prediction not found"));
    }

    @DeleteMapping("/predictions/{id}")
    public void deletePrediction(@PathVariable Long id) {
        predictionRepository.deleteById(id);
    }

    @PatchMapping("/predictions/{id}/deactivate")
    public Prediction deactivate(@PathVariable Long id) {
        return predictionRepository.findById(id).map(p -> {
            p.setActive(false);
            return predictionRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Prediction not found"));
    }

    @GetMapping("/predictions")
    public List<Prediction> allPredictions() {
        return predictionRepository.findAll();
    }

    /* ================= RESULTS ================= */

    @PostMapping("/results")
    public Result addOrUpdateResult(@RequestBody Result result) {

        Prediction pred = predictionRepository
                .findByMatch(result.getMatch())
                .orElse(null);

        return resultRepository.findByMatch(result.getMatch()).map(r -> {
            r.setResult(result.getResult());
            if (pred != null) {
                r.setPick(pred.getPrediction());
                r.setOdds(pred.getOdds());
            }
            return resultRepository.save(r);
        }).orElseGet(() -> {
            if (pred != null) {
                result.setPick(pred.getPrediction());
                result.setOdds(pred.getOdds());
            }
            return resultRepository.save(result);
        });
    }

    @GetMapping("/results")
    public List<Result> allResults() {
        return resultRepository.findAll();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/leagues")
    public List<String> getLeagues() {
        return predictionRepository.findDistinctLeagues();
    }
}
