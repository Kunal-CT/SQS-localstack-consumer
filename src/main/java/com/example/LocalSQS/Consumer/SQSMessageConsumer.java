package com.example.LocalSQS.Consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Service
public class SQSMessageConsumer {

    private static final String QUEUE_URL = "http://localhost:4566/000000000000/localstack-queue"; // Queue URL

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

            // Simulate message processing (e.g., store in DB, etc.)
            // After processing, delete the message from the queue to avoid reprocessing
            amazonSQS.deleteMessage(QUEUE_URL, message.getReceiptHandle());
            System.out.println("Message processed and deleted.");
        }
    }
}
