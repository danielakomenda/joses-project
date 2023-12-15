package ch.zhaw.referee.model;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TrainingCreateDTO {
    private TrainingType trainingType;
    private String description;
    private String location;
    private Date date; 
    private int minLevel; 
    private int minParticipants; 
}