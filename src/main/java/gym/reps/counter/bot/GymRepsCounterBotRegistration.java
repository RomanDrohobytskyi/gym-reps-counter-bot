package gym.reps.counter.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class GymRepsCounterBotRegistration {
    private final GymRepsCounterBot gymRepsCounterBot;

    public GymRepsCounterBotRegistration(GymRepsCounterBot gymRepsCounterBot) {
        this.gymRepsCounterBot = gymRepsCounterBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this.gymRepsCounterBot);
        } catch (TelegramApiException e) {
            log.error("Error registering bot", e);
        }
    }
}
