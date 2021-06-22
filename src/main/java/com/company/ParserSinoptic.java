package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserSinoptic {
    static String getPage(String str, ModelParser modelParser) throws Exception {
        //try {
            String url = "https://sinoptik.ua/погода-" + str;
            Document page = (Document) Jsoup.connect(url).timeout(15 * 1000).get();

            modelParser.setCountryRegion(page.select("html body.ru div#wrapper div#header.clearfix div.cityName.cityNameShort div.currentRegion").text());
            modelParser.setDayName(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabs div#bd1.main.loaded p.day-link").text());
            modelParser.setDayInt(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabs div#bd1.main.loaded p.date").text());
            modelParser.setMonth(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabs div#bd1.main.loaded p.month").text());
            modelParser.setMinTemp(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabs div#bd1.main.loaded div.temperature div.min span").text());
            modelParser.setMaxTemp(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabs div#bd1.main.loaded div.temperature div.max span").text());
            modelParser.setHumidity(page.select(".weatherDetails > tbody:nth-child(2) > tr:nth-child(6) > td:nth-child(6)").text());
            modelParser.setPreasure(page.select("tr.gray:nth-child(5) > td:nth-child(6)").text());
            modelParser.setDescription(page.select("html body.ru div#wrapper div#content.clearfix div#leftCol div#mainContentBlock div#blockDays.bd1 div.tabsContent div.tabsContentInner div#bd1c.Tab div.wDescription.clearfix div.rSide div.description").text());
        /*} catch (Exception e) {
            return "Oops, что-то не сработало, попробуйте еще" + "\n"
                    + "или проверьте ввод.";
        }*/

        return "Регион: " + modelParser.getCountryRegion().toLowerCase() + "\n" +
                "День: " + modelParser.getDayName().toLowerCase() + " " + modelParser.getDayInt() + "\n" +
                "Месяц: " + modelParser.getMonth().toLowerCase() + "\n" +
                "Температура: макс/мин " + modelParser.getMaxTemp() + "/" + modelParser.getMinTemp() + " C°" + "\n" +
                "Влажность: " + RexEx.gateDateFromStr(modelParser.getHumidity()) + "%" + "\n" +
                "Давление: " + RexEx.gateDateFromStr(modelParser.getPreasure()) + "mmp" + "\n" +
                "Описание: " + modelParser.getDescription() + "\n" +
                Emoji.LEFT_RIGHT_ARROW +" Фактическая геомагнитная обстановка: " + new Radars().getGeoMag() + "\n" + "\n" +
                new Radars().getRadarUkr(modelParser) + "\n" +
                "Больше данных: https://sinoptik.ua/погода-" + str;
    }
}
