package gym.reps.counter;

import gym.reps.counter.simple.bot.SimpleGymRepsCounterBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {
        try {
            initSimpleBot();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void initSimpleBot() throws TelegramApiException {
        SimpleGymRepsCounterBot gymRepsCounterBot = new SimpleGymRepsCounterBot();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(gymRepsCounterBot);
    }
}