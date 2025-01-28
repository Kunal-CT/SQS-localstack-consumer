package com.example.LocalSQS.SQSService;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SQSMessageReceiver {

    @Autowired
    private AmazonSQS amazonSQS;

    // URL of your LocalStack SQS queue
    private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/localstack-queue";

    /**
     * Method to receive messages from the SQS queue.
     */
    public void receiveMessages() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMaxNumberOfMessages(10) // Max number of messages to receive
                .withWaitTimeSeconds(10); // Long polling for 10 seconds

        // Receiving messages
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

        if (messages.isEmpty()) {
            System.out.println("No messages available.");
            return;
        }

        for (Message message : messages) {
            System.out.println("Received Message: " + message.getBody());
            // Optionally, delete the message after processing it
            amazonSQS.deleteMessage(QUEUE_URL, message.getReceiptHandle());
        }
    }
}

