package ch.zhaw.referee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Level;
import ch.zhaw.referee.model.Training;
import ch.zhaw.referee.model.TrainingCreateDTO;
import ch.zhaw.referee.model.TrainingStateAggregation;
import ch.zhaw.referee.model.TrainingType;
import ch.zhaw.referee.repository.TrainingRepository;

@RestController
@RequestMapping("/api")
public class TrainingController {

    @Autowired
    TrainingRepository trainingRepository;

    @PostMapping("/training")
    public ResponseEntity<Training> createTraining(@RequestBody TrainingCreateDTO cDTO) {
        Training jDAO = new Training(cDTO.getTrainingType(), cDTO.getDescription(), cDTO.getLocation(), cDTO.getDate(),
                cDTO.getMinLevel(), cDTO.getMinParticipants());
        Training j = trainingRepository.save(jDAO);
        return new ResponseEntity<>(j, HttpStatus.CREATED);
    }

    @GetMapping("/training")
    public ResponseEntity<Page<Training>> getAllTrainings(
            @RequestParam(required = false) Level minLevel,
            @RequestParam(required = false) TrainingType trainingsType,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "2") Integer pageSize) {

        Page<Training> trainings;
        if (minLevel == null && trainingsType != null) {
            trainings = trainingRepository.findByTrainingType(trainingsType, PageRequest.of(pageNumber - 1, pageSize));
        } else if (minLevel != null && trainingsType == null) {
            trainings = trainingRepository.findByminLevelGreaterThan(minLevel, PageRequest.of(pageNumber - 1, pageSize));
        } else if (minLevel != null && trainingsType != null) {
            trainings = trainingRepository.findByTrainingTypeAndMinLevelGreaterThan(trainingsType, minLevel,
                    PageRequest.of(pageNumber - 1, pageSize));
        } else {
            trainings = trainingRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        }
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/training/id/{id}")
    public ResponseEntity<Training> getTrainingById(@PathVariable String id) {
        Optional<Training> training = trainingRepository.findById(id);
        if (training.isPresent()) {
            return new ResponseEntity<Training>(training.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<Training>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/training/aggregation/state")
    public List<TrainingStateAggregation> getJobStateAggregation() {
        return trainingRepository.getTrainingStateAggregation();
    }
}
