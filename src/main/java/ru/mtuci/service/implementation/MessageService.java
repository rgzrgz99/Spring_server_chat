package ru.mtuci.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.domain.Message;
import ru.mtuci.repository.IChatMessengerDB;
import ru.mtuci.service.IMessageService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    IChatMessengerDB chatMessengerDB;

    @Override
    public List<Message> getMessage(String login, LocalDateTime startTime, Integer chatId) {
        if(chatId == -1){
            return chatMessengerDB.getMessage(login, startTime);
        }
        return chatMessengerDB.getMessage(login, startTime, chatId);
    }

    @Override
    public String sendMessage(String login, Integer chatId, String message) {
        return chatMessengerDB.addMessage(login, chatId, LocalDateTime.now(), message);
    }
}
