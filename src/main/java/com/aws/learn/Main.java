package com.aws.learn;

import java.util.List;

public class Main {
    public String deleteExpiredMessages(int days) {
        if (days > 31 || days < 1) {
            return "Please enter number grater 0 and less 31";
        }
        MessageDao dao = new MessageDao();
        List<Message> all = dao.getAll(days * -1);
        if (all.size() > 0 ) {
            dao.deleteMessages(all);
            return all.size() + " messages was deleted successful";
        }
        return "No expired messages";
    }
}
