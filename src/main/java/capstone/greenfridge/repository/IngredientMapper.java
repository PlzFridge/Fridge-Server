package capstone.greenfridge.repository;

import capstone.greenfridge.domain.Ingredient.Ingredient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IngredientMapper {
    Ingredient findByIngredientName(String ingredientName);
}
