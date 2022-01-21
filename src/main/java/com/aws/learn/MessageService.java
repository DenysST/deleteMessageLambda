package com.aws.learn;

import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.chime.ChimeClient;
import software.amazon.awssdk.services.chime.model.DeleteChannelMessageRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessageService {
    public List<Message> getAll(int days) {
        String query = "SELECT * " +
                "FROM messages " +
                "JOIN channels c on c.id = messages.channel_id " +
                "WHERE created_timestamp <= ?";
        Date checkDate = addDays(new Date(), days);
        List<Message> allMessages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement getMessages =
                     connection.prepareStatement(query)) {
            Timestamp timestamp = new Timestamp(checkDate.getTime());
            getMessages.setTimestamp(1, timestamp);
            ResultSet resultSet = getMessages.executeQuery();
            while (resultSet.next()) {
                Message message = createMessage(resultSet);
                allMessages.add(message);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't get messages from db", throwables);
        }
        return allMessages;
    }

    public void deleteMessagesFromDb(List<Message> messages) {
        String query = createDeleteQuery(messages);
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteStatement =
                     connection.prepareStatement(query)) {
            deleteStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't delete messages from", throwables);
        }
    }

    public void deleteMessageFromChime(List<Message> messages) {
        ChimeClient client = AmazonChimeService.getClient();
        for (Message message : messages) { // I know loop isn't the best way, but I don't find other decision
           try {
               client.deleteChannelMessage(DeleteChannelMessageRequest.builder()
                       .channelArn(message.getChannelArn())
                       .messageId(message.getMessage_id())
                       .chimeBearer("")
                       .build());
           } catch (SdkException e) {
               throw new RuntimeException("Error while delete message from chime", e);
           }
        }
    }
    private String createDeleteQuery(List<Message> messages) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM messages m WHERE m.id IN (");
        for (int i = 0; i < messages.size(); i++) {
            query.append(messages.get(i).getId());
            if (i != messages.size() -1) {
                query.append(",");
            }
        }
        return query.append(")").toString();

    }
    private Message createMessage(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        Long id = resultSet.getObject("id", Long.class);
        String content = resultSet.getString("content");
        String messageId = resultSet.getString("message_id");
        String channelArn = resultSet.getString("channel_arn");
        message.setId(id);
        message.setContent(content);
        message.setMessage_id(messageId);
        message.setChannelArn(channelArn);
        return message;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days * -1); //minus number would decrement the days
        return cal.getTime();
    }
}
