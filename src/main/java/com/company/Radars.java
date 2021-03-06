package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Radars {

    public String getRadarEur() {
        try {
            Document pageEur = Jsoup.connect("https://www.weatheronline.co.uk/Europe.htm")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            
            Elements el = pageEur.select("html body.eBody div.eZent_300 div.eAll_sky div.eAll_border div.eAll div.cont div.c2_r div.zentrier div img");
            
            for (Element image : el) {
                String radarSrc = image.attr("src");
                radarSrc = radarSrc.replace("//", "");
                return radarSrc.replace(".gif", ".png");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Oops, нет данных";
    }
    
    public String getRadarRain() {
        try{
        Document pageMeteoRain = Jsoup.connect("https://www.meteoinfo.by/maps/?type=gis&map=SYNOP_GIS&date=20210622&time=06")
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
        Elements sourceRain = pageMeteoRain.select("html body div#cover div#wrapper div#container div#content table#map tbody tr td img");
             for (Element s : sourceRain){
                 double i = Math.random();
                 String src = s.attr("src");
             return "https://www.meteoinfo.by/maps/" + src + "?t=" + i + " - осадки 12ч прогноз";
        }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return "Oops, нет данных";
    }
    
    public String getRadarTemp() {
        try{
        Document pageMeteoTemp = Jsoup.connect("https://www.meteoinfo.by/maps/?type=egrr&map=TMP2m&date=2021062600&time=12")
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com")
                        .get();
        Elements sourceTemp = pageMeteoTemp.select("#map > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > img:nth-child(1)");
        for (Element s : sourceTemp){
                double i = Math.random();
                String src2 = s.attr("src");
             return "https://www.meteoinfo.by/maps/" + src2 + "?t=" + i + " - t' 12ч прогноз";
        }
      } catch (Exception e) {
            e.printStackTrace();
        }
        return "Oops, нет данных";
    }

    public String getRadarUkr(ModelParser modelParser) {
        return "РАДАР КИЕВ: " + "https://radar.veg.by/kiev/";
    }

    public String getGeoMag() {
        try {
            Document page2 = (Document) Jsoup.connect("https://www.pogoda.by/uv-gw/geo.php").get();
            Elements elGeomag = page2.select("html body div#cover div#wrapper div#container div#content");
            String res = null;
            for (Element el : elGeomag) {
                res = el.child(6).text();
            }
            return res;
        }catch (Exception e){
            try{
                Document page3 = (Document) Jsoup.connect("https://v-kosmose.com/magnitnyie-buri-segodnya/").get();
                return page3.select(".entry-content > p:nth-child(7)").text().toLowerCase();
            }catch (Exception el){
                return "Oops, нет данных";
            }
        }
    }
}
