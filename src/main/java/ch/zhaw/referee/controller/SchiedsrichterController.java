package ch.zhaw.referee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.zhaw.referee.model.MailInformation;
import ch.zhaw.referee.model.Schiedsrichter;
import ch.zhaw.referee.model.SchiedsrichterCreateDTO;
import ch.zhaw.referee.repository.SchiedsrichterRepository;
import ch.zhaw.referee.repository.TrainingRepository;
import ch.zhaw.referee.service.MailValidatorService;

@RestController
@RequestMapping("/api")

public class SchiedsrichterController {

    @Autowired
    SchiedsrichterRepository schiedsrichterRepository;
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    MailValidatorService mailValidatorService;

    @PostMapping("/schiedsrichter")
    public ResponseEntity<Schiedsrichter> createSchiedsrichter(@RequestBody SchiedsrichterCreateDTO fDTO) {
        // Check, if Email is invalid
        MailInformation mailInformation = mailValidatorService.validateEmail(fDTO.getEmail());
        if (mailInformation.isDisposable() || !mailInformation.isDns() || !mailInformation.isFormat()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Check, if Email is already in Database
        Schiedsrichter schiedsrichter = schiedsrichterRepository.findFirstByEmail(fDTO.getEmail());
        if (schiedsrichter != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // If email is valid and unique, a new Schiedsrichter is created
        Schiedsrichter fDAO = new Schiedsrichter(fDTO.getName(), fDTO.getEmail(), fDTO.getLevel());
        Schiedsrichter f = schiedsrichterRepository.save(fDAO);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }

    @GetMapping("/schiedsrichter")
    public ResponseEntity<List<Schiedsrichter>> getAllSchiedsrichter() {
        List<Schiedsrichter> allFree = schiedsrichterRepository.findAll();
        return new ResponseEntity<>(allFree, HttpStatus.OK);
    }

    @GetMapping("/schiedsrichter/id/{id}")
    public ResponseEntity<Schiedsrichter> getSchiedsrichterById(@PathVariable String id) {
        Optional<Schiedsrichter> optSchiedsrichter = schiedsrichterRepository.findById(id);
        if (optSchiedsrichter.isPresent()) {
            return new ResponseEntity<>(optSchiedsrichter.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}