package gym.reps.counter.model.dto;


import gym.reps.counter.model.entity.Exercise;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class UserWorkoutData {
    private final Exercise exercise;
    private final Long chatId;
    private Date startTime = new Date();
    private String weight;
    private String reps;
    private String sets;

    public String getStringChatId() {
        return String.valueOf(this.chatId);
    }
}
