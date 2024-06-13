package com.telegrambmkgbot;

public class BotMessageObject {
    public String message;
    public Long chatId;

    public BotMessageObject(String a, Long b){
        this.message = a; 
        this.chatId = b;
    }
}
