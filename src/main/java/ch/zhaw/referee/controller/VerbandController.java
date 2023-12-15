package ch.zhaw.referee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.referee.repository.VerbandRepository;

@RestController
@RequestMapping("/api")

public class VerbandController {
    @Autowired
    VerbandRepository verbandRepository;
    
}