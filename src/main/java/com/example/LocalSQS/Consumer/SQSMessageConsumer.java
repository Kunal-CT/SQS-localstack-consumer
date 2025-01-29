package com.example.LocalSQS.Consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.example.LocalSQS.dto.Superhero;
import com.example.LocalSQS.repos.SuperheroRepository;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SQSMessageConsumer {

    private static final String QUEUE_NAME = "localstack-queue";

    private final SuperheroRepository superheroRepository;

    @Autowired
    public SQSMessageConsumer(SuperheroRepository superheroRepository) {
        this.superheroRepository = superheroRepository;
    }

    @SqsListener(QUEUE_NAME)  // Automatically listens for messages
    public void processMessage(String superheroName) {
        System.out.println("Received message: " + superheroName);

        Optional<Superhero> superheroOptional = superheroRepository.findByName(superheroName);
        if (superheroOptional.isPresent()) {
            Superhero superhero = superheroOptional.get();
            superhero.setPower("Updated power : { " + superhero.getPower() + " }");
            superhero.setUniverse("Updated Universe : { " + superhero.getUniverse() + " }");
            superheroRepository.save(superhero);
            System.out.println("Superhero updated successfully.");
        } else {
            System.err.println("Superhero not found.");
        }
    }
}
