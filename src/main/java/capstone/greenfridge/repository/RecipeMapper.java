package capstone.greenfridge.repository;

import capstone.greenfridge.domain.Recipe;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {
    List<Recipe> getRecipeList();
    Recipe getRecipe(Long recipeId);
}
