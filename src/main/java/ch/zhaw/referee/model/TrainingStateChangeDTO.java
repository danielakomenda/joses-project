package ch.zhaw.referee.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainingStateChangeDTO {
    private String trainingId;
    private String schiedsrichterEmail;
    private String verbandId;
}
