package com.example.LocalSQS.Controller;

//import com.example.demo.service.SQSService;
import com.example.LocalSQS.SQSService.SQSMessageReceiver;
import com.example.LocalSQS.SQSService.SQSMessageSender;
import com.example.LocalSQS.SQSService.SQSMessageSender.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SQSController {

    @Autowired
    private SQSMessageSender sqsService;

    @Autowired
    private SQSMessageReceiver sqsMessageReceiver;

    @GetMapping("/sendMessage")
    public String sendMessage() {
        sqsService.sendMessage("Superman");
        return "Message sent!";
    }

    @GetMapping("/receiveMessages")
    public String receiveMessages() {
        sqsMessageReceiver.receiveMessages();
        return "Messages received!";
    }
}

