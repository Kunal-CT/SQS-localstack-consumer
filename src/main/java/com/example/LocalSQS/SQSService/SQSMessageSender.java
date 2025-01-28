package com.example.LocalSQS.SQSService;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQSMessageSender {

    @Autowired
    private AmazonSQS amazonSQS;

    // URL of your LocalStack SQS queue
    private static final String QUEUE_URL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/localstack-queue";

    /**
     * Method to send a message to the SQS queue.
     */
    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(message);

        // Sending message
        amazonSQS.sendMessage(sendMessageRequest);
        System.out.println("Message Sent: " + message);
    }

}
