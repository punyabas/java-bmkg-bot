package com.telegrambmkgbot;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BotMessageMagnitudeDataTest {

    String json;
    String expected;
    Double magnitude;

    public BotMessageMagnitudeDataTest(String a, double b, String c) {
        this.json = a;
        this.magnitude = b;
        this.expected = c;
    }

    @Parameterized.Parameters(name = "{index} -> Gempa berkekuatan {1} M termasuk {2}")
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"06 Jun 2024\",\"Jam\":\"19:46:26 WIB\",\"DateTime\":\"2024-06-06T12:46:26+00:00\",\"Coordinates\":\"-1.15,119.80\",\"Lintang\":\"1.15 LS\",\"Bujur\":\"119.80 BT\",\"Magnitude\": \"3.8\",\"Kedalaman\":\"34 km\",\"Wilayah\":\"Pusat gempa berada di darat 29 km Selatan Palu\",\"Dirasakan\":\"II Sigi\"}}}",
                        3.8, "gempa bumi kecil"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"07 Jun 2024\",\"Jam\":\"23:31:06 WIB\",\"DateTime\":\"2024-06-07T16:31:06+00:00\",\"Coordinates\":\"-2.86,139.37\",\"Lintang\":\"2.86 LS\",\"Bujur\":\"139.37 BT\",\"Magnitude\":\"5.8\",\"Kedalaman\":\"45 km\",\"Wilayah\":\"Pusat gempa berada di darat 95 km timur laut Kobagma\",\"Dirasakan\":\"III Kab. Jayapura\"}}}",
                        5.8, "gempa bumi ringan"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"06 Jun 2024\",\"Jam\":\"20:40:29 WIB\",\"DateTime\":\"2024-06-06T13:40:29+00:00\",\"Coordinates\":\"-1.43,133.91\",\"Lintang\":\"1.43 LS\",\"Bujur\":\"133.91 BT\",\"Magnitude\":\"6.2\",\"Kedalaman\":\"35 km\",\"Wilayah\":\"Pusat gempa berada di darat 17 km barat daya Pegunungan Arfak\",\"Dirasakan\":\"II Manokwari Selatan\"}}}",
                        6.2, "gempa bumi sedang"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"09 Jun 2024\",\"Jam\":\"00:01:56 WIB\",\"DateTime\":\"2024-06-08T17:01:56+00:00\",\"Coordinates\":\"-8.12,107.87\",\"Lintang\":\"8.12 LS\",\"Bujur\":\"107.87 BT\",\"Magnitude\":\"7.4\",\"Kedalaman\":\"43 km\",\"Wilayah\":\"Pusat gempa berada di laut 82 km BaratDaya Kab. Pangandaran\",\"Dirasakan\":\"II - III Pangandaran, II - III Garut\"}}}",
                        7.4, "gempa bumi besar"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"13:02:05 WIB\",\"DateTime\":\"2024-06-10T06:02:05+00:00\",\"Coordinates\":\"0.17,124.90\",\"Lintang\":\"0.17 LU\",\"Bujur\":\"124.90 BT\",\"Magnitude\":\"8.1\",\"Kedalaman\":\"15 km\",\"Wilayah\":\"Pusat gempa berada di laut 72 km tenggara Tutuyan - Bolaang Mongondow Timur\",\"Dirasakan\":\"II Minahasa\"}}}",
                        8.1, "gempa bumi hebat"
                },
                {
                        "{\"Infogempa\":{\"gempa\":{\"Tanggal\":\"10 Jun 2024\",\"Jam\":\"20:30:14 WIB\",\"DateTime\":\"2024-06-10T13:30:14+00:00\",\"Coordinates\":\"-2.46,120.78\",\"Lintang\":\"2.46 LS\",\"Bujur\":\"120.78 BT\",\"Magnitude\":\"2.4\",\"Kedalaman\":\"5 km\",\"Wilayah\":\"Pusat gempa berada di darat 45 km baratlaut Luwu Timur\",\"Dirasakan\":\"II Mangkutana\"}}}",
                        2.4, "gempa bumi sangat kecil"
                }
        });
    }

    @Test
    public void dataDrivenEarthquakeMagnitude() {
        BotMessageHandler handler = new BotMessageHandler();
        String message = handler.kekuatan(json);
        assertTrue("Magnitude classification failed", message.contains(expected));
    }

}
