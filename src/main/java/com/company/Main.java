package com.company;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static class wea_bot extends TelegramLongPollingBot {
        public static void main(String[] args) {
            // Initialize Api Context
            ApiContextInitializer.init();
            // Instantiate Telegram Bots API
            TelegramBotsApi botsApi = new TelegramBotsApi();
            // Register our bot
            try {
                botsApi.registerBot(new wea_bot());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String getBotUsername() {
            return "wea_bot";
        }

        @Override
        public String getBotToken() {
            return ""; //bot token
        }

        public void sendMsg(Message message, String text) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.enableHtml(true);
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setText(text);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpdateReceived(Update update) {
            Model model = new Model();
            ModelParser modelParser = new ModelParser();
            Message message = update.getMessage();
            String strWeaCity = message.getText().toLowerCase();

            if (message.getText().equals("/start")) {
                sendMsg(message, "Доброго времени, " + message.getFrom().getFirstName() + ", давай начнем! " + Emoji.ROCKET + "\n" +
                        "/help что бы помочь боту понимать Вас. " + Emoji.GLOBE_WITH_MERIDIANS);
                sendMsg(message, "Данные для бота берутся с двух источников: OpenWeather и Sinoptic." + "\n" + "\n" +
                        Emoji.HEAVY_EXCLAMATION_MARK_SYMBOL + " ввод предпочтительно на русском! RU" + "\n" + "\n" +
                        "- EN|RU • получите OW|SIN прогноз на сегодня: \"ввод населённого пункта\"" + "\n" +
                        "- RU • получить SIN прогноз на три(3) дня; пример: прогноз на 3-ри дня \"ввод населённого пункта\"" + "\n" + "\n" + "/help");
            } else if (message.getText().equals("/help")) {
                SendMessage response = new SendMessage();
                response.setChatId(message.getChatId());
                response.setText("Данные для бота берутся с двух источников: OpenWeather и Sinoptic." + "\n" +
                        Emoji.HEAVY_EXCLAMATION_MARK_SYMBOL + "\n" +
                        "• получите прогноз на сегодня: \"ввод населённого пункта\"" + "\n" +
                        "• получить SIN прогноз на три(3) дня; пример: прогноз на 3-ри дня \"ввод населённого пункта\"" + "\n" + "\n" +
                        "Пример:");
                response.setReplyMarkup(getMainMenuForHelp());
                try {
                    execute(response);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (message.getText().startsWith("прогноз на 3-ри дня")) {
                String cityForForecast = message.getText().substring(20).toLowerCase();
                try {
                    if (cityForForecast.contains(" ")) {
                        sendMsg(message, ParserSinopticDays.getPage(cityForForecast.replace(" ", "-"), modelParser, "2"));
                        sendMsg(message, ParserSinopticDays.getPage(cityForForecast.replace(" ", "-"), modelParser, "3"));
                        sendMsg(message, ParserSinopticDays.getPage(cityForForecast.replace(" ", "-"), modelParser, "4"));
                    } else {
                        sendMsg(message, ParserSinopticDays.getPage(cityForForecast, modelParser, "2"));
                        sendMsg(message, ParserSinopticDays.getPage(cityForForecast, modelParser, "3"));
                        sendMsg(message, ParserSinopticDays.getPage(cityForForecast, modelParser, "4"));
                    }
                } catch (Exception e) {
                    sendMsg(message, "проверьте ввод населённого пункта" + "\n" + "/help");
                }
            } else if (message.hasText()) {
                SendMessage response = new SendMessage();
                response.setChatId(message.getChatId());
                response.setText("запрос в обработке ... " + Emoji.ROCKET);
                response.setReplyMarkup(getUserMarkup(update));
                // для синоптика когда город с пробелом
                String strReplWeaCity = strWeaCity.replace(" ", "-").toLowerCase();
                try {
                    execute(response);
                    sendMsg(message, WeatherOpenWeather.getWeather(strWeaCity, model));
                    sendMsg(message, ParserSinoptic.getPage(strWeaCity.contains(" ") ? strReplWeaCity : strWeaCity, modelParser));
                } catch (Exception e) {
                    sendMsg(message, "Oops, что-то не сработало, попробуйте еще" + "\n"
                            + "или проверьте ввод." + "\n" + "\n" + "/help");
                }
            } else {
                sendMsg(message, "нет команды по вводу! Проверьте корректность /help");
            }
        }

        private ReplyKeyboardMarkup getMainMenuForHelp() {
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            markup.setResizeKeyboard(true);
            markup.setOneTimeKeyboard(true);
            KeyboardRow row1 = new KeyboardRow();
            KeyboardRow row2 = new KeyboardRow();
            KeyboardRow row3 = new KeyboardRow();
            row1.add("Белая Церковь");
            row2.add("Киев");
            row3.add("прогноз на 3-ри дня Киев");
            List<KeyboardRow> rows = new ArrayList<>();
            rows.add(row1);
            rows.add(row2);
            rows.add(row3);
            markup.setKeyboard(rows);
            return markup;
        }

        private ReplyKeyboardMarkup getUserMarkup(Update update) {
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            markup.setOneTimeKeyboard(true);
            markup.setResizeKeyboard(true);
            KeyboardRow row1 = new KeyboardRow();
            KeyboardRow row2 = new KeyboardRow();
            row1.add(update.getMessage().getText());
            row2.add("прогноз на 3-ри дня " + update.getMessage().getText());
            List<KeyboardRow> rows = new ArrayList<>();
            rows.add(row1);
            rows.add(row2);
            markup.setKeyboard(rows);
            return markup;
        }
    }
}
