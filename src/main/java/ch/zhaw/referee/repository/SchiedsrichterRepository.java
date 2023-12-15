package ch.zhaw.referee.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.referee.model.Schiedsrichter;

public interface SchiedsrichterRepository extends MongoRepository<Schiedsrichter, String> {
    Schiedsrichter findFirstByEmail(String email);
}