package com.telegrambmkgbot;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class BotHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/bot-bmkg-get-update", new BotHttpHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server is listening on port 8080");
    }
}

