package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RexEx {

    private static Pattern patternHumidity = Pattern.compile("\\s\\d{2}\\s"); // 2 nums
    private static Pattern patternPreasure = Pattern.compile("\\s\\d{3}\\s"); // 3 nums
    private static Pattern patternPng = Pattern.compile("\\d+"); // all nums

    static String gateDateFromStr(String str) throws Exception {
        Matcher matcherHumidiry = patternHumidity.matcher(str);
        Matcher matcherPreasure = patternPreasure.matcher(str);
        Matcher matcherPng = patternPng.matcher(str);

        if (matcherHumidiry.find()) {
            return matcherHumidiry.group();
        }
        if (matcherPreasure.find()) {
            return matcherPreasure.group();
        }
        if (matcherPng.find()) {
            return matcherPng.group();
        }
        throw new Exception("Cant extract date from " + str);
    }
}
