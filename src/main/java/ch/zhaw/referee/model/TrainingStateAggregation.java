package ch.zhaw.referee.model;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TrainingStateAggregation {
    private String id;
    private List<String> trainingIds;
    private String count;
}