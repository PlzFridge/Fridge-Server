package capstone.greenfridge.domain.Ingredient;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 카메라를 통해 재료입력을 할때 외부에서 입력들어오는 DTO
 */
@Builder
@Getter
public class IngredientDTO {
    private final String ingredientName;
    private final LocalDate durationAt;
}
