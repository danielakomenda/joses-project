package ch.zhaw.referee.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import ch.qos.logback.classic.Level;
import ch.zhaw.referee.model.Training;
import ch.zhaw.referee.model.TrainingStateAggregation;
import ch.zhaw.referee.model.TrainingType;

public interface TrainingRepository extends MongoRepository<Training, String> {
    Page<Training> findByminLevelGreaterThan(Level minLevel, Pageable pageable);

    Page<Training> findByTrainingType(TrainingType trainingType, Pageable pageable);

    Page<Training> findByTrainingTypeAndMinLevelGreaterThan(TrainingType type, Level minLevel, Pageable pageable);


    @Aggregation("{$group:{_id:'$trainingState',trainingIds:{$push:'$_id',},count:{$count:{}}}}")
    List<TrainingStateAggregation> getTrainingStateAggregation();
}