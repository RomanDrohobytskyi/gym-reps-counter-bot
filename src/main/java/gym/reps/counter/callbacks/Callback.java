package gym.reps.counter.callbacks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Callback {

    private CallbackType callbackType;
    private String data;
}
