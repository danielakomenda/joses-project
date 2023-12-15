package ch.zhaw.referee.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.referee.model.Verband;

public interface VerbandRepository extends MongoRepository<Verband, String> {
    Optional<Verband> findById(String id);
}