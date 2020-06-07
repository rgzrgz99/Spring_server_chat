package ru.mtuci.service;

public interface IAuthenticationService {

    public String getToken(String login, String password, String name);
    public String getLoginByToken(String token);
    public String addNewUser(String login, String password);
}
