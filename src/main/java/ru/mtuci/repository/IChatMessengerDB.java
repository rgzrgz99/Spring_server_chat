package ru.mtuci.repository;

import ru.mtuci.domain.Chat;
import ru.mtuci.domain.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IChatMessengerDB {

    String SELECT_PASSWORDHASH_BY_LOGIN = "SELECT password FROM messengeruser WHERE login = ?";
    String SELECT_LOGIN_BY_TOKEN = "SELECT login FROM session WHERE token = ?";
    String INSERT_INTO_SESSION = "INSERT INTO session(login, token, name, start_time) VALUES (?, ?, ?, ?)";

    String SELECT_CHATS_BY_LOGIN = "SELECT * FROM chat WHERE chat_id IN (SELECT chat_id FROM messengeruser_chat_compound WHERE login = ?)";
    String SELECT_CHAT_MEMBERS_BY_CHATID = "SELECT login FROM messengeruser_chat_compound WHERE chat_id = ?";
    String INSERT_INTO_CHAT = "INSERT INTO chat(name) VALUES (?)";
    String INSERT_CHAT_COMPOUND = "INSERT INTO messengeruser_chat_compound(login, chat_id) VALUES (?, ?)";

    String SELECT_MESSAGES_BY_LOGIN_AND_TIME = "SELECT * FROM message " +
            "WHERE chat_id IN (SELECT chat_id FROM messengeruser_chat_compound WHERE login = ?) AND time > ?";
    String SELECT_MESSAGES_BY_LOGIN_AND_TIME_AND_CHATID = "SELECT * FROM message " +
            "WHERE chat_id IN (SELECT chat_id FROM messengeruser_chat_compound WHERE login = ? AND chat_id = ?) AND time > ?";
    String INSERT_INTO_MESSAGE = "INSERT INTO message(login, chat_id, time, message) VALUES (?, ?, ?, ?)";
    String INSERT_INTO_MESSENGERUSER = "INSERT INTO messengeruser(login, password) VALUES (?, ?)";

    public String getPasswordHashByLogin(String login);
    public String addSession(String login, String token, String name, LocalDateTime date);
    public String getLoginByToken(String token);
    public String addNewUser(String Login,String Password);
    public List<Chat> getChats(String login);
    public Integer addChat(String name);
    public List<String> getChatMembers(Integer chatId);
    public String addUserChatCompound(String login, Integer chatid);




    public String addMessage(String login, Integer chatid, LocalDateTime time, String message);


    public List<Message> getMessage(String login, LocalDateTime startTime);
    public List<Message> getMessage(String login, LocalDateTime startTime, Integer chatid);

}
