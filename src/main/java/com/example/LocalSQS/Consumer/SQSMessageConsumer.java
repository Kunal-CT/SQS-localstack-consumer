package com.example.LocalSQS.Consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.LocalSQS.dto.Superhero;
import com.example.LocalSQS.repos.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

@Service
public class SQSMessageConsumer {

    private static final String QUEUE_URL = "http://localhost:4566sta/000000000000/localstack-queue"; // Queue URL

    private final SuperheroRepository superheroRepository;

    public SQSMessageConsumer(SuperheroRepository superheroRepository){
        this.superheroRepository = superheroRepository;
    }

    @Autowired
    private AmazonSQS amazonSQS;

    /**
     * Method to poll the queue and receive messages.
     * This will be triggered periodically.
     */
    @Scheduled(fixedRate = 5000)  // Poll every 5 seconds
    public void pollMessages() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMaxNumberOfMessages(10)   // Maximum number of messages to receive
                .withWaitTimeSeconds(10);      // Long polling for 10 seconds

        // Receive the messages from SQS
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

        if (messages.isEmpty()) {
            System.out.println("No messages available at the moment.");
            return;
        }

        // Process each message
        for (Message message : messages) {
            System.out.println("Received message: " + message.getBody());
            String superheroName = message.getBody();
            Optional<Superhero> superheroOptional = superheroRepository.findByName(superheroName);
            if (superheroOptional.isPresent()) {
                System.out.println("Superhero found: " + message.getBody());
                Superhero superhero = superheroOptional.get();
                superhero.setName(superheroName);
                superhero.setPower("I am badass");
                superhero.setUniverse("Tollywood");
                superheroRepository.save(superhero);
            } else {
                throw new RuntimeException("Superhero not found");
            }
            amazonSQS.deleteMessage(QUEUE_URL, message.getReceiptHandle());
            System.out.println("Message processed and deleted.");
        }
    }
}
