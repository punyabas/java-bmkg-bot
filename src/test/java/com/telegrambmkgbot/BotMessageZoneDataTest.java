package com.telegrambmkgbot;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith (Parameterized.class)
public class BotMessageZoneDataTest {

    String json, expected;
    LocationObject loc;
    public BotMessageZoneDataTest(String a, LocationObject b, String c ){
        this.json = a; this.loc=b; this.expected=c;
    }


    @Parameters
    public static Collection<Object[]> parameters(){
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
            }
        });
    }

    @Test
    public void dataDrivenEarthquakeZone(){
        BotMessageHandler handler = new BotMessageHandler();
        String message = handler.zonasi(json, loc);
        assertTrue("Zone classification failed", message.contains(expected));
    }

}
