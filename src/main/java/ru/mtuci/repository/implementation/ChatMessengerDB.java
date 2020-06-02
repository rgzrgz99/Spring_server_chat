package ru.mtuci.repository.implementation;

import org.springframework.stereotype.Repository;
import ru.mtuci.domain.Chat;
import ru.mtuci.domain.Message;
import ru.mtuci.repository.IChatMessengerDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatMessengerDB implements IChatMessengerDB {

    Connection conn;
    ChatMessengerDB(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/messenger";
            String login = "qqbeta";
            String password = "12345";
            conn = DriverManager.getConnection(url, login, password);
            System.out.print("sdfsdfs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPasswordHashByLogin(String login) {
        String passwordHash = "error";
        try {
            PreparedStatement stmt = conn.prepareStatement(SELECT_PASSWORDHASH_BY_LOGIN);
            stmt.setString(1, login);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                passwordHash = rs.getString("password");
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return passwordHash;
    }

    @Override
    public String addSession(String login, String token, String name, LocalDateTime date) {
        String res = "error";
        try{

            PreparedStatement pstmt = conn.prepareStatement(INSERT_INTO_SESSION,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, login);
            pstmt.setString(2, token);
            pstmt.setString(3, name);
            pstmt.setTimestamp(4, new Timestamp(date.toInstant(ZoneOffset.UTC).toEpochMilli()));

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows != 0) {
                res = "ok";
            }

        } catch (SQLException e){
            System.out.println(e);
        }

        return res;
    }

    @Override
    public String getLoginByToken(String token) {
        String login = "error";
        try {
            PreparedStatement stmt = conn.prepareStatement(SELECT_LOGIN_BY_TOKEN);
            stmt.setString(1, token);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                login = rs.getString("login");
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return login;
    }

    @Override
    public List<Chat> getChats(String login) {
        List<Chat> chats = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(SELECT_CHATS_BY_LOGIN);
            stmt.setString(1, login);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                chats.add(new Chat(rs));
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return chats;
    }

    @Override
    public Integer addChat(String name) {
        Integer res = -1;
        try{

            PreparedStatement pstmt = conn.prepareStatement(INSERT_INTO_CHAT,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, name);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows != 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()){
                    res = generatedKeys.getInt("chat_id");
                }
            }

        } catch (SQLException e){
            System.out.println(e);
        }

        return res;
    }

    @Override
    public List<String> getChatMembers(Integer chatId) {
        List<String> members = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(SELECT_CHAT_MEMBERS_BY_CHATID);
            stmt.setInt(1, chatId);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                members.add(rs.getString("login"));
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return members;
    }

    @Override
    public String addUserChatCompound(String login, Integer chatid) {
        String res = "error";
        try{

            PreparedStatement pstmt = conn.prepareStatement(INSERT_CHAT_COMPOUND,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, login);
            pstmt.setInt(2, chatid);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows != 0) {
                res = "ok";
            }

        } catch (SQLException e){
            System.out.println(e);
        }

        return res;
    }

    @Override
    public String addMessage(String login, Integer chatid, LocalDateTime time, String message) {
        String res = "error";
        try{

            PreparedStatement pstmt = conn.prepareStatement(INSERT_INTO_MESSAGE,
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, login);
            pstmt.setInt(2, chatid);
            pstmt.setTimestamp(3, new Timestamp(time.toInstant(ZoneOffset.UTC).toEpochMilli()));
            pstmt.setString(4, message);

            int affectedRows = pstmt.executeUpdate();
            if(affectedRows != 0) {
                res = "ok";
            }

        } catch (SQLException e){
            System.out.println(e);
        }

        return res;
    }

    @Override
    public List<Message> getMessage(String login, LocalDateTime startTime) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(SELECT_MESSAGES_BY_LOGIN_AND_TIME);

            stmt.setString(1, login);
            stmt.setTimestamp(2, new Timestamp(startTime.toInstant(ZoneOffset.UTC).toEpochMilli()));


            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                messages.add(new Message(rs));
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return messages;
    }

    @Override
    public List<Message> getMessage(String login, LocalDateTime startTime, Integer chatid) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(SELECT_MESSAGES_BY_LOGIN_AND_TIME_AND_CHATID);

            stmt.setString(1, login);
            stmt.setInt(2, chatid);
            stmt.setTimestamp(3, new Timestamp(startTime.toInstant(ZoneOffset.UTC).toEpochMilli()));


            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                messages.add(new Message(rs));
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return messages;
    }
}
