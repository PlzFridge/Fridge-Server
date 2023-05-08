package capstone.greenfridge.domain.response;

import capstone.greenfridge.domain.FridgeListVO;
import capstone.greenfridge.domain.Recipe;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecipeInfo {
    private final Integer status;
    private final String message;
    private final List<Recipe> data;
}
