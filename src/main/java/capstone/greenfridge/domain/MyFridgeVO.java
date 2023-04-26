package capstone.greenfridge.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


/**
 * 재료현황을 나타낼때 사용할 클래스
 */
@Builder
@Getter
public class MyFridgeVO {
    private final Long ingredientId;
    private final String ingredientName;
    private final LocalDate durationAt;
    private final LocalDate storedAt;
}
