package capstone.greenfridge.repository;

import capstone.greenfridge.domain.FridgeListVO;
import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.MyFridgeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FridgeMapper {
    void saveIngredient(MyFridgeVO fridgeVO);
    void deleteIngredient(Long fridgeId);
    List<FridgeListVO> getFridgeList();
}
