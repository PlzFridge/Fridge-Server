package capstone.greenfridge.domain.response;

import capstone.greenfridge.domain.Recipe;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeData {
    private final Long recipeId;
    private final String recipeName;
    private final String recipeImg;
    private final String ingredientList;
    private final String method;
    private final List<String> existList;
    private final List<String> notExistList;
    private final Long carbon;
}
