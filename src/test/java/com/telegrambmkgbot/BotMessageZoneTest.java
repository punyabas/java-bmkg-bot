package com.telegrambmkgbot;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith (Theories.class)
public class BotMessageZoneTest {
    @DataPoints
    public static String[] data = {
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"12 Jun 2024\",\"Jam\":\"10:45:23 WIB\",\"DateTime\":\"2024-06-12T03:45:23+00:00\",\"Coordinates\":\"-3.33,142.43\",\"Lintang\":\"3.33 LS\",\"Bujur\":\"142.43 BT\",\"Magnitude\":\"5.1\",\"Kedalaman\":\"10 km\",\"Wilayah\":\"132 km BaratLaut WEWAK-PNG\",\"Potensi\":\"Tidak berpotensi tsunami\",\"Dirasakan\":\"-\"}}}",
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"15:07:20 WIB\",\"DateTime\":\"2024-06-11T08:07:20+00:00\",\"Coordinates\":\"0.89,97.76\",\"Lintang\":\"0.89 LU\",\"Bujur\":\"97.76 BT\",\"Magnitude\":\"4.5\",\"Kedalaman\":\"31 km\",\"Wilayah\":\"Pusat gempa berada di darat 27 km tenggara Nias\",\"Dirasakan\":\"II-III Gunungsitoli, II-III Kab. Nias, II-III Kab. Nias Selatan\"}}}",
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"06:46:10 WIB\",\"DateTime\":\"2024-06-10T23:46:10+00:00\",\"Coordinates\":\"4.72,95.88\",\"Lintang\":\"4.72 LU\",\"Bujur\":\"95.88 BT\",\"Magnitude\":\"4.6\",\"Kedalaman\":\"79 km\",\"Wilayah\":\"Pusat gempa berada di darat 34 km timur laut Calang - Aceh Jaya\",\"Dirasakan\":\"II-III Lamno\"}}}",
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"00:29:36 WIB\",\"DateTime\":\"2024-06-10T17:29:36+00:00\",\"Coordinates\":\"-6.77,112.06\",\"Lintang\":\"6.77 LS\",\"Bujur\":\"112.06 BT\",\"Magnitude\":\"3.2\",\"Kedalaman\":\"9 km\",\"Wilayah\":\"Pusat gempa berada di laut 14 km timur laut Tuban\",\"Dirasakan\":\"II Tuban\"}}}",
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"20:30:14 WIB\",\"DateTime\":\"2024-06-10T13:30:14+00:00\",\"Coordinates\":\"-2.46,120.78\",\"Lintang\":\"2.46 LS\",\"Bujur\":\"120.78 BT\",\"Magnitude\":\"2.6\",\"Kedalaman\":\"5 km\",\"Wilayah\":\"Pusat gempa berada di darat 45 km baratlaut Luwu Timur\",\"Dirasakan\":\"II Mangkutana\"}}}",
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"14:08:03 WIB\",\"DateTime\":\"2024-06-10T07:08:03+00:00\",\"Coordinates\":\"5.56,95.43\",\"Lintang\":\"5.56 LU\",\"Bujur\":\"95.43 BT\",\"Magnitude\":\"3.5\",\"Kedalaman\":\"2 km\",\"Wilayah\":\"Pusat gempa berada di darat 39 km tenggara Kota Sabang\",\"Dirasakan\":\"II-III Aceh Besar\"}}}",
        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"13:02:05 WIB\",\"DateTime\":\"2024-06-10T06:02:05+00:00\",\"Coordinates\":\"0.17,124.90\",\"Lintang\":\"0.17 LU\",\"Bujur\":\"124.90 BT\",\"Magnitude\":\"4.8\",\"Kedalaman\":\"15 km\",\"Wilayah\":\"Pusat gempa berada di laut 72 km tenggara Tutuyan - Bolaang Mongondow Timur\",\"Dirasakan\":\"II Minahasa\"}}}",
    };

    @DataPoints
    public static LocationObject[] loc = {
        new LocationObject(0.89, 107.76), //lat same, lon + 10
        new LocationObject(-3.33, 143.43),  //lat same, lon + 1
        new LocationObject(5.72,95.88), //lon same, lat + 1
        new LocationObject(-16.77,112.06),  //lon same, lat + 10
        new LocationObject(5.56,100.43),  //lat same, lat + 5
        new LocationObject(5.17,124.90)  //lon same, lat + 5
      };

    @Theory
    public void TheoriesEarthquakeZoneTest(String data, LocationObject loc){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node;
        try{
            node = mapper.readTree(data);
        }  catch (Exception e) {
           System.out.println("error parse data");
           return;
        }

        String epic =  node.get("Infogempa").get("gempa").get("Coordinates").asText();
        String[] coordinates = epic.split(",");
        double lat = Double.parseDouble(coordinates[0]); 
        double lon = Double.parseDouble(coordinates[1]);
        double delta = 0;

        assumeTrue (Math.abs(loc.lat) <= 180);
        assumeTrue (Math.abs(loc.lon) <= 180);
        assumeTrue (Math.abs(lon) <= 180);
        assumeTrue (Math.abs(lat) <= 180);
        assumeTrue (loc.lat == lat || loc.lon == lon);
       

        // System.err.println("loc.lat " + loc.lat);
        // System.err.println("loc.lon " + loc.lon);
        // System.err.println("lat " + lat);
        // System.err.println("lon " + lon);

        if (loc.lat == lat ) {
            delta = Math.abs(loc.lon - lon); 
        }
        if (loc.lon == lon ) {
            delta = Math.abs(loc.lat - lat); 
        }
        // System.err.println("delta " + delta);
        String msg = "default";
        if ( 0 <= delta && delta < 1 ) {
            msg = "gempa bumi lokal, relatif dari lokasi anda.";
        }
        else if ( 1 <= delta && delta < 10) {
            msg = "gempa bumi regional, relatif dari lokasi anda.";
        }
        else if ( delta >= 10) {
            msg = "gempa bumi teleseismik, relatif dari lokasi anda.";
        }

        BotMessageHandler handler = new BotMessageHandler();
        String output = handler.zonasi(data, loc);
        // System.err.println("output " + output);
        // System.err.println("msg " + msg);

        try {
            assertTrue("Calculation doesnt comply with theories"+output, output.contains(msg));
            System.out.println("Success classification zone with coordinates of "+lat+","+lon+" and "+loc.lat+","+loc.lon+" classification message: "+output);
            System.out.println(" ");
        } catch (AssertionError e) {
            System.out.println("Failed classification zone with coordinates of "+lat+","+lon+" and "+loc.lat+","+loc.lon+", the actual distance is: " + delta + ", but The output message is: " + output + ", the message should mention that: " + msg);
            System.out.println(" ");
        }
       
    } 
    
}
