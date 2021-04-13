package com.company;

import com.google.common.base.Utf8;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class WeatherWeatherstack {

    public static String getWeather(String message, Model model) throws Exception {
        try {
            URL url = new URL("http://api.weatherstack.com/current?access_key=5ca78866919bae171119fb9afa218ae9&query=" + message);
            Scanner in = new Scanner((InputStream) url.getContent());
            StringBuilder result = new StringBuilder();
            while (in.hasNext()) {
                result.append(in.nextLine());
            }

            JSONObject object = new JSONObject(result.toString());

            JSONObject location = object.getJSONObject("location");
            model.setName(location.getString("name"));
            model.setCountry(location.getString("country"));

            // ниже строчка выбора массива с данными; название это конкретный массив с API Json
            JSONObject current = object.getJSONObject("current");
            // ниже строчка выбора данных с массива данных; название это выбор
            model.setTemp(current.getDouble("temperature"));
            ///model.setHumidity(current.getDouble("humidity"));

            JSONArray getArray = current.getJSONArray("weather_descriptions");
            model.setDescriptions((String) getArray.get(Integer.parseInt("0")));
            String inDescript = model.getDescriptions().toLowerCase();

            if (inDescript.contains("clear")) {
                model.setIcon(Emoji.SUN_BEHIND_CLOUD);
            } else if (inDescript.contains("cloudy") || model.getDescriptions().contains("overcast")) {
                model.setIcon(Emoji.CLOUD);
            } else if (inDescript.contains("rain")) {
                model.setIcon(Emoji.UMBRELLA_WITH_RAIN_DROPS);
            } else if (inDescript.contains("foggy") || model.getDescriptions().contains("haze")) {
                model.setIcon(Emoji.FOGGY);
            }
            else {model.setIcon(Emoji.GLOBE_WITH_MERIDIANS);}

        } catch (Exception e) {
            return "Oops, input wrong, try again /";
        }

        return "City: " + model.getName().toLowerCase() + "\n" +
                "Country: " + model.getCountry().toLowerCase() + "\n" +
                "Temperature: " + model.getTemp() + " C°" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Descriptions: " + model.getDescriptions().toLowerCase() + " " +
                model.getIcon();
                //+ new Radars().getRadarEur();
                //"\n" + "This data from: http://weatherstack.com";
    }
}
