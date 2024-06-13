package com.telegrambmkgbot;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith (Parameterized.class)
public class BotMessageZoneDataTest {

    private String sJson, expected;
    private LocationObject loc;

    public BotMessageZoneDataTest(String a, LocationObject b, String c ){
        this.sJson = a; this.loc=b; this.expected=c;
    }

    @Parameters
    public static Collection<Object[]> data(){
        // LocationObject a = new LocationObject(-3.00,142);
        // LocationObject b = new LocationObject(-3.00,142);
        return Arrays.asList (new Object [][]{
            {
                "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"12 Jun 2024\",\"Jam\":\"10:45:23 WIB\",\"DateTime\":\"2024-06-12T03:45:23+00:00\",\"Coordinates\":\"-3.33,142.43\",\"Lintang\":\"3.33 LS\",\"Bujur\":\"142.43 BT\",\"Magnitude\":\"5.1\",\"Kedalaman\":\"10 km\",\"Wilayah\":\"132 km BaratLaut WEWAK-PNG\",\"Potensi\":\"Tidak berpotensi tsunami\",\"Dirasakan\":\"-\"}}}",
                new LocationObject(-3.00,142), 
                "gempa bumi lokal"
            },
            {
                "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"15:07:20 WIB\",\"DateTime\":\"2024-06-11T08:07:20+00:00\",\"Coordinates\":\"0.89,97.76\",\"Lintang\":\"0.89 LU\",\"Bujur\":\"97.76 BT\",\"Magnitude\":\"4.5\",\"Kedalaman\":\"31 km\",\"Wilayah\":\"Pusat gempa berada di darat 27 km tenggara Nias\",\"Dirasakan\":\"II-III Gunungsitoli, II-III Kab. Nias, II-III Kab. Nias Selatan\"}}}",
                new LocationObject(01,97), 
                "gempa bumi lokal"
            },
            {
                "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"06:46:10 WIB\",\"DateTime\":\"2024-06-10T23:46:10+00:00\",\"Coordinates\":\"4.72,95.88\",\"Lintang\":\"4.72 LU\",\"Bujur\":\"95.88 BT\",\"Magnitude\":\"4.6\",\"Kedalaman\":\"79 km\",\"Wilayah\":\"Pusat gempa berada di darat 34 km timur laut Calang - Aceh Jaya\",\"Dirasakan\":\"II-III Lamno\"}}}",
                new LocationObject(5.72,98.88), 
                "gempa bumi regional"
            },
            {
                "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"00:29:36 WIB\",\"DateTime\":\"2024-06-10T17:29:36+00:00\",\"Coordinates\":\"-6.77,112.06\",\"Lintang\":\"6.77 LS\",\"Bujur\":\"112.06 BT\",\"Magnitude\":\"3.2\",\"Kedalaman\":\"9 km\",\"Wilayah\":\"Pusat gempa berada di laut 14 km timur laut Tuban\",\"Dirasakan\":\"II Tuban\"}}}",
                new LocationObject(-8,113), 
                "gempa bumi regional"
            },
            {
                "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"14:08:03 WIB\",\"DateTime\":\"2024-06-10T07:08:03+00:00\",\"Coordinates\":\"5.56,95.43\",\"Lintang\":\"5.56 LU\",\"Bujur\":\"95.43 BT\",\"Magnitude\":\"3.5\",\"Kedalaman\":\"2 km\",\"Wilayah\":\"Pusat gempa berada di darat 39 km tenggara Kota Sabang\",\"Dirasakan\":\"II-III Aceh Besar\"}}}",
                new LocationObject(15.56,100.43), 
                "gempa bumi teleseismik"
            },
            {
                "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"13:02:05 WIB\",\"DateTime\":\"2024-06-10T06:02:05+00:00\",\"Coordinates\":\"0.17,124.90\",\"Lintang\":\"0.17 LU\",\"Bujur\":\"124.90 BT\",\"Magnitude\":\"4.8\",\"Kedalaman\":\"15 km\",\"Wilayah\":\"Pusat gempa berada di laut 72 km tenggara Tutuyan - Bolaang Mongondow Timur\",\"Dirasakan\":\"II Minahasa\"}}}",
                new LocationObject(-9.17,116.90), 
                "gempa bumi teleseismik"
            }
        });
    }

    @Test
    public void dataDrivenEarthquakeZone(){
        BotMessageHandler handler = new BotMessageHandler();
        String message = handler.zonasi(sJson, loc);
        // System.out.println("hasil: " + message);
        assertTrue("Zone classification failed", message.contains(expected));
    }

}
