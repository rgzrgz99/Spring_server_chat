package ru.mtuci.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Chat {
    public String chatId;
    public String name;

    public Chat(){}
    public Chat(ResultSet rs){
        try {
            chatId = rs.getString("chat_id");
            name = rs.getString("name");
        } catch (SQLException e){
            System.out.println(e);
        }
    }
}
