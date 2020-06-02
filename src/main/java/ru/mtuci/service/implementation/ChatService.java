package ru.mtuci.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.domain.Chat;
import ru.mtuci.repository.IChatMessengerDB;
import ru.mtuci.service.IChatService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService implements IChatService {

    @Autowired
    IChatMessengerDB chatMessengerDB;

    @Override
    public List<Chat> getChats(String login) {
        return chatMessengerDB.getChats(login);
    }

    @Override
    public List<String> getChatMembers(String login, Integer chatId) {
        List<String> chatMembers = chatMessengerDB.getChatMembers(chatId);

        if(!chatMembers.contains(login)){
            return new ArrayList<>();
        }

        return chatMembers;
    }

    @Override
    public String addChat(String login, String name) {
        Integer chatId = chatMessengerDB.addChat(name);
        return chatMessengerDB.addUserChatCompound(login, chatId);
    }


    @Override
    public String addUserToChat(String memberLogin, Integer chatId, String targetLogin) {
        List<String> chatMembers = chatMessengerDB.getChatMembers(chatId);

        if(!chatMembers.contains(memberLogin)){
            return "error";
        }

        return chatMessengerDB.addUserChatCompound(targetLogin, chatId);
    }
}
