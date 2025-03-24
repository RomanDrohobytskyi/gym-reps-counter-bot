package gym.reps.counter.commands;

import gym.reps.counter.callbacks.CallbackType;
import gym.reps.counter.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final static String START_MESSAGE = "Welcome back %s. What do we do today?";

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getFrom().getUserName();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(String.format(START_MESSAGE, username));

        addStartKeyboardOptions(sendMessage);
        return sendMessage;
    }

    private void addStartKeyboardOptions(SendMessage sendMessage) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();


        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("New workout");
        String jsonCallback = JsonUtil.toJson(List.of(CallbackType.NEW_WORKOUT, "New workout"));
        inlineKeyboardButton.setCallbackData(jsonCallback);
        keyboardButtonsRow.add(inlineKeyboardButton);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        inlineKeyboardMarkup.setKeyboard(rowList);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}
