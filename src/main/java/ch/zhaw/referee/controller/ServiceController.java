package ch.zhaw.referee.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.referee.model.Training;
import ch.zhaw.referee.model.TrainingStateChangeDTO;
import ch.zhaw.referee.service.TrainingService;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    TrainingService trainingService;


    @PutMapping("/assigntraining")
    public ResponseEntity<Training> assignTraining(@RequestBody TrainingStateChangeDTO changeS) {
        String schiedsrichterEmail = changeS.getSchiedsrichterEmail();
        String trainingId = changeS.getTrainingId();
        Optional<Training> training = trainingService.assigneMeToTraining(trainingId, schiedsrichterEmail);
        if (training.isPresent()) {
            return new ResponseEntity<>(training.get(), HttpStatus.OK); 
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/completetraining")
    public ResponseEntity<Training> completeTraining(@RequestBody TrainingStateChangeDTO changeS) {
        String verbandId = changeS.getVerbandId();
        String trainingId = changeS.getTrainingId();
        Optional<Training> training= trainingService.completeTraining(trainingId, verbandId);
        if (training.isPresent()) {
            return new ResponseEntity<>(training.get(), HttpStatus.OK); 
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
