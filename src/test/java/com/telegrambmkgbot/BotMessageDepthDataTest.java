package com.telegrambmkgbot;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BotMessageDepthDataTest {

    String json;
    String expected;
    Double depth;

    public BotMessageDepthDataTest(String a, double b, String c) {
        this.json = a;
        this.depth = b;
        this.expected = c;
    }

    @Parameterized.Parameters(name = "{index} -> Gempa sedalam {1} km termasuk {2}")
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"09 Jun 2024\",\"Jam\":\"00:01:56 WIB\",\"DateTime\":\"2024-06-08T17:01:56+00:00\",\"Coordinates\":\"-8.12,107.87\",\"Lintang\":\"8.12 LS\",\"Bujur\":\"107.87 BT\",\"Magnitude\":\"4.2\",\"Kedalaman\":\"43 km\",\"Wilayah\":\"Pusat gempa berada di laut 82 km BaratDaya Kab. Pangandaran\",\"Dirasakan\":\"II - III Pangandaran, II - III Garut\"}}}",
                        43, "gempa bumi dangkal"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"11 Jun 2024\",\"Jam\":\"06:46:10 WIB\",\"DateTime\":\"2024-06-10T23:46:10+00:00\",\"Coordinates\":\"4.72,95.88\",\"Lintang\":\"4.72 LU\",\"Bujur\":\"95.88 BT\",\"Magnitude\":\"4.6\",\"Kedalaman\":\"79 km\",\"Wilayah\":\"Pusat gempa berada di darat 34 km timur laut Calang - Aceh Jaya\",\"Dirasakan\":\"II-III Lamno\"}}}",
                        79, "gempa bumi sedang"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"06 Jun 2024\",\"Jam\":\"19:46:26 WIB\",\"DateTime\":\"2024-06-06T12:46:26+00:00\",\"Coordinates\":\"-1.15,119.80\",\"Lintang\":\"1.15 LS\",\"Bujur\":\"119.80 BT\",\"Magnitude\": \"3.8\",\"Kedalaman\":\"305 km\",\"Wilayah\":\"Pusat gempa berada di darat 29 km Selatan Palu\",\"Dirasakan\":\"II Sigi\"}}}",
                        305, "gempa bumi dalam"
                }
        });
    }

    @Test
    public void dataDrivenEarthquakeDepth() {
        BotMessageHandler handler = new BotMessageHandler();
        String message = handler.kedalaman(json);
        assertTrue("Depth classification failed", message.contains(expected));
    }

}
