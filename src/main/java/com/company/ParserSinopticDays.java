package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserSinopticDays {
    static String getPage(String str, ModelParser modelParser, String days) throws Exception {
        String url = "https://sinoptik.ua/погода-" + str;
        Document page = (Document) Jsoup.connect(url).timeout(15 * 1000).get();
        modelParser.setCountryRegion(page.select("html body.ru div#wrapper div#header.clearfix div.cityName.cityNameShort div.currentRegion").text());
        modelParser.setDayName(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabs div#bd" + days + ".main p.date").text());
        modelParser.setMonth(page.select("#bd" + days + " > p:nth-child(3)").text());
        modelParser.setMinTemp(page.select("#bd" + days + " > div:nth-child(5) > div:nth-child(1) > span:nth-child(1)").text());
        modelParser.setMaxTemp(page.select("#bd" + days + " > div:nth-child(5) > div:nth-child(2) > span:nth-child(1)").text());
        //modelParser.setDescription(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd2 div.tabsContent div.tabsContentInner div#bd"+days+"c.Tab div.wDescription.clearfix div.rSide div.description").text());
        //String icoOut = null;
        Elements el = page.select("#bd"+days+" > div:nth-child(4)");

        for (Element image : el) {
            modelParser.setDescription(image.attr("title"));
        }

        return "Регион: " + modelParser.getCountryRegion().toLowerCase() + "\n" +
                "День: " + modelParser.getDayName() + " " + modelParser.getMonth() + "\n" +
                "Температура: макс/мин " + modelParser.getMaxTemp() + "/" + modelParser.getMinTemp() + " C°" + "\n" +
                "Описание: " + modelParser.getDescription();
    }
}
