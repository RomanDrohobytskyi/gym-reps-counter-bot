package gym.reps.counter.secrets.reader;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecretsReader {
    public final static String BOT_TOKEN = "TELEGRAM_BOT_TOKEN";
    public final static String BOT_NAME = "TELEGRAM_BOT_NAME";
    public final static String DATABASE_URL = "DATABASE_URL";
    private Dotenv dotenv;

    public static SecretsReader getBotConfigReader() {
        SecretsReader reader = new SecretsReader();
        reader.dotenv = Dotenv.configure().filename("bot.config.env").load();
        return reader;
    }

    public String readSecret(String secretName) {
        return dotenv.get(secretName);
    }
}
