package ch.zhaw.referee.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import ch.zhaw.referee.repository.SchiedsrichterRepository;
import ch.zhaw.referee.repository.TrainingRepository;
import ch.zhaw.referee.repository.VerbandRepository;
import ch.zhaw.referee.model.Mail;
import ch.zhaw.referee.model.Schiedsrichter;
import ch.zhaw.referee.model.SchiedsrichterToTrainingList;
import ch.zhaw.referee.model.Training;
import ch.zhaw.referee.model.TrainingToSchiedsrichterList;
import ch.zhaw.referee.model.TrainingState;
import ch.zhaw.referee.model.Verband;

@Service
public class TrainingService {

    private static final Mail mail = new Mail();
    MailService mailService = new MailService();
    
    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    SchiedsrichterRepository schiedsrichterRepository;
    @Autowired
    VerbandRepository verbandRepository;



    // Nur der Verband kan Trainings abschliessen
    // Im Frontend muss die VerbandId dafür übergeben werden
    public Optional<Training> completeTraining(String trainingId, String verbandId) {
        Optional<Training> trainingToAssign = trainingRepository.findById(trainingId);
        if (trainingToAssign.isPresent()) {
            Training training = trainingToAssign.get();
            if (training.getTrainingState() == TrainingState.DEFINITIV) {
                Optional<Verband> verband = verbandRepository.findById(verbandId);
                if (verband.isPresent()) {
                    if (training.getVerbandId().equals(verband.get().getId())) {
                        training.setTrainingState(TrainingState.ABGESCHLOSSEN);
                        trainingRepository.save(training);
                        return Optional.of(training);
                    }
                }
            }
        }
        return Optional.empty();
    }



    // Teilt ein Schiedsrichter einem Training zu
    // Wird die Mindest-Teilnehmerzahl erreicht oder überschritten, wird das
    // Training definitiv durchgeführt
    public Optional<Training> assigneMeToTraining(String trainingId, String SchiedsrichterEmail) {
        Optional<Training> trainingToAssign = trainingRepository.findById(trainingId);
        Schiedsrichter schiedsrichter = schiedsrichterRepository.findFirstByEmail(SchiedsrichterEmail);
        
        if (trainingToAssign.isPresent()) {
            Training training = trainingToAssign.get();

            if (training.getTrainingState() == TrainingState.NEU
                    || training.getTrainingState() == TrainingState.DEFINITIV) {

                if (schiedsrichter != null) {

                    SchiedsrichterToTrainingList schiriToAdd = new SchiedsrichterToTrainingList(schiedsrichter.getId(), schiedsrichter.getName(), schiedsrichter.getEmail(), schiedsrichter.getLevel());
                    TrainingToSchiedsrichterList trainingToAdd = new TrainingToSchiedsrichterList(training.getId(), training.getTrainingType(), training.getDescription(), training.getLocation(), training.getDate(), training.getMinLevel(), training.getVerbandId());

                    training.addParticipant(schiriToAdd);
                    schiedsrichter.addTraining(trainingToAdd);

                    if (training.getTrainingState() == TrainingState.NEU) {
                        sendConfirmationForRegistrationProv(training, SchiedsrichterEmail);
                    }

                    else if (training.getTrainingState() == TrainingState.DEFINITIV) {
                        sendConfirmationForRegistrationDef(training, SchiedsrichterEmail);
                    }

                    if (training.getParticipants().size()>= training.getMinParticipants()
                            && training.getTrainingState() == TrainingState.NEU) {
                        sendConfirmationMailToAll(training);
                        training.setTrainingState(TrainingState.DEFINITIV);
                    }

                    trainingRepository.save(training);
                    schiedsrichterRepository.save(schiedsrichter);
                    return Optional.of(training);
                }
            }
        }
        return Optional.empty();
    }


    // Sendet allen angemeldeten Benutzern eine Bestätigungsemail,
    // Falls sich der TrainingState auf DEFINITIV ändert
    public void sendConfirmationMailToAll(Training training) {
        ArrayList<SchiedsrichterToTrainingList> participants = training.getParticipants();
        
        for (SchiedsrichterToTrainingList teilnehmer : participants) {
            String subject = training.getDescription();
            String message = "Das Training " + subject + " findet definitiv statt. Vielen Dank für Deine Anmeldung. Wir freuen uns auf das Training.";
            mail.setSubject(subject);
            mail.setMessage(message);
            mail.setTo(teilnehmer.getEmail());
            try {
            mailService.sendMail(mail);
            } catch (Exception e) {
                System.out.println("Fehler beim Senden der Email.");
            }
        }
    }

    // Sendet neuen Nutzern eine Bestätigungsmail,
    // Falls der Trainingsstate noch NEU ist
    public void sendConfirmationForRegistrationProv(Training training, String email) {
        String subject = training.getDescription();
        String message = "Du hast dich für das Training " + subject + " angemeldet. Sobald sich genügend Teilnehmer angemeldet haben, wirst Du eine Bestätigungs-Email erhalten. Wir freuen uns über Dein Interesse.";
        mail.setSubject(subject);
        mail.setMessage(message);
        mail.setTo(email);
        try {
            mailService.sendMail(mail);
        } catch (Exception e) {
            System.out.println("Fehler beim Senden der Email.");
        }
    }

    // Sendet neuen Nutzern eine Bestätigungemail,
    // Falls der TrainingState bereits DEFINITIV ist (beim Wechsel waren diese
    // Mitglieder noch nicht im Verteiler)
    public void sendConfirmationForRegistrationDef(Training training, String email) {
        String subject = training.getDescription();
        String message = "Du hast dich für das Trainig " + subject
                + " angemeldet. Das Training findet definitiv statt. Wir freuen uns über Dein Interesse.";
        mail.setSubject(subject);
        mail.setMessage(message);
        mail.setTo(email);
        try {
            mailService.sendMail(mail);
        } catch (Exception e) {
            System.out.println("Fehler beim Senden der Email.");
        }
    }
}
