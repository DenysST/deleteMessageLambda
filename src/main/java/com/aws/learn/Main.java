package com.aws.learn;

import java.util.List;
public class Main {

    public void deleteExpiredMessages(int days) {
        MessageService service = new MessageService();
        List<Message> messages = service.getAll(days);
        if (messages.size() > 0 ) {
            service.deleteMessagesFromDb(messages);
            service.deleteMessageFromChime(messages);
            System.out.println(messages.size() + " messages was deleted successes");
            return;
        }
        System.out.println("No expired messages");
    }
}
