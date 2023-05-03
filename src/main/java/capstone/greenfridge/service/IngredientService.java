package capstone.greenfridge.service;

import capstone.greenfridge.domain.FridgeListVO;
import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.domain.MyFridgeVO;
import capstone.greenfridge.domain.FridgeInfo;
import capstone.greenfridge.repository.FridgeMapper;
import capstone.greenfridge.repository.IngredientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static capstone.greenfridge.domain.ExceptionMessageConst.SUCCESS_LOAD_LIST;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientMapper ingredientMapper;
    private final FridgeMapper fridgeMapper;

    public void saveIngredient(IngredientDTO ingredientDTO){

        String ingredientName= ingredientDTO.getIngredientName();
        LocalDate durationAt;
        Ingredient tempIngredient=ingredientMapper.findByIngredientName(ingredientName);

        if(ingredientDTO.getDurationAt()==null){
            LocalDate todayLocalDate=LocalDate.now();
            Long duration=tempIngredient.getDurationAt();

            durationAt=todayLocalDate.plusDays(duration);
        }else{
            durationAt=ingredientDTO.getDurationAt();
        }

        MyFridgeVO fridgeVO=MyFridgeVO.builder()
                .ingredientId(tempIngredient.getIngredientId())
                .ingredientName(ingredientDTO.getIngredientName())
                .durationAt(durationAt)
                .storedAt(LocalDate.now())
                .build();
        fridgeMapper.saveIngredient(fridgeVO);
        //ingredientDTO의 유효기간이 비어있다면 재료이름을 통해 평균적인 유통기한 입력
    }

    public void deleteIngredient(Long fridgeId){

        fridgeMapper.deleteIngredient(fridgeId);
    }

    public FridgeInfo getFridgeList(){

        List<FridgeListVO> data=fridgeMapper.getFridgeList();

        FridgeInfo fridgeInfo = FridgeInfo.builder()
                .status(HttpStatus.OK.value())
                .message(SUCCESS_LOAD_LIST.getMessage())
                .data(data)
                .build();

        return fridgeInfo;
    }
}
