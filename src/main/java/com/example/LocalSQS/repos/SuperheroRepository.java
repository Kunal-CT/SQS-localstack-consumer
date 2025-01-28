package com.example.LocalSQS.repos;

import com.example.LocalSQS.dto.Superhero;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuperheroRepository extends MongoRepository<Superhero, String> {
//    void deleteByName(String name);
//
//    Optional<Superhero> findByName(String name);
}
