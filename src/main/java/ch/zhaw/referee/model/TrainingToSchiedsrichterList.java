package ch.zhaw.referee.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainingToSchiedsrichterList {
    private String id;
    private TrainingType trainingType;
    private String description;
    private String location;
    private Date date; 
    private int minLevel;
    private String verbandId;
}
