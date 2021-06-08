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
            //парсить ссылку на картинку с сайта
            Elements el = pageEur.select("html body.eBody div.eZent_300 div.eAll_sky div.eAll_border div.eAll div.cont div.c2_r div.zentrier div img");
            for (Element image : el) {
                String radarSrc = image.attr("src");
                radarSrc = radarSrc.replace("//", "");
                return radarSrc.replace(".gif", ".jpeg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Oops, нет данных";
    }

    public String getRadarUkr(ModelParser modelParser) {
            
        return "Упс, нет данных UKB. Радар временно не работает.";
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
