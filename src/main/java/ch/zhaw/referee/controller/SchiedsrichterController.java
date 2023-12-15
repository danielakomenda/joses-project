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

import ch.zhaw.referee.model.Schiedsrichter;
import ch.zhaw.referee.model.SchiedsrichterCreateDTO;
import ch.zhaw.referee.repository.SchiedsrichterRepository;
import ch.zhaw.referee.repository.TrainingRepository;

@RestController
@RequestMapping("/api")

public class SchiedsrichterController {

    @Autowired
    SchiedsrichterRepository schiedsrichterRepository;
    @Autowired
    TrainingRepository trainingRepository;

    @PostMapping("/schiedsrichter")
    public ResponseEntity<Schiedsrichter> createSchiedsrichter(@RequestBody SchiedsrichterCreateDTO fDTO) {
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