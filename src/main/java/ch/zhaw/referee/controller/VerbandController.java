package ch.zhaw.referee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.referee.model.MailInformation;
import ch.zhaw.referee.model.Verband;
import ch.zhaw.referee.model.VerbandCreateDTO;
import ch.zhaw.referee.repository.VerbandRepository;
import ch.zhaw.referee.service.MailValidatorService;

@RestController
@RequestMapping("/api")

public class VerbandController {
    @Autowired
    VerbandRepository verbandRepository;

    @Autowired
    MailValidatorService mailValidatorService;

    @PostMapping("/verband")
    public ResponseEntity<Verband> createSchiedsrichter(@RequestBody VerbandCreateDTO fDTO) {
        // Check, if Email is invalid
        MailInformation mailInformation = mailValidatorService.validateEmail(fDTO.getEmail());
        if (mailInformation.isDisposable() || !mailInformation.isDns() || !mailInformation.isFormat()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Check, if Email is already in Database
        Verband schiedsrichter = verbandRepository.findFirstByEmail(fDTO.getEmail());
        if (schiedsrichter != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // If email is valid and unique, a new Schiedsrichter is created
        Verband fDAO = new Verband(fDTO.getRegion(), fDTO.getEmail());
        Verband f = verbandRepository.save(fDAO);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }
}