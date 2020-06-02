package ru.mtuci.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Message {
    public String chatId;
    public String login;
    public LocalDateTime time;
    public String message;

    public Message(){}
    public Message(ResultSet rs){
        try{
            chatId = rs.getString("chat_id");
            login = rs.getString("login");
            time = LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getTimestamp("time").getTime()), ZoneOffset.UTC);
            message = rs.getString("message");
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
