package com.aws.learn;

import java.util.List;
public class Main {

    public void deleteExpiredMessages(String arn) {
        MessageDao dao = new MessageDao();
        List<Message> messages = dao.getAllByChannel(arn);
        if (messages.size() > 0 ) {
            dao.deleteMessagesFromDb(messages);
            dao.deleteMessageFromChime(messages);
        }
    }
}
