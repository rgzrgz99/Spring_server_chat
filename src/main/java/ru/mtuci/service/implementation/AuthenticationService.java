package ru.mtuci.service.implementation;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.repository.IChatMessengerDB;
import ru.mtuci.service.IAuthenticationService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    IChatMessengerDB chatMessengerDB;

    @Override
    public String getToken(String login, String password, String name) {
        if(!password.equals(chatMessengerDB.getPasswordHashByLogin(login))){
            return "error";
        }
        String token = RandomStringUtils.randomAlphanumeric(16);
        if(chatMessengerDB.addSession(login, token, name, LocalDateTime.now()).equals("ok")){
            return token;
        }

        return "error";
    }

    @Override
    public String getLoginByToken(String token) {
        return chatMessengerDB.getLoginByToken(token);
    }

    @Override
    public String addNewUser(String login, String password){
        return chatMessengerDB.addNewUser(login, password);
    }
}
