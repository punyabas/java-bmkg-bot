package com.telegrambmkgbot;

public class BotMessageObject {
    public String message;
    public int chatId;

    public BotMessageObject(String a, int b){
        this.message = a; 
        this.chatId = b;
    }
}
