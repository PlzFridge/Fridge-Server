package capstone.greenfridge.service;

import capstone.greenfridge.domain.FridgeListVO;
import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.domain.MyFridgeVO;
import capstone.greenfridge.domain.response.FridgeCRUD;
import capstone.greenfridge.domain.response.FridgeInfo;
import capstone.greenfridge.repository.FridgeMapper;
import capstone.greenfridge.repository.IngredientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static capstone.greenfridge.domain.ExceptionMessageConst.*;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientMapper ingredientMapper;
    private final FridgeMapper fridgeMapper;

    public FridgeCRUD saveIngredient(IngredientDTO ingredientDTO){

        String ingredientName= ingredientDTO.getIngredientName();
        LocalDate durationAt;

        Optional<Ingredient> tempIngredient=ingredientMapper.findByIngredientName(ingredientName);


        Boolean success= tempIngredient.isPresent();
        String message=(tempIngredient.isEmpty())? FAILED_SAVE_INGREDIENT.getMessage()
                : SUCCESS_SAVE_INGREDIENT.getMessage();

        if(success) {//들어온 입력에 해당하는 재료가 존재할시
            if (ingredientDTO.getDurationAt() == null) {
                LocalDate todayLocalDate = LocalDate.now();
                Long duration = tempIngredient.get().getDurationAt();

                durationAt = todayLocalDate.plusDays(duration);
            } else {
                durationAt = ingredientDTO.getDurationAt();
            }

            MyFridgeVO fridgeVO = MyFridgeVO.builder()
                    .ingredientId(tempIngredient.get().getIngredientId())
                    .ingredientName(ingredientDTO.getIngredientName())
                    .durationAt(durationAt)
                    .storedAt(LocalDate.now())
                    .build();
            fridgeMapper.saveIngredient(fridgeVO);
        }

        return FridgeCRUD.builder()
                .status(HttpStatus.CREATED.value())
                .success(success)
                .message(message)
                .build();
    }

    public FridgeCRUD deleteIngredient(Long fridgeId){

        fridgeMapper.deleteIngredient(fridgeId);

        return FridgeCRUD.builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message(SUCCESS_DELETE_INGREDIENT.getMessage())
                .build();
    }

    public FridgeInfo getFridgeList(){

        List<FridgeListVO> data=fridgeMapper.getFridgeList();

        return FridgeInfo.builder()
                .status(HttpStatus.OK.value())
                .message(SUCCESS_LOAD_LIST.getMessage())
                .data(data)
                .build();
    }
}
