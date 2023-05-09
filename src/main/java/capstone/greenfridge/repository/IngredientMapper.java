package capstone.greenfridge.repository;

import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.Ingredient.IngredientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IngredientMapper {
    Optional<Ingredient> findByIngredientName(String ingredientName);

    List<IngredientVO> toInitIngredientMap();
}
