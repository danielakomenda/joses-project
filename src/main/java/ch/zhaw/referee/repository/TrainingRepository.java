package ch.zhaw.referee.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import ch.qos.logback.classic.Level;
import ch.zhaw.referee.model.Training;
import ch.zhaw.referee.model.TrainingStateAggregation;
import ch.zhaw.referee.model.TrainingType;

public interface TrainingRepository extends MongoRepository<Training, String> {
    List<Training> findByminLevelGreaterThan(Level minLevel);
    List<Training> findByTrainingType(TrainingType trainingType);

    @Aggregation("{$group:{_id:'$trainingState',trainingIds:{$push:'$_id',},count:{$count:{}}}}")
    List<TrainingStateAggregation> getTrainingStateAggregation();
}