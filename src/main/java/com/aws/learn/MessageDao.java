package com.aws.learn;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessageDao {
    public List<Message> getAll(int days) {
        String query = "SELECT * FROM messages where created_timestamp <= ?";
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

    public void deleteMessages(List<Message> messages) {
        String query = createDeleteQuery(messages);
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement deleteStatement =
                     connection.prepareStatement(query)) {
            deleteStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't delete messages from", throwables);
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
        message.setId(id);
        message.setContent(content);
        return message;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
