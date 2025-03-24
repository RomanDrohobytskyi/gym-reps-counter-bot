package gym.reps.counter.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Slf4j
@Component
public class CommandsHandler {

    private final static String START_COMMAND = "/start";

    private Map<String, Command> commands;

    public CommandsHandler(StartCommand startCommand) {
        commands = Map.of(START_COMMAND, startCommand);
    }

    public SendMessage handleCommands(Update update) {
        String messageText = update.getMessage().getText();
        String command = messageText.split(" ")[0];
        long chatId = update.getMessage().getChatId();

        var commandHandler = commands.get(command);
        if (commandHandler != null) {
            return commandHandler.apply(update);
        } else {
            return new SendMessage(String.valueOf(chatId), "Command not found");
        }
    }
}
