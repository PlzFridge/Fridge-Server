package capstone.greenfridge.repository;

import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.MyFridgeVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FridgeMapper {
    void saveIngredient(MyFridgeVO fridgeVO);
    void deleteIngredient(Long ingredientId);
}
