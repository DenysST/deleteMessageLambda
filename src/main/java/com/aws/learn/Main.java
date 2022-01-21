package com.aws.learn;

import java.util.List;
public class Main {

    public String deleteExpiredMessages(int days) {
        MessageService service = new MessageService();
        List<Message> messages = service.getAll(days);
        if (messages.size() > 0 ) {
            service.deleteMessagesFromDb(messages);
            service.deleteMessageFromChime(messages);
            System.out.println(messages.size() + " messages was deleted successes");
            return messages.size() + " messages was deleted successes";
        }
        System.out.println("No expired messages");
        return  "No expired messages";
    }
}
