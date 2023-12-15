package ch.zhaw.referee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SchiedsrichterToTrainingList {
    private String id;
    private String name;
    private String email;
    private int level;
}
