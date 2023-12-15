package ch.zhaw.referee.model;

import java.util.ArrayList;
import java.util.List;

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

    
    private ArrayList<Training> trainings = new ArrayList<>();


    public void addTraining(Training training){
        trainings.add(training);
    }

    public int getNumberOfTrainings() {
        return trainings.size();
    }
}
