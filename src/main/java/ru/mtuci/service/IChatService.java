package ru.mtuci.service;

import ru.mtuci.domain.Chat;

import java.util.List;

public interface IChatService {

    public List<Chat> getChats(String login);
    public List<String> getChatMembers(String login, Integer chatId);
    public String addChat(String login, String name);
    public String addUserToChat(String memberLogin, Integer chatId, String targetLogin);

}
