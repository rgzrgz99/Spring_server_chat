package ru.mtuci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.domain.Chat;
import ru.mtuci.domain.Message;
import ru.mtuci.responses.Status;
import ru.mtuci.responses.Token;
import ru.mtuci.service.IAuthenticationService;
import ru.mtuci.service.IChatService;
import ru.mtuci.service.IMessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messenger")
public class ChatMessengerController {

    @Autowired
    IAuthenticationService authenticationService;

    @Autowired
    IChatService chatService;

    @Autowired
    IMessageService messageService;

    @GetMapping("add-user")
    public Status addUser(@RequestParam(required = true, defaultValue = "") String login,
                        @RequestParam(required = true, defaultValue = "") String password){
        if(login.equals("") || password.equals("")){
            return new Status("error");
        }
        return new Status(authenticationService.addNewUser(login, password));
    }

    @GetMapping("get-token")
    public Token addSession(@RequestParam(required = true, defaultValue="") String login,
                             @RequestParam(required = true, defaultValue="") String password,
                             @RequestParam(required = false, defaultValue="noname") String name){

        if(login.equals("") || password.equals("")){
            return new Token("error");
        }

        return new Token(authenticationService.getToken(login, password, name));
    }

    @GetMapping("send-message")
    public Status sendMessage(@RequestParam(required = true, defaultValue="") String token,
                              @RequestParam(required = true, defaultValue="-1") Integer chatid,
                              @RequestParam(required = true, defaultValue="") String message){

        if(token.equals("") || message.equals("")){
            return new Status("error");
        }

        String login = authenticationService.getLoginByToken(token);

        return new Status(messageService.sendMessage(login, chatid, message));

    }

    @GetMapping("get-message")
    public List<Message> getMessage(@RequestParam(required = true, defaultValue="") String token,
                                    @RequestParam(required = false, defaultValue="2019-01-01T00:00")
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                            LocalDateTime start,
                                    @RequestParam(required = false, defaultValue="-1") Integer chatid){

        if(token.equals("") || start == null){
            return new ArrayList<Message>();
        }

        String login = authenticationService.getLoginByToken(token);

        return messageService.getMessage(login, start, chatid);
    }




    @GetMapping("chat-list")
    public List<Chat> getChats(@RequestParam(required = true, defaultValue="") String token){
        if(token.equals("")){
            return new ArrayList<Chat>();
        }

        String login = authenticationService.getLoginByToken(token);

        return chatService.getChats(login);
    }

    @GetMapping("chat-members-list")
    public List<String> getChatMembers(@RequestParam(required = true, defaultValue="") String token,
                                     @RequestParam(required = true, defaultValue="-1") Integer chatid){
        if(token.equals("")){
            return new ArrayList<String>();
        }

        String login = authenticationService.getLoginByToken(token);

        return chatService.getChatMembers(login, chatid);
    }

    @GetMapping("add-chat")
    public Status addChat(@RequestParam(required = true, defaultValue="") String token,
                          @RequestParam(required = true, defaultValue="") String name){

        if(token.equals("") || name.equals("")){
            return new Status("error");
        }

        String login = authenticationService.getLoginByToken(token);

        return new Status(chatService.addChat(login, name));
    }

    @GetMapping("add-user-to-chat")
    public Status addUserToChat(@RequestParam(required = true, defaultValue="") String token,
                                @RequestParam(required = true, defaultValue="") Integer chatid,
                                @RequestParam(name = "login", required = true, defaultValue="") String targetLogin){

        if(token.equals("") || targetLogin.equals("")){
            return new Status("error");
        }

        String login = authenticationService.getLoginByToken(token);

        return new Status(chatService.addUserToChat(login, chatid, targetLogin));
    }


}
