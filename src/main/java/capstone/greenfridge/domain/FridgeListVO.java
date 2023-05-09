package capstone.greenfridge.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FridgeListVO {
    private Long fridgeId;
    private Long ingredientId;
    private String ingredientName;
    private String ingredientImg;
    private LocalDate storedAt;
    private LocalDate durationAt;
}
