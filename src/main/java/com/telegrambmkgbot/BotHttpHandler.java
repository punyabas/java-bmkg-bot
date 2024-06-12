package com.telegrambmkgbot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class BotHttpHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            InputStream requestBody = exchange.getRequestBody();
            String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Received request body: " + body);

            // Jackson main object
            ObjectMapper mapper = new ObjectMapper();

            // read the json strings and convert it into JsonNode
            JsonNode node = mapper.readTree(body);
            BotMessageHandler handler = new BotMessageHandler();

            BotMessageObject msg = handler.handleMessage(node);
            handler.sendMessage(msg);

            // display the JsonNode
            // System.out.println("Name: " + node.get("message").get("text").asText());

            String response = "ok";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            // System.out.println("pesan masuk");
        }
    
}
