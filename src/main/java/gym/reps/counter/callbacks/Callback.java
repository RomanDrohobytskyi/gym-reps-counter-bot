package gym.reps.counter.callbacks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Callback {

    private CallbackType callbackType;
    private String data;

    public static Callback of(List<String> callbackData) {
        if (CollectionUtils.isEmpty(callbackData) || callbackData.size() > 2) {
            throw new IllegalArgumentException("Invalid callback data");
        }
        return of(CallbackType.valueOf(callbackData.get(0)), callbackData.get(1));
    }

    public static Callback of(CallbackType callbackType, String data) {
        return Callback.builder()
                .callbackType(callbackType)
                .data(data)
                .build();
    }
}
