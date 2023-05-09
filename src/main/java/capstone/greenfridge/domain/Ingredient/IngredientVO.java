package capstone.greenfridge.domain.Ingredient;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IngredientVO {
    private final Long ingredientId;
    private final String ingredientName;
}
