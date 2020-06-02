package ru.mtuci.service;

import ru.mtuci.domain.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface IMessageService {

    public List<Message> getMessage(String login, LocalDateTime startTime, Integer chatId);

    public String sendMessage(String login, Integer chatId, String message);

}
