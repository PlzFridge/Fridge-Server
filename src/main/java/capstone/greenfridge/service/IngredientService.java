package capstone.greenfridge.service;

import capstone.greenfridge.domain.FridgeListVO;
import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.domain.Ingredient.IngredientVO;
import capstone.greenfridge.domain.MyFridgeVO;
import capstone.greenfridge.domain.Recipe;
import capstone.greenfridge.domain.response.FridgeCRUD;
import capstone.greenfridge.domain.response.FridgeInfo;
import capstone.greenfridge.domain.response.RecipeInfo;
import capstone.greenfridge.repository.FridgeMapper;
import capstone.greenfridge.repository.IngredientMapper;
import capstone.greenfridge.repository.RecipeMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import static capstone.greenfridge.domain.ExceptionMessageConst.*;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientMapper ingredientMapper;
    private final FridgeMapper fridgeMapper;
    private final RecipeMapper recipeMapper;
    private final static Map<String,Long> ingredientMap=new LinkedHashMap<>();
    private final static Map<String,Set<Long>> recipeIngredient=new LinkedHashMap<>();
    @PostConstruct
    public void init(){
        List<IngredientVO> initIngredientData=ingredientMapper.toInitIngredientMap();
        List<Recipe> initRecipeData=recipeMapper.getRecipeList();

        for(IngredientVO i:initIngredientData){//재료종류 불러와서 이름,id로 저장
            ingredientMap.put(i.getIngredientName(),i.getIngredientId());
        }
        for(Recipe i:initRecipeData){
            String[] temp=i.getIngredientList().split("/");
            Set<Long> parsingList=new HashSet<>();

            for(String j:temp){
                parsingList.add(ingredientMap.get(j));
            }
            recipeIngredient.put(i.getRecipeName(),parsingList);
        }

        //레시피는 순서대로, 재료 ID가 각각 Set 으로 저장되어있는 상태

    }

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

    public RecipeInfo recommendRecipe(){
        List<FridgeListVO> curFridgeData=fridgeMapper.getFridgeList();
        List<Recipe> data=new ArrayList<>();
        long[] score=new long[recipeIngredient.size()];
        long[] maxIdxArray=new long[3];

        for(FridgeListVO i:curFridgeData){
            String tempName=i.getIngredientName();
            int idx=0;
            for(Set<Long> j:recipeIngredient.values()){
                if(j.contains(i.getIngredientId())){
                    score[idx]=score[idx]+10;
                }
                idx++;
            }
        }

        for(int i=0;i<3;i++){
            long maxValue=0;
            int maxIdx=0;
            for(int j=0;j<recipeIngredient.size();j++){
                if(maxValue<score[j]){
                    maxValue=score[j];
                    maxIdx=j;
                }
            }
            maxIdxArray[i]=maxIdx;
            score[maxIdx]=-1;
        }
        //점수 제일 높은 레시피의 id 3개 maxIdxArray에 저장된 상태
        for(int i=0;i<3;i++){
            Recipe selected=recipeMapper.getRecipe(maxIdxArray[i]);
            data.add(selected);
        }

        return RecipeInfo.builder()
                .status(HttpStatus.OK.value())
                .message(SUCCESS_RECOMMEND_RECIPE.getMessage())
                .data(data)
                .build();
    }

}
