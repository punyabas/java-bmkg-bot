package com.telegrambmkgbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BotMessageHandler {
    
    private static final String API_URL_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=%s";
    private static final String API_TOKEN = "6554764956:AAFhB6Xd3_CDxCMm4j42hxfdR8Sgo2yUb-4"; // Replace with your bot's API token
    private static final String PARSE_MODE = "Markdown";

    public BotMessageObject handleMessage(JsonNode node) {
        // Handle update message
        // Get data from decoded update object
        // System.out.println("THIS: ");
        String conversation = "Reply pesan ini dengan data lokasi anda";
        JsonNode messageNode = node.path("message");
        String text;
        String msg;
        Integer chatId = node.get("message").get("chat").get("id").asInt();
        if (messageNode.has("text")) {
            text = node.get("message").get("text").asText();
        } else if (messageNode.has("location") && messageNode.has("reply_to_message") && conversation.equals(messageNode.get("reply_to_message").get("text").asText())) {
            String jsonZonasi = this.getData("https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json");
            double lat = messageNode.get("location").get("latitude").asDouble();
            double lon = messageNode.get("location").get("longitude").asDouble();
            LocationObject loc = new LocationObject(lat, lon);
            msg = this.zonasi(jsonZonasi, loc);
            BotMessageObject chat = new BotMessageObject(msg, chatId);
            return chat;
        } else {
            text = "invalid command";
        }

        switch (text) {
            case "/start":
                msg = "Selamat Datang Di BOT Gempa BMKG  ketik /menu untuk mencoba";
                break;
            case "/menu":
                // msg = "Daftar command adalah sebagai berikut%0A%2Fstart%0A%2Fmenu%0A%2Fterakhir%0A%2Fkekuatan%0A%2Fzonasi%0A%2Fkedalaman";
                msg = "Daftar command adalah sebagai berikut%0A%2Fkekuatan%0A%2Fzonasi%0A%2Fkedalaman";
                break;
            // case "/terakhir":
            //     String jsonTerakhir = this.getData("https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json");
            //     msg = this.terakhir(jsonTerakhir);
            //     break;
            case "/kekuatan":
                String jsonKekuatan = this.getData("https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json");
                msg = this.kekuatan(jsonKekuatan);
                break;
            case "/zonasi":
                msg = conversation;
                break;
            case "/kedalaman":
                String jsonKedalaman = this.getData("https://data.bmkg.go.id/DataMKG/TEWS/autogempa.json");
                msg = this.kedalaman(jsonKedalaman);
                break;
            default:
                msg = "Maaf pesan tidak bisa diproses";
                break;
        }

        BotMessageObject chat = new BotMessageObject(msg, chatId);
        return chat;
    }

    // public String terakhir(String json){
    //     System.out.println("Received request body: " + json);
    //     ObjectMapper mapper = new ObjectMapper();
    //     JsonNode node;
    //     try{
    //         node = mapper.readTree(json);
    //     }  catch (Exception e) {
    //         return "gagal mendapatkan data gempa";
    //     }
        
    //     // 2024-06-10T23:46:10+00:00
    //     String timestampAsString = node.get("Infogempa").get("gempa").get("DateTime").asText();

    //      // Parse the timestamp string to an OffsetDateTime object
    //     OffsetDateTime timestampTime = OffsetDateTime.parse(timestampAsString);

    //     // Get the current time
    //     OffsetDateTime currentTime = OffsetDateTime.now();

    //     // Calculate the difference in hours between the current time and the timestamp
    //     Duration duration = Duration.between(timestampTime, currentTime);
        
    //     // Return true if the difference is less than or equal to 1 hour
    //     if(duration.toHours() < 1){
    //         var mag = node.get("Infogempa").get("gempa").get("Magnitude").asText();
    //         var time = node.get("Infogempa").get("gempa").get("Jam").asText();
    //         var loc = node.get("Infogempa").get("gempa").get("Wilayah").asText();
    //         return "Gempa berkekuaran " +mag+ "SR, terjadi pada " + time + ". "+ loc;
    //     } else {
    //         return "Tidak ada gempa tercatat sejak 1 jam terakhir";
    //     }
        
    // }

    // BISA DIPAKAI NANTI
    // public String[] dataset (String json) throws JsonProcessingException {
    //     System.out.println("Received request body: " + json);
    //     ObjectMapper mapper = new ObjectMapper();
    //     JsonNode node;
    //     node = mapper.readTree(json);
    //     String date = node.get("Infogempa").get("gempa").get("Tanggal").asText();
    //     String time = node.get("Infogempa").get("gempa").get("Jam").asText();
    //     String locs = node.get("Infogempa").get("gempa").get("Wilayah").asText();
    //     String depth = node.get("Infogempa").get("gempa").get("Kedalaman").asText();
    //     return new String[] {date, time, locs, depth};
    // }

    public String kekuatan(String json){
        System.out.println("Received request body: " + json);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(json);
        }  catch (Exception e) {
            return "gagal mendapatkan data gempa";
        }

        String date = node.get("Infogempa").get("gempa").get("Tanggal").asText();
        String time = node.get("Infogempa").get("gempa").get("Jam").asText();
        String loc = node.get("Infogempa").get("gempa").get("Wilayah").asText();
        String depth = node.get("Infogempa").get("gempa").get("Kedalaman").asText();

        String msg = "Telah terjadi gempa pada tanggal "+ date + " pukul " + time + ", dengan pusat gempa berada di "+ loc;
        double mag =  Double.parseDouble(node.get("Infogempa").get("gempa").get("Magnitude").asText());
        msg = msg + ". Kekuatan gempa adalah " +mag+ " SR, dengan kedalaman " + depth + ". Berdasarkan magnitude, gempa bumi yang terjadi termasuk ";
        if ( 2.54 <= mag && mag < 5.5) {
            msg = msg + "gempa bumi kecil, kerusakan minim.";
        }
        else if ( 5.5 <= mag && mag < 6.1) {
            msg = msg + "gempa bumi ringan, menyebabkan kerusakan ringan pada bangunan.";
        }
        else if ( 6.1 <= mag && mag < 7.0) {
           msg = msg + "gempa bumi sedang, banyak kerusakan pada pemukiman padat.";
        }
        else if ( 7.0 <= mag && mag < 8.0) {
           msg = msg + "gempa bumi besar, banyak kerusakan serius";
        }
        else if ( mag >= 8.0) {
           msg = msg + "gempa bumi hebat, menghancurkan komunitas di dekat pusat gempa.";
        }
        else {
           msg = msg + "gempa bumi sangat kecil, tidak terasa.";
        }
        return msg;
    }

    public String zonasi(String json, LocationObject loc){
        System.out.println("Received request body: " + json);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(json);
        }  catch (Exception e) {
            return "gagal mendapatkan data gempa";
        }

        String date = node.get("Infogempa").get("gempa").get("Tanggal").asText();
        String time = node.get("Infogempa").get("gempa").get("Jam").asText();
        String locs = node.get("Infogempa").get("gempa").get("Wilayah").asText();
        String depth = node.get("Infogempa").get("gempa").get("Kedalaman").asText();

        String msg = "Telah terjadi gempa pada tanggal "+ date + " pukul " + time + ", dengan pusat gempa berada di "+ locs;
        double mag =  Double.parseDouble(node.get("Infogempa").get("gempa").get("Magnitude").asText());
        msg = msg + ". Kekuatan gempa adalah " +mag+ " SR, dengan kedalaman " + depth + ". Berdasarkan zonasi, gempa bumi yang terjadi termasuk ";
        String epic =  node.get("Infogempa").get("gempa").get("Coordinates").asText();
        String[] coordinates = epic.split(",");
        double lat = Double.parseDouble(coordinates[0]); 
        double lon = Double.parseDouble(coordinates[1]);

        double latDistance = Math.toRadians(loc.lat - lat);
        double lonDistance = Math.toRadians(loc.lon - lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(loc.lat)) * Math.cos(Math.toRadians(lat))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double deg = Math.toDegrees(c); // convert to degree

        if ( 0 <= deg && deg < 1 ) {
            msg = msg + "gempa bumi lokal, relatif dari lokasi anda.";
        }
        else if ( 1 <= deg && deg < 10) {
            msg = msg + "gempa bumi regional, relatif dari lokasi anda.";
        }
        else if ( deg >= 10) {
            msg = msg + "gempa bumi teleseismik, relatif dari lokasi anda.";
        }
        else {
            msg = msg + "gempa bumi yang tidak terklasifikasi.";
        }

        return msg;
    }

    public String kedalaman(String json) {
        System.out.println("Received request body: " + json);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(json);
        }  catch (Exception e) {
            return "gagal mendapatkan data gempa";
        }
        String date = node.get("Infogempa").get("gempa").get("Tanggal").asText();
        String time = node.get("Infogempa").get("gempa").get("Jam").asText();
        String locs = node.get("Infogempa").get("gempa").get("Wilayah").asText();
        String depth = node.get("Infogempa").get("gempa").get("Kedalaman").asText();

        String msg = "Telah terjadi gempa pada tanggal "+ date + " pukul " + time + ", dengan pusat gempa berada di "+ locs;
        double mag =  Double.parseDouble(node.get("Infogempa").get("gempa").get("Magnitude").asText());
        msg = msg + ". Kekuatan gempa adalah " +mag+ " SR, dengan kedalaman " + depth + ". Berdasarkan kedalaman, gempa bumi yang terjadi termasuk ";
        String deepString = node.get("Infogempa").get("gempa").get("Kedalaman").asText();
        deepString = deepString.replaceAll("[^0-9]", "");
        double deep =  Double.parseDouble(deepString);
        if (  0 < deep && deep <= 70) {
            msg = msg + "gempa bumi dangkal.";
        }
        else if ( 70 < deep && deep <= 300) {
            msg = msg + "gempa bumi sedang.";
        }
        else if ( deep > 300) {
            msg = msg + "gempa bumi dalam.";
        }
        else {
            msg = msg + "gempa bumi yang tidak terklasifikasi.";
        }

        return msg;
    }

    public void sendMessage(BotMessageObject msg) {
        try {
            var apiUrl = String.format(API_URL_TEMPLATE, API_TOKEN, msg.chatId, msg.message, PARSE_MODE);
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            String inputLine;

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                var response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Print the response from the API
                System.out.println("Response: " + response.toString());
            } else {
                var responseBody = connection.getResponseMessage();
                System.out.println("GET request not worked" + responseBody);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData(String link){
        try {
            // Create a URL object
            URL url = new URL(link);
            
            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Set request method to GET
            connection.setRequestMethod("GET");
            
            // Get the response code
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                // Create a BufferedReader to read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                
                // Close the BufferedReader
                in.close();
                
                // Print the response (for debugging purposes)
                System.out.println("Response: " + response.toString());
                return response.toString();
            } else {
                System.out.println("GET request not worked");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
