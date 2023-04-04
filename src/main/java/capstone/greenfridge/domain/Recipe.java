package capstone.greenfridge.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Recipe {
    private final Long recipeId;
    private final String recipeName;
    private final String recipeImg;
    private final String ingredientList;
    private final String method;
}
