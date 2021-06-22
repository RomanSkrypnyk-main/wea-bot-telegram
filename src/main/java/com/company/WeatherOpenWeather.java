package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherOpenWeather {

    public static String getWeather(String message, Model model) throws Exception {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=a94b7d68121a70bf0a90585108a3232b");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));
        model.setId(object.getInt("id"));

        JSONObject sys = object.getJSONObject("sys");
        model.setCountry(sys.getString("country"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity((int) main.getDouble("humidity"));

        //массив[] данных обьекта. массив внутри обьекта
        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setDescriptions((String) obj.get("description"));
        }

        if (model.getDescriptions().contains("clear")) {
            model.setIcon(Emoji.SUN_BEHIND_CLOUD);
        } else if (model.getDescriptions().contains("clouds")) {
            model.setIcon(Emoji.CLOUD);
        } else if (model.getDescriptions().contains("rain") /*|| model.getDescriptions().contains("rain")*/) {
            model.setIcon(Emoji.UMBRELLA_WITH_RAIN_DROPS);
        } else if (model.getDescriptions().contains("mist") || model.getDescriptions().contains("fog")) {
            model.setIcon(Emoji.FOGGY);
        } else if (model.getDescriptions().contains("thunderstorm")) {
            model.setIcon(Emoji.HIGH_VOLTAGE_SIGN);
        } else if (model.getDescriptions().contains("snow")) {
            model.setIcon(Emoji.SNOWMAN_WITHOUT_SNOW);
        } else {
            model.setIcon(Emoji.GLOBE_WITH_MERIDIANS);
        }

        return "Город: " + model.getName().toLowerCase() + "\n" +
                "Страна: " + model.getCountry().toLowerCase() + "\n" +
                "Температура: " + model.getTemp() + " C°" + "\n" +
                "Влажность: " + model.getHumidity() + "%" + "\n" +
                "Описание: " + model.getDescriptions().toLowerCase() + " " +
                model.getIcon() + "\n" + "Радары погоды Европа: " + new Radars().getRadarEur() + "\n" +
                new Radars().getRadarRain() + "\n" + new Radars().getRadarTemp();
    }
}


