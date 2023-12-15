package ch.zhaw.referee.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document("schiedsrichter")
public class Schiedsrichter {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private int level;

    private ArrayList<TrainingToSchiedsrichterList> trainings = new ArrayList<>();

    public void addTraining(TrainingToSchiedsrichterList training) {
        trainings.add(training);
    }
}
