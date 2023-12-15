package ch.zhaw.referee.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Document("training")
public class Training {
     @Id
    private String id;
    @NonNull
    private TrainingType trainingType;
    @NonNull
    private String description;
    @NonNull
    private String location;
    @NonNull
    private Date date; 
    @NonNull
    private int minLevel;
    @NonNull
    private int minParticipants;

    private String verbandId;


    private TrainingState trainingState = TrainingState.NEU;

    private ArrayList<Schiedsrichter> participants = new ArrayList<>();

    public void addParticipant(Schiedsrichter schiedsrichter){
        participants.add(schiedsrichter);
    }
}
