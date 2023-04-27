package capstone.greenfridge.domain.Ingredient;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 재료에 대한
 */
@Builder
@Getter
public class Ingredient {
    private final Long ingredientId;
    private final String ingredientName;
    private final String ingredientImg;
    private final Long carbonOutput;
    private final String disposal;
    private final Long durationAt;
}
