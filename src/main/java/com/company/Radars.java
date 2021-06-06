package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Radars {

    public String getRadarEur() {
        try {
            Document pageEur = Jsoup.connect("https://www.weatheronline.co.uk/Europe.htm").userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
            Elements el = pageEur.select("html body.eBody div.eZent_300 div.eAll_sky div.eAll_border div.eAll div.cont div.c2_r div.zentrier div img");
            Iterator var3 = el.iterator();
            if (var3.hasNext()) {
                Element image = (Element)var3.next();
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
        try {
            Document pageSin = Jsoup.connect("https://radar.veg.by/kiev/")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            //"https://meteoinfo.by/radar/UKBB/UKBB" + "_" + RexEx.gateDateFromStr(new Radars().getRadarUkr(modelParser)) + ".png"

            //modelParser.setPngStr(pageSin.select("html body div#scroller div div img"));
            //return modelParser.getPngStr().toString().replace("_", "-");
            //return "Радар осадков: " + "https://meteoinfo.by/radar/UKBB/UKBB" + "_" + RexEx.gateDateFromStr(modelParser.getPngStr().toString().replace("_", "-")) + ".png";
            Elements radar = pageSin.select("html body div#scroller div div img");
            for (Element image : radar) {
                String radarSrc = image.attr("src");
                return "https://radar.veg.by" + radarSrc;
            }
            //return "Радар осадков: ";
        } catch (Exception e) {
            return "Упс, нет данных UKB. Радар временно не работает.";
        }
        //return getRadarEur();
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
            //return "Oops, нет данных";
        }
    }

    /*public String descriptonForRadars(Model model) throws Exception {
        //try {
            if (model.getCountry().contains("UA") || model.getCountry().toLowerCase().contains("ukraine")) {
                return "Радар осадков: " + "https://meteoinfo.by/radar/UKBB/UKBB" + "_" + RexEx.gateDateFromStr(getRadarUkr(new ModelParser())) + ".png";
                //return "https://meteoinfo.by/radar/UKBB/UKBB" + "_" + RexEx.gateDateFromStr(modelParser.getPngStr().toString().replace("_", "-"))  + ".png" ;
            } else {
                return "Упс, нет данных UKB. Радар временно не работает.";
            }
        *//*} catch (Exception e) {
            return getRadarEur();
        }*//*
    }*/
}
